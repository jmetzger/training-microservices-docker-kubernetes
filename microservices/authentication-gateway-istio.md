# API Gateway vs. Istio Service Mesh – Authentication in Kubernetes

Beide Komponenten übernehmen Authentication in Kubernetes – aber auf **unterschiedlichen Ebenen**
und für **unterschiedliche Szenarien**. Oft braucht man beide.

![API Gateway vs. Istio – Wann was?](/images/auth-gateway-vs-istio.svg)

---

## Teil 1: API Gateway Authentication

### Wann?

Das API Gateway ist die **einzige Stelle im Cluster, die nach außen offen ist**.
Es schützt den Nord-Süd-Traffic – also alles, was von außen reinkommt.

**Typische Situationen:**
- Mobile App sendet einen JWT → Gateway prüft ihn, bevor er irgendeinen Service erreicht
- Browser-Login via OAuth2 → Gateway delegiert den Login an Keycloak (oauth2-proxy)
- Partner-API mit API-Key → Gateway prüft den Key, Services wissen davon nichts
- Rate Limiting, IP-Allowlisting, TLS-Terminierung – alles am Gateway

**Was das Gateway NICHT löst:**
- Service A ruft Service B auf → das sieht das Gateway nicht (Ost-West-Traffic)
- Ein kompromittierter Service kann intern trotzdem alles aufrufen

![API Gateway Authentication Flow](/images/auth-api-gateway-flow.svg)

### Wie funktioniert es?

Das Gateway steht **vor allen Services**. Es prüft den eingehenden Request und
setzt bei Erfolg einen Header mit den User-Informationen. Die Backend-Services
lesen nur noch diesen Header – sie brauchen keinen JWT-Code.

```
Kunde sendet:   GET /api/orders
                Authorization: Bearer eyJhbGci...

Gateway prüft:  JWT Signatur (via JWKS-Endpoint von Keycloak)
                Ablaufzeit (exp claim)
                Audience (aud claim)

Gateway setzt:  X-User-Id: user-123
                X-User-Email: kunde@example.com
                X-User-Role: customer

Backend empfängt: nur die gesetzten Header – kein JWT mehr
```

### Code-Beispiel: NGINX Ingress + oauth2-proxy

```yaml
# ingress.yaml – auth-url leitet jeden Request zuerst zum oauth2-proxy
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: order-api
  namespace: production
  annotations:
    nginx.ingress.kubernetes.io/auth-url: "https://$host/oauth2/auth"
    nginx.ingress.kubernetes.io/auth-signin: "https://$host/oauth2/start?rd=$escaped_request_uri"
    nginx.ingress.kubernetes.io/auth-response-headers: >-
      X-Auth-Request-User,X-Auth-Request-Email,Authorization
spec:
  ingressClassName: nginx
  rules:
  - host: api.example.com
    http:
      paths:
      - path: /api/
        pathType: Prefix
        backend:
          service:
            name: order-service
            port:
              number: 8080
      - path: /oauth2/
        pathType: Prefix
        backend:
          service:
            name: oauth2-proxy
            port:
              number: 4180
```

```yaml
# oauth2-proxy Deployment
apiVersion: apps/v1
kind: Deployment
metadata:
  name: oauth2-proxy
  namespace: production
spec:
  replicas: 1
  selector:
    matchLabels:
      app: oauth2-proxy
  template:
    metadata:
      labels:
        app: oauth2-proxy
    spec:
      containers:
      - name: oauth2-proxy
        image: quay.io/oauth2-proxy/oauth2-proxy:v7.6.0
        args:
        - --provider=oidc
        - --oidc-issuer-url=https://keycloak.example.com/realms/myrealm
        - --client-id=myapp
        - --redirect-url=https://api.example.com/oauth2/callback
        - --upstream=file:///dev/null   # nur Auth-Check, keine Weiterleitung
        - --set-xauthrequest=true       # X-Auth-Request-User/Email setzen
        - --cookie-secure=true
        - --cookie-httponly=true
        env:
        - name: OAUTH2_PROXY_CLIENT_SECRET
          valueFrom:
            secretKeyRef:
              name: oauth2-proxy-secret
              key: client-secret
        - name: OAUTH2_PROXY_COOKIE_SECRET
          valueFrom:
            secretKeyRef:
              name: oauth2-proxy-secret
              key: cookie-secret
```

### Code-Beispiel: Kong Gateway mit JWT-Plugin

```yaml
# Kong JWT-Plugin – validiert JWT für alle Routen mit diesem Plugin
apiVersion: configuration.konghq.com/v1
kind: KongPlugin
metadata:
  name: jwt-auth
  namespace: production
plugin: jwt
config:
  claims_to_verify:
  - exp
  - nbf
  key_claim_name: kid
---
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: order-api
  namespace: production
  annotations:
    konghq.com/plugins: jwt-auth
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

### Code-Beispiel: Traefik ForwardAuth

```yaml
# Traefik Middleware: ForwardAuth zum oauth2-proxy
apiVersion: traefik.io/v1alpha1
kind: Middleware
metadata:
  name: oauth2-auth
  namespace: production
spec:
  forwardAuth:
    address: http://oauth2-proxy.production.svc.cluster.local:4180/oauth2/auth
    trustForwardHeader: true
    authResponseHeaders:
    - X-Auth-Request-User
    - X-Auth-Request-Email
    - X-Auth-Request-Groups
---
apiVersion: traefik.io/v1alpha1
kind: IngressRoute
metadata:
  name: order-api
  namespace: production
spec:
  entryPoints:
  - websecure
  routes:
  - match: Host(`api.example.com`) && PathPrefix(`/api/`)
    kind: Rule
    middlewares:
    - name: oauth2-auth
    services:
    - name: order-service
      port: 8080
```

### Backend-Service: nur Header lesen

```python
# FastAPI – kein JWT-Code nötig, Gateway hat bereits validiert
from fastapi import FastAPI, Header, HTTPException

app = FastAPI()

@app.get("/api/orders")
def get_orders(
    x_auth_request_user:  str = Header(None),
    x_auth_request_email: str = Header(None),
):
    if not x_auth_request_user:
        raise HTTPException(status_code=401)
    return {"user": x_auth_request_user, "orders": []}
```

```go
// Go – identisch, nur Header lesen
func ordersHandler(w http.ResponseWriter, r *http.Request) {
    user := r.Header.Get("X-Auth-Request-User")
    if user == "" {
        http.Error(w, "Unauthorized", http.StatusUnauthorized)
        return
    }
    // user ist vertrauenswürdig – vom Gateway gesetzt
    fmt.Fprintf(w, `{"user":"%s"}`, user)
}
```

---

## Teil 2: Istio Service Mesh Authentication

### Wann?

Istio löst das Problem, das das API Gateway **nicht** löst: den **Ost-West-Traffic**
zwischen Services. Ohne Istio kann jeder Service im Cluster jeden anderen
Service aufrufen – ohne Authentifizierung, ohne Verschlüsselung.

**Typische Situationen:**
- Service A darf Service B aufrufen, aber Service C nicht
- Alle Verbindungen zwischen Services sollen verschlüsselt sein (mTLS)
- Ein JWT vom Kunden soll auch im Cluster weiterhin gültig sein und geprüft werden
- Zero Trust: jeder Service muss sich ausweisen – auch intern

**Was Istio alleine NICHT löst:**
- Den initialen Login des Kunden (dafür ist Keycloak / der Identity Provider zuständig)
- TLS-Terminierung von außen (dafür ist das Gateway zuständig)

![Istio Service Mesh Authentication Flow](/images/auth-istio-mesh-flow.svg)

### Wie funktioniert es?

Istio injiziert in jeden Pod einen **Envoy Sidecar**. Dieser Proxy sitzt zwischen
der App und dem Netzwerk. Die App selbst merkt nichts davon – alles passiert transparent.

**Zwei Auth-Ebenen in Istio:**

```
PeerAuthentication   → Wer darf mit wem reden? (Service-Identität via mTLS)
RequestAuthentication → Wer ist der Endnutzer?  (JWT-Validierung)
AuthorizationPolicy  → Was ist erlaubt?         (Kombination aus beidem)
```

```
Envoy A (order-service)         Envoy B (payment-service)
        |                               |
        |── TLS ClientHello ──────────>|
        |   mit SVID-Zertifikat        |
        |   (spiffe://cluster/ns/      |
        |    production/sa/order-svc)  |
        |                               |
        |<─ TLS ServerHello ───────────|
        |   mit SVID-Zertifikat        |
        |   (spiffe://cluster/ns/      |
        |    production/sa/payment-svc)|
        |                               |
        |── verschlüsselte Daten ─────>|
        |   (mTLS Tunnel)              |
        |                               |
        App sieht: normaler HTTP-Call  App sieht: normaler HTTP-Call
```

### Code-Beispiel 1: mTLS cluster-weit erzwingen (PeerAuthentication)

```yaml
# Gilt für den gesamten Cluster – kein Plain-Text-HTTP mehr möglich
apiVersion: security.istio.io/v1beta1
kind: PeerAuthentication
metadata:
  name: default
  namespace: istio-system   # istio-system = gilt für alle Namespaces
spec:
  mtls:
    mode: STRICT
```

```bash
# Testen: ohne Istio würde das funktionieren, mit STRICT schlägt es fehl
kubectl run test --image=curlimages/curl --rm -it -- \
  curl http://payment-service.production.svc.cluster.local:9090/health
# → Connection reset (kein mTLS → abgelehnt)
```

### Code-Beispiel 2: JWT vom Kunden validieren (RequestAuthentication)

Istio prüft das JWT automatisch über den JWKS-Endpoint.
Der Service-Code muss das nicht selbst tun.

```yaml
# Istio cached die JWKS Public Keys und prüft jeden eingehenden JWT
apiVersion: security.istio.io/v1beta1
kind: RequestAuthentication
metadata:
  name: customer-jwt
  namespace: production
spec:
  # selector leer = gilt für alle Services im Namespace
  jwtRules:
  - issuer: "https://keycloak.example.com/realms/myrealm"
    jwksUri: "https://keycloak.example.com/realms/myrealm/protocol/openid-connect/certs"
    audiences:
    - "myapp"
    outputClaimToHeaders:
    - header: x-customer-id    # JWT-Claim → HTTP-Header für den Service
      claim: customer_id
    - header: x-user-sub
      claim: sub
    - header: x-user-role
      claim: role
    forwardOriginalToken: false  # rohen JWT nicht ans Backend schicken
```

```bash
# Testen: gültiger Token → 200, kein Token → 403 (wenn AuthorizationPolicy gesetzt)
TOKEN=$(curl -s -X POST https://keycloak.example.com/realms/myrealm/protocol/openid-connect/token \
  -d "client_id=myapp&grant_type=password&username=test&password=test" | jq -r .access_token)

curl -H "Authorization: Bearer $TOKEN" https://api.example.com/api/orders
```

### Code-Beispiel 3: Zugriffskontrolle (AuthorizationPolicy)

```yaml
# Regel 1: Kein JWT → Request ablehnen
apiVersion: security.istio.io/v1beta1
kind: AuthorizationPolicy
metadata:
  name: deny-no-jwt
  namespace: production
spec:
  selector:
    matchLabels:
      app: order-service
  action: DENY
  rules:
  - from:
    - source:
        notRequestPrincipals: ["*"]  # kein JWT vorhanden
---
# Regel 2: Nur bestimmte Rollen dürfen POST
apiVersion: security.istio.io/v1beta1
kind: AuthorizationPolicy
metadata:
  name: order-service-allow
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
        paths: ["/api/orders*"]
    when:
    - key: request.auth.principal
      notValues: [""]               # eingeloggt
  - to:
    - operation:
        methods: ["POST"]
        paths: ["/api/orders"]
    when:
    - key: request.auth.claims[role]
      values: ["customer", "admin"] # nur diese Rollen dürfen bestellen
---
# Regel 3: Ost-West – nur order-service darf payment-service aufrufen
apiVersion: security.istio.io/v1beta1
kind: AuthorizationPolicy
metadata:
  name: payment-service-allow
  namespace: production
spec:
  selector:
    matchLabels:
      app: payment-service
  action: ALLOW
  rules:
  - from:
    - source:
        # SPIFFE ID aus dem ServiceAccount des order-service
        principals:
        - "cluster.local/ns/production/sa/order-service"
    to:
    - operation:
        methods: ["POST"]
        paths: ["/internal/payments"]
```

### Code-Beispiel 4: Backend liest Claims aus Headern

Istio setzt die Claims als Header (via `outputClaimToHeaders`).
Der Service braucht keine JWT-Bibliothek.

```go
// Go – Istio hat JWT validiert, Claims stehen als Header bereit
func createOrderHandler(w http.ResponseWriter, r *http.Request) {
    customerID := r.Header.Get("X-Customer-Id")
    role       := r.Header.Get("X-User-Role")

    if customerID == "" {
        // sollte durch AuthorizationPolicy nie passieren
        http.Error(w, "Unauthorized", http.StatusUnauthorized)
        return
    }

    log.Printf("Order von Kunde %s (Rolle: %s)", customerID, role)
    // Business-Logik ...
    w.WriteHeader(http.StatusCreated)
}
```

```python
# FastAPI – gleich, nur Header lesen
from fastapi import FastAPI, Header, HTTPException

app = FastAPI()

@app.post("/api/orders")
async def create_order(
    x_customer_id: str = Header(None),
    x_user_role:   str = Header(None),
):
    if not x_customer_id:
        raise HTTPException(status_code=401)
    # Istio hat den JWT bereits geprüft – hier nur Business-Logik
    return {"status": "created", "customer": x_customer_id}
```

### Code-Beispiel 5: Installation und Namespace vorbereiten

```bash
# 1. Istio installieren
istioctl install --set profile=default -y

# 2. Namespace für Sidecar-Injection markieren
kubectl label namespace production istio-injection=enabled

# 3. Bereits laufende Pods neu starten (damit Sidecar injiziert wird)
kubectl rollout restart deployment -n production

# 4. Prüfen ob Sidecars laufen (READY = 2/2)
kubectl get pods -n production
# NAME                             READY   STATUS
# order-service-7d9f8b-xk2pq      2/2     Running   ← 2/2 = App + Envoy

# 5. mTLS Status prüfen
istioctl x check-inject -n production
istioctl authn tls-check order-service.production.svc.cluster.local
```

---

## Zusammenspiel: Gateway + Istio

Der typische Produktions-Setup kombiniert beide:

```
Internet
   │
   ▼
┌─────────────────────────────────────────────────────┐
│  API Gateway (NGINX / Kong / Traefik)               │
│  • TLS terminieren                                  │
│  • JWT validieren (JWKS) ODER oauth2-proxy          │
│  • Claims als Header setzen                         │
│  • Rate Limiting, Routing                           │
└───────────────────────┬─────────────────────────────┘
                        │ Header: X-User-Id, X-User-Role
                        ▼
┌─────────────────────────────────────────────────────┐
│  Istio Service Mesh (Namespace: production)         │
│                                                     │
│  ┌──────────────┐   mTLS   ┌──────────────┐        │
│  │ Order Service│ ───────► │ Payment Svc  │        │
│  │ + Envoy      │          │ + Envoy      │        │
│  └──────────────┘          └──────────────┘        │
│         │                                           │
│  AuthorizationPolicy: Wer darf was aufrufen?        │
│  PeerAuthentication: STRICT – kein Plain-Text       │
└─────────────────────────────────────────────────────┘
```

```yaml
# Komplettes Minimalbeispiel: beides zusammen aktivieren
---
# 1. mTLS erzwingen
apiVersion: security.istio.io/v1beta1
kind: PeerAuthentication
metadata:
  name: default
  namespace: istio-system
spec:
  mtls:
    mode: STRICT
---
# 2. JWT am Istio Gateway validieren (zusätzlich zum Ingress-Gateway)
apiVersion: security.istio.io/v1beta1
kind: RequestAuthentication
metadata:
  name: global-jwt
  namespace: production
spec:
  jwtRules:
  - issuer: "https://keycloak.example.com/realms/myrealm"
    jwksUri: "https://keycloak.example.com/realms/myrealm/protocol/openid-connect/certs"
    outputClaimToHeaders:
    - header: x-user-sub
      claim: sub
    - header: x-user-role
      claim: role
---
# 3. Alles ohne JWT ablehnen
apiVersion: security.istio.io/v1beta1
kind: AuthorizationPolicy
metadata:
  name: require-jwt
  namespace: production
spec:
  action: DENY
  rules:
  - from:
    - source:
        notRequestPrincipals: ["*"]
```

---

## Weiterführendes

- [Istio Security Konzepte](https://istio.io/latest/docs/concepts/security/)
- [Istio AuthorizationPolicy Referenz](https://istio.io/latest/docs/reference/config/security/authorization-policy/)
- [oauth2-proxy Dokumentation](https://oauth2-proxy.github.io/oauth2-proxy/)
- [Kong JWT Plugin](https://docs.konghq.com/hub/kong-inc/jwt/)
- [Traefik ForwardAuth](https://doc.traefik.io/traefik/middlewares/http/forwardauth/)
