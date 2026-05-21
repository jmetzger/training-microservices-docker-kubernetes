# Istio Service Mesh Authentication – Nord-Süd und Ost-West

Istio übernimmt Authentication für **beide Verkehrsrichtungen** im Kubernetes-Cluster:
Nord-Süd (externe Clients → Services) und Ost-West (Service → Service).

Ein dediziertes API Gateway (Kong, APISIX, Tyk) ist die **Alternative** – sinnvoll
nur wenn Istio an seine Grenzen stößt.

---

## Istio als primäre Lösung

Istio löst das Auth-Problem auf zwei Ebenen gleichzeitig:

| Ebene | Mechanismus | Schützt |
|-------|------------|---------|
| Nord-Süd | Istio Ingress Gateway + RequestAuthentication | Eingehende Requests von außen |
| Ost-West | PeerAuthentication (mTLS) + AuthorizationPolicy | Service-zu-Service intern |

![Istio übernimmt Nord-Süd und Ost-West](/images/auth-istio-nord-sued.svg)

---

## Teil 1: Nord-Süd mit Istio

### Wie es funktioniert

Der **Istio Ingress Gateway** ist der einzige Einstiegspunkt von außen.
Er terminiert TLS und prüft das JWT via `RequestAuthentication` – direkt
gegen den JWKS-Endpoint von Keycloak. Die Backend-Services bekommen
die verifizierten Claims als HTTP-Header.

```
Mobile App                  Istio Ingress Gateway           Order Service
    │                               │                             │
    │── GET /api/orders             │                             │
    │   Authorization: Bearer JWT ─>│                             │
    │                               │  JWT prüfen (JWKS)          │
    │                               │  Signatur ✓  exp ✓          │
    │                               │  sub → x-user-sub           │
    │                               │  role → x-user-role         │
    │                               │                             │
    │                               │── mTLS ────────────────────>│
    │                               │   x-user-sub: user-42       │
    │                               │   x-user-role: customer     │
    │                               │                             │
    │<───────────────────────── Response ────────────────────────│
```

### Code: Istio Gateway + VirtualService

```yaml
# Gateway: TLS terminieren, Port 443 öffnen
apiVersion: networking.istio.io/v1beta1
kind: Gateway
metadata:
  name: api-gateway
  namespace: production
spec:
  selector:
    istio: ingressgateway
  servers:
  - port:
      number: 443
      name: https
      protocol: HTTPS
    tls:
      mode: SIMPLE
      credentialName: api-tls-cert   # Secret mit TLS-Zertifikat (z.B. via cert-manager)
    hosts:
    - api.example.com
---
# VirtualService: Routing zu den Services
apiVersion: networking.istio.io/v1beta1
kind: VirtualService
metadata:
  name: api-routes
  namespace: production
spec:
  hosts:
  - api.example.com
  gateways:
  - api-gateway
  http:
  - match:
    - uri:
        prefix: /api/orders
    route:
    - destination:
        host: order-service
        port:
          number: 8080
  - match:
    - uri:
        prefix: /api/products
    route:
    - destination:
        host: product-service
        port:
          number: 8080
```

### Code: RequestAuthentication – JWT prüfen

```yaml
# Istio validiert JWT selbst via JWKS – kein Code im Service nötig
apiVersion: security.istio.io/v1beta1
kind: RequestAuthentication
metadata:
  name: customer-jwt
  namespace: production
spec:
  # Kein selector → gilt für alle Services im Namespace
  jwtRules:
  - issuer: "https://keycloak.example.com/realms/myrealm"
    jwksUri: "https://keycloak.example.com/realms/myrealm/protocol/openid-connect/certs"
    audiences:
    - "myapp"
    outputClaimToHeaders:          # Claims → HTTP-Header für Backend-Services
    - header: x-user-sub
      claim: sub
    - header: x-user-role
      claim: role
    - header: x-customer-id
      claim: customer_id
    forwardOriginalToken: false    # rohen JWT nicht ans Backend weitergeben
```

### Code: AuthorizationPolicy – Zugriff steuern (Nord-Süd)

```yaml
# Kein gültiges JWT → ablehnen
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
        notRequestPrincipals: ["*"]   # kein JWT vorhanden
---
# GET für alle eingeloggten User, POST nur für bestimmte Rollen
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
        paths: ["/api/orders*"]
    when:
    - key: request.auth.principal
      notValues: [""]
  - to:
    - operation:
        methods: ["POST"]
        paths: ["/api/orders"]
    when:
    - key: request.auth.claims[role]
      values: ["customer", "admin"]
```

### Code: Backend-Service liest nur Header

```go
// Go – Istio hat JWT validiert, Claims stehen als Header bereit
// Keine JWT-Bibliothek nötig
func createOrderHandler(w http.ResponseWriter, r *http.Request) {
    userSub    := r.Header.Get("X-User-Sub")
    role       := r.Header.Get("X-User-Role")
    customerID := r.Header.Get("X-Customer-Id")

    if userSub == "" {
        http.Error(w, "Unauthorized", http.StatusUnauthorized)
        return
    }
    log.Printf("Order von %s (Rolle: %s, KundeID: %s)", userSub, role, customerID)
    // Business-Logik ...
}
```

```python
# FastAPI – identisch
from fastapi import FastAPI, Header, HTTPException

app = FastAPI()

@app.post("/api/orders")
async def create_order(
    x_user_sub:    str = Header(None),
    x_user_role:   str = Header(None),
    x_customer_id: str = Header(None),
):
    if not x_user_sub:
        raise HTTPException(status_code=401)
    return {"status": "created", "user": x_user_sub, "role": x_user_role}
```

---

## Teil 2: Ost-West mit Istio

### Code: mTLS cluster-weit erzwingen

```yaml
# PeerAuthentication STRICT: kein Plain-Text mehr zwischen Pods
apiVersion: security.istio.io/v1beta1
kind: PeerAuthentication
metadata:
  name: default
  namespace: istio-system   # gilt für gesamten Cluster
spec:
  mtls:
    mode: STRICT
```

### Code: Service-zu-Service Zugriff einschränken

```yaml
# Nur order-service darf payment-service aufrufen
# Identität kommt aus dem Kubernetes ServiceAccount (SPIFFE/SVID)
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
        principals:
        - "cluster.local/ns/production/sa/order-service"
    to:
    - operation:
        methods: ["POST"]
        paths: ["/internal/payments"]
```

```yaml
# ServiceAccounts explizit vergeben – Basis für die SPIFFE-Identität
apiVersion: v1
kind: ServiceAccount
metadata:
  name: order-service
  namespace: production
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: order-service
  namespace: production
spec:
  template:
    spec:
      serviceAccountName: order-service   # → SPIFFE: cluster.local/ns/production/sa/order-service
      containers:
      - name: order-service
        image: my-registry/order-service:1.0
```

### Istio installieren und testen

```bash
# 1. Istio installieren
istioctl install --set profile=default -y

# 2. Namespace für Sidecar-Injection markieren
kubectl label namespace production istio-injection=enabled

# 3. Pods neu starten (Sidecar wird injiziert)
kubectl rollout restart deployment -n production

# 4. Prüfen: 2/2 = App + Envoy Sidecar
kubectl get pods -n production
# order-service-7d9f8b-xk2pq   2/2   Running

# 5. mTLS testen: Plain-Text-Verbindung muss fehlschlagen
kubectl run test --image=curlimages/curl --rm -it -- \
  curl http://payment-service.production.svc.cluster.local:9090/health
# → Connection reset (STRICT mTLS aktiv)

# 6. AuthorizationPolicy testen
kubectl exec -it deploy/order-service -n production -- \
  curl http://payment-service:9090/internal/payments -X POST
# → 200 OK  (order-service SA ist erlaubt)

kubectl exec -it deploy/product-service -n production -- \
  curl http://payment-service:9090/internal/payments -X POST
# → 403 RBAC: access denied  (product-service SA ist nicht erlaubt)
```

---

## Wann zusätzlich ein API Gateway?

Istio ist für die meisten Szenarien ausreichend. Ein API Gateway (Kong, APISIX, Tyk)
kommt hinzu, wenn spezifische API-Management-Features gebraucht werden,
die Istio nicht bietet.

![Wann reicht Istio – wann brauche ich ein API Gateway?](/images/auth-wann-api-gateway.svg)

### Typische Gründe für ein API Gateway

**API-Key-Management:** Externe Partner oder Drittanbieter bekommen API-Keys statt JWTs.
Istio kennt kein Consumer-Konzept mit Key-Verwaltung.

**Developer Portal:** Öffentliche API-Dokumentation, Self-Service-Registrierung,
Key-Generierung für externe Entwickler.

**Request/Response-Transformation:** Body umschreiben, Header hinzufügen/entfernen,
Protokollkonvertierung (REST → gRPC) – in Istio nur über komplexe EnvoyFilter möglich.

### Code: Kong vor Istio (wenn beides gebraucht wird)

```yaml
# Kong läuft als Deployment – der Ingress-Traffic geht durch Kong,
# dann ins Istio Mesh weiter
apiVersion: configuration.konghq.com/v1
kind: KongConsumer
metadata:
  name: partner-app
  namespace: production
  annotations:
    kubernetes.io/ingress.class: kong
username: partner-app
---
# API-Key für den Consumer
apiVersion: v1
kind: Secret
metadata:
  name: partner-app-key
  namespace: production
  labels:
    konghq.com/credential: key-auth
type: Opaque
stringData:
  kongCredType: key-auth
  key: "abc123geheimerschluessel"
---
# Route mit Key-Auth absichern
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: partner-api
  namespace: production
  annotations:
    konghq.com/plugins: key-auth-plugin
spec:
  ingressClassName: kong
  rules:
  - host: api.example.com
    http:
      paths:
      - path: /partner/
        pathType: Prefix
        backend:
          service:
            name: order-service   # Istio Sidecar läuft weiterhin im order-service Pod
            port:
              number: 8080
```

```
Internet
   │  API-Key oder JWT
   ▼
┌──────────────────────────────────┐
│  Kong API Gateway                │
│  API-Key prüfen, Rate Limit,     │
│  X-Consumer-* Header setzen      │
└────────────────┬─────────────────┘
                 │
                 ▼
┌──────────────────────────────────┐
│  Istio Service Mesh              │
│  mTLS Ost-West, AuthPolicy       │
│  (Kong-Pod hat auch Envoy)       │
└──────────────────────────────────┘
```

---

## Weiterführendes

- [Istio Ingress Gateway](https://istio.io/latest/docs/tasks/traffic-management/ingress/ingress-control/)
- [Istio RequestAuthentication](https://istio.io/latest/docs/reference/config/security/request_authentication/)
- [Istio AuthorizationPolicy](https://istio.io/latest/docs/reference/config/security/authorization-policy/)
- [Kong Kubernetes Ingress Controller](https://docs.konghq.com/kubernetes-ingress-controller/)
- [Apache APISIX für Kubernetes](https://apisix.apache.org/docs/ingress-controller/getting-started/)
