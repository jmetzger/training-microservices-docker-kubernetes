# API Gateway vs. Istio Service Mesh – Authentication in Kubernetes

Beide Komponenten übernehmen Authentication in Kubernetes – aber auf **unterschiedlichen Ebenen**
und für **unterschiedliche Szenarien**. Oft braucht man beide.

> **Wichtig:** Ein API Gateway ist **keine** Ingress-Controller-Erweiterung.
> Es ist eine eigenständige Software speziell für API-Management –
> mit Plugins, Consumer-Verwaltung, Developer Portal und Analytics.
> Bekannte Vertreter: **Kong**, **Apache APISIX**, **Tyk**.

![API Gateway vs. Istio – Wann was?](/images/auth-gateway-vs-istio.svg)

---

## Teil 1: API Gateway Authentication

### Was ist ein API Gateway?

Ein API Gateway sitzt **vor allen Microservices** und übernimmt alles, was mit
API-Management zu tun hat – Authentication ist nur eine von vielen Aufgaben.

| Ingress Controller (NGINX, Traefik) | API Gateway (Kong, APISIX, Tyk) |
|--------------------------------------|----------------------------------|
| Routing / TLS – das war's | Auth, Rate Limiting, Routing, Transformation |
| Kubernetes-nativ, einfach | Speziell für APIs / Microservices gebaut |
| Konfiguration via Annotations | Plugin-Ökosystem, Admin-API, Developer Portal |
| Kein Consumer-Konzept | Consumer: wer ruft meine API auf? |
| Kein API-Key-Management | API-Keys, JWT, OIDC – alles built-in |

### Wann API Gateway?

- Externe Clients (Mobile App, Browser SPA, Partner) rufen APIs auf
- Verschiedene Auth-Methoden je nach Client (JWT, API-Key, OIDC)
- Rate Limiting pro Consumer oder pro API-Key
- Request/Response-Transformation (Header hinzufügen, Body umbauen)
- Zentrales API-Management mit Developer Portal

### Was das Gateway nicht löst

- Service A ruft intern Service B auf → das Gateway sieht das nicht
- Ost-West-Traffic bleibt ohne Gateway-Auth → dafür ist Istio zuständig

![API Gateway Authentication Flow](/images/auth-api-gateway-flow.svg)

### Wie funktioniert Authentication im API Gateway?

Das Gateway kennt das Konzept **Consumer**: eine App oder ein User,
der die API aufruft. Jeder Consumer bekommt Credentials (JWT, API-Key).
Das Gateway prüft die Credentials und leitet den Request **mit Consumer-Kontext**
an den Backend-Service weiter.

```
Mobile App                    Kong API Gateway              Order Service
    |                               |                             |
    |── GET /api/orders             |                             |
    |   Authorization: Bearer JWT ─>|                             |
    |                               |── JWT Plugin prüft:         |
    |                               |   Signatur ✓                |
    |                               |   exp ✓                     |
    |                               |   Consumer ermitteln        |
    |                               |                             |
    |                               |── GET /api/orders ─────────>|
    |                               |   X-Consumer-Id: 42         |
    |                               |   X-Consumer-Username: app1 |
    |                               |   X-Consumer-Custom-Id: ... |
    |                               |                             |
    |<─────────────────────────── Response ─────────────────────  |
```

---

### Code-Beispiel: Kong in Kubernetes (KongIngress + Plugins)

Kong läuft als Deployment im Cluster. Die Konfiguration erfolgt via
Kubernetes Custom Resources (CRDs).

```yaml
# 1. Kong installieren (Helm)
# helm repo add kong https://charts.konghq.com
# helm install kong kong/ingress -n kong --create-namespace
```

```yaml
# 2. Consumer anlegen – repräsentiert eine App oder einen User
apiVersion: configuration.konghq.com/v1
kind: KongConsumer
metadata:
  name: mobile-app
  namespace: production
  annotations:
    kubernetes.io/ingress.class: kong
username: mobile-app
custom_id: "app-001"
```

```yaml
# 3. JWT-Credentials für den Consumer erstellen
apiVersion: configuration.konghq.com/v1
kind: KongPlugin
metadata:
  name: jwt-plugin
  namespace: production
plugin: jwt
config:
  claims_to_verify:
  - exp
  key_claim_name: iss   # Kong sucht Consumer anhand des iss-Claims
---
# Secret mit dem Public Key des OIDC Providers
apiVersion: v1
kind: Secret
metadata:
  name: mobile-app-jwt
  namespace: production
  labels:
    konghq.com/credential: jwt
type: Opaque
stringData:
  kongCredType: jwt
  key: "https://keycloak.example.com/realms/myrealm"  # muss dem iss-Claim entsprechen
  algorithm: RS256
  rsa_public_key: |
    -----BEGIN PUBLIC KEY-----
    MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA...
    -----END PUBLIC KEY-----
```

```yaml
# 4. Route mit JWT-Plugin absichern
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: order-api
  namespace: production
  annotations:
    konghq.com/plugins: jwt-plugin
    konghq.com/strip-path: "false"
spec:
  ingressClassName: kong
  rules:
  - host: api.example.com
    http:
      paths:
      - path: /api/orders
        pathType: Prefix
        backend:
          service:
            name: order-service
            port:
              number: 8080
```

```yaml
# 5. Rate Limiting zusätzlich – 100 Requests pro Minute pro Consumer
apiVersion: configuration.konghq.com/v1
kind: KongPlugin
metadata:
  name: rate-limit
  namespace: production
plugin: rate-limiting
config:
  minute: 100
  policy: local
  limit_by: consumer
---
# Beide Plugins kombinieren
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: order-api
  namespace: production
  annotations:
    konghq.com/plugins: jwt-plugin,rate-limit
```

---

### Code-Beispiel: Apache APISIX in Kubernetes

APISIX ist eine Alternative zu Kong – ebenfalls Plugin-basiert, sehr performant.

```yaml
# APISIX installieren (Helm)
# helm repo add apisix https://charts.apiseven.com
# helm install apisix apisix/apisix --set ingress-controller.enabled=true -n apisix --create-namespace
```

```yaml
# ApisixRoute mit JWT-Plugin
apiVersion: apisix.apache.org/v2
kind: ApisixRoute
metadata:
  name: order-api
  namespace: production
spec:
  http:
  - name: orders-route
    match:
      hosts:
      - api.example.com
      paths:
      - /api/orders*
    backends:
    - serviceName: order-service
      servicePort: 8080
    plugins:
    - name: jwt-auth
      enable: true
    - name: limit-count
      enable: true
      config:
        count: 100
        time_window: 60
        key: consumer_name
        rejected_code: 429
---
# Consumer mit JWT-Credentials
apiVersion: apisix.apache.org/v2
kind: ApisixConsumer
metadata:
  name: mobile-app
  namespace: production
spec:
  authParameter:
    jwtAuth:
      value:
        key: mobile-app-key
        secret: "my-secret"     # für HS256
        # oder: public_key für RS256 (Keycloak)
        algorithm: RS256
        exp: 86400
```

```yaml
# OIDC Plugin – APISIX holt Token selbst von Keycloak
apiVersion: apisix.apache.org/v2
kind: ApisixRoute
metadata:
  name: order-api-oidc
  namespace: production
spec:
  http:
  - name: orders-oidc
    match:
      hosts:
      - api.example.com
      paths:
      - /api/*
    backends:
    - serviceName: order-service
      servicePort: 8080
    plugins:
    - name: openid-connect
      enable: true
      config:
        client_id: myapp
        client_secret: secret
        discovery: https://keycloak.example.com/realms/myrealm/.well-known/openid-configuration
        redirect_uri: https://api.example.com/callback
        scope: openid profile email
        bearer_only: true          # nur Bearer-Token prüfen, kein Redirect
        set_userinfo_header: true  # X-Userinfo Header setzen
```

---

### Backend-Service: Consumer-Header lesen

Nach der Prüfung setzt das Gateway Headers mit Consumer-Informationen.
Der Backend-Service braucht keinen Auth-Code.

```go
// Go – Kong setzt X-Consumer-* Header nach erfolgreicher JWT-Prüfung
func ordersHandler(w http.ResponseWriter, r *http.Request) {
    consumerID       := r.Header.Get("X-Consumer-Id")
    consumerUsername := r.Header.Get("X-Consumer-Username")
    consumerCustomID := r.Header.Get("X-Consumer-Custom-Id")

    if consumerID == "" {
        // Sollte nie passieren – Kong hätte vorher abgelehnt
        http.Error(w, "Unauthorized", http.StatusUnauthorized)
        return
    }

    log.Printf("Request von Consumer %s (username: %s)", consumerID, consumerUsername)
    // Business-Logik ...
}
```

```python
# FastAPI – APISIX setzt X-Userinfo wenn openid-connect Plugin aktiv
import base64, json
from fastapi import FastAPI, Header, HTTPException

app = FastAPI()

@app.get("/api/orders")
def get_orders(x_userinfo: str = Header(None)):
    if not x_userinfo:
        raise HTTPException(status_code=401)

    # X-Userinfo ist base64-kodiertes JSON
    user = json.loads(base64.b64decode(x_userinfo + "=="))
    return {"user": user.get("sub"), "email": user.get("email"), "orders": []}
```

---

## Teil 2: Istio Service Mesh Authentication

### Wann Istio?

Istio löst das Problem, das das API Gateway **nicht** löst: den **Ost-West-Traffic**
zwischen Services. Ohne Istio kann jeder Service im Cluster jeden anderen
aufrufen – ohne Authentifizierung, ohne Verschlüsselung.

**Typische Situationen:**
- Alle Verbindungen zwischen Services müssen verschlüsselt sein (mTLS)
- Service A darf Service B aufrufen, Service C aber nicht
- Ein kompromittierter Service soll keinen Schaden anrichten können
- JWT vom Kunden soll auch im Cluster weiter gültig sein

**Was Istio nicht löst:**
- Den initialen Login des Kunden – das ist Aufgabe des Identity Providers (Keycloak)
- API-Key-Verwaltung, Rate Limiting, Developer Portal – das ist Aufgabe des API Gateways

![Istio Service Mesh Flow](/images/auth-istio-mesh-flow.svg)

### Wie funktioniert es?

Istio injiziert in jeden Pod einen **Envoy Sidecar**. Dieser Proxy sitzt transparent
zwischen App und Netzwerk – die App selbst merkt nichts davon.

Istio hat **zwei Auth-Mechanismen**:

```
PeerAuthentication   → Wer ist der aufrufende Service?  (mTLS, SPIFFE-Identität)
RequestAuthentication → Wer ist der Endnutzer?           (JWT-Validierung)
AuthorizationPolicy  → Was ist erlaubt?                  (Kombination aus beidem)
```

### Code-Beispiel 1: mTLS erzwingen (PeerAuthentication)

```yaml
# Cluster-weit: kein Plain-Text-HTTP zwischen Pods mehr möglich
apiVersion: security.istio.io/v1beta1
kind: PeerAuthentication
metadata:
  name: default
  namespace: istio-system
spec:
  mtls:
    mode: STRICT
```

```bash
# Namespace für Sidecar-Injection markieren
kubectl label namespace production istio-injection=enabled

# Pods neu starten damit Envoy injiziert wird
kubectl rollout restart deployment -n production

# Prüfen: READY = 2/2 bedeutet App + Envoy Sidecar
kubectl get pods -n production
# order-service-7d9f8b-xk2pq   2/2   Running

# mTLS-Status prüfen
istioctl authn tls-check order-service.production.svc.cluster.local
```

### Code-Beispiel 2: JWT validieren (RequestAuthentication)

```yaml
# Istio prüft JWT automatisch via JWKS – der Service-Code muss das nicht tun
apiVersion: security.istio.io/v1beta1
kind: RequestAuthentication
metadata:
  name: customer-jwt
  namespace: production
spec:
  jwtRules:
  - issuer: "https://keycloak.example.com/realms/myrealm"
    jwksUri: "https://keycloak.example.com/realms/myrealm/protocol/openid-connect/certs"
    audiences:
    - "myapp"
    outputClaimToHeaders:
    - header: x-customer-id    # JWT-Claim → HTTP-Header
      claim: customer_id
    - header: x-user-role
      claim: role
    forwardOriginalToken: false
```

### Code-Beispiel 3: Zugriffskontrolle (AuthorizationPolicy)

```yaml
# Requests ohne JWT ablehnen
apiVersion: security.istio.io/v1beta1
kind: AuthorizationPolicy
metadata:
  name: require-jwt
  namespace: production
spec:
  selector:
    matchLabels:
      app: order-service
  action: DENY
  rules:
  - from:
    - source:
        notRequestPrincipals: ["*"]
---
# Nur bestimmte Rollen dürfen POST – GET ist public
apiVersion: security.istio.io/v1beta1
kind: AuthorizationPolicy
metadata:
  name: order-service-rules
  namespace: production
spec:
  selector:
    matchLabels:
      app: order-service
  action: ALLOW
  rules:
  - to:
    - operation:
        methods: ["GET"]
  - to:
    - operation:
        methods: ["POST"]
    when:
    - key: request.auth.claims[role]
      values: ["customer", "admin"]
---
# Ost-West: nur order-service darf payment-service aufrufen
apiVersion: security.istio.io/v1beta1
kind: AuthorizationPolicy
metadata:
  name: payment-allow-order
  namespace: production
spec:
  selector:
    matchLabels:
      app: payment-service
  action: ALLOW
  rules:
  - from:
    - source:
        # SPIFFE-ID aus dem Kubernetes ServiceAccount
        principals:
        - "cluster.local/ns/production/sa/order-service"
    to:
    - operation:
        methods: ["POST"]
        paths: ["/internal/payments"]
```

### Code-Beispiel 4: Backend liest Claims aus Headern

```go
// Go – Istio hat JWT validiert und Claims als Header gesetzt
func createOrderHandler(w http.ResponseWriter, r *http.Request) {
    customerID := r.Header.Get("X-Customer-Id")
    role       := r.Header.Get("X-User-Role")

    if customerID == "" {
        http.Error(w, "Unauthorized", http.StatusUnauthorized)
        return
    }
    // Business-Logik – kein JWT-Code nötig
    log.Printf("Order von %s (Rolle: %s)", customerID, role)
}
```

```python
# FastAPI – identisch, Claims kommen als Header
from fastapi import FastAPI, Header, HTTPException

app = FastAPI()

@app.post("/api/orders")
async def create_order(
    x_customer_id: str = Header(None),
    x_user_role:   str = Header(None),
):
    if not x_customer_id:
        raise HTTPException(status_code=401)
    return {"status": "created", "customer": x_customer_id}
```

---

## Zusammenspiel: API Gateway + Istio

```
Internet
   │  JWT / API-Key
   ▼
┌────────────────────────────────────────┐
│  API Gateway  (Kong / APISIX / Tyk)   │
│  • Consumer prüfen (JWT, API-Key)      │
│  • Rate Limiting                       │
│  • Request-Transformation              │
│  • X-Consumer-* Header setzen          │
└──────────────────┬─────────────────────┘
                   │ Consumer-Header
                   ▼
┌────────────────────────────────────────┐
│  Istio Service Mesh                    │
│                                        │
│  ┌─────────────┐  mTLS  ┌──────────┐  │
│  │ Order Svc   │───────>│ Payment  │  │
│  │ + Envoy     │        │ + Envoy  │  │
│  └─────────────┘        └──────────┘  │
│                                        │
│  PeerAuthentication: STRICT            │
│  AuthorizationPolicy: SA-basiert       │
└────────────────────────────────────────┘
```

```yaml
# Minimales Setup: beides zusammen
---
apiVersion: security.istio.io/v1beta1
kind: PeerAuthentication
metadata:
  name: default
  namespace: istio-system
spec:
  mtls:
    mode: STRICT
---
apiVersion: security.istio.io/v1beta1
kind: AuthorizationPolicy
metadata:
  name: payment-only-from-order
  namespace: production
spec:
  selector:
    matchLabels:
      app: payment-service
  action: ALLOW
  rules:
  - from:
    - source:
        principals:
        - "cluster.local/ns/production/sa/order-service"
```

---

## Weiterführendes

- [Kong Kubernetes Ingress Controller](https://docs.konghq.com/kubernetes-ingress-controller/)
- [Kong JWT Plugin](https://docs.konghq.com/hub/kong-inc/jwt/)
- [Apache APISIX für Kubernetes](https://apisix.apache.org/docs/ingress-controller/getting-started/)
- [APISIX openid-connect Plugin](https://apisix.apache.org/docs/apisix/plugins/openid-connect/)
- [Tyk Operator für Kubernetes](https://tyk.io/docs/tyk-operator/)
- [Istio Security Konzepte](https://istio.io/latest/docs/concepts/security/)
