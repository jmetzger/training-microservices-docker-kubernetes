# Authentication in Kubernetes – Externe Clients (Kunde / Mobile App)

## Wer authentifiziert sich wozu?

Zuerst die wichtigste Unterscheidung – es gibt **zwei völlig getrennte Auth-Ebenen**:

![Zwei Auth-Ebenen in Kubernetes](/images/auth-zwei-ebenen.svg)

> **ServiceAccount-Token ist Workload-Identität** – für Pod-zu-Pod oder CI/CD-zu-API-Server.
> Ein Kunde / eine Mobile App bekommt **niemals** einen ServiceAccount-Token zu sehen.

---

## Der kube-apiserver und seine Auth-Mechanismen

Der kube-apiserver ist der **einzige Einstiegspunkt** für alle Kubernetes-Operationen.
Er kennt mehrere Auth-Methoden – aber keine davon ist für Kunden gedacht.

![kube-apiserver Auth-Methoden](/images/auth-kube-apiserver.svg)

### OIDC am kube-apiserver konfigurieren (für Entwickler-Login)

```yaml
# kube-apiserver Flags (z.B. in /etc/kubernetes/manifests/kube-apiserver.yaml)
spec:
  containers:
  - command:
    - kube-apiserver
    - --oidc-issuer-url=https://keycloak.example.com/realms/myrealm
    - --oidc-client-id=kubectl
    - --oidc-username-claim=email
    - --oidc-groups-claim=groups
    # Damit kann ein Entwickler per Keycloak-Login kubectl benutzen
```

```yaml
# RBAC: Entwickler-Gruppe darf Pods lesen
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  name: dev-team-view
subjects:
- kind: Group
  name: "dev-team"          # kommt aus dem groups-Claim im OIDC-Token
  apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: ClusterRole
  name: view
  apiGroup: rbac.authorization.k8s.io
```

---

## Kunde / Mobile App – der richtige Auth-Flow

Ein externer Kunde hat **keinen Zugang zum kube-apiserver**. Er kommuniziert ausschließlich
über den **Ingress** mit den Anwendungs-Services. Der OIDC-Provider stellt JWTs aus,
die am Ingress oder im Service Mesh geprüft werden.

![Kunde / Mobile App Auth-Flow in Kubernetes](/images/auth-kunde-mobile-flow.svg)

---

## JWT-Flow für Mobile App – vollständiger Code

### 1. PKCE Authorization Code Flow (Mobile App / SPA)

```
Mobile App                    Keycloak                   Backend (K8s)
    |                             |                             |
    |── GET /auth?                |                             |
    |   response_type=code        |                             |
    |   code_challenge=S256  ────>|                             |
    |                             |                             |
    |<── Login-Seite (redirect) ──|                             |
    |                             |                             |
    |── User gibt Credentials ───>|                             |
    |                             |                             |
    |<── Authorization Code ──────|                             |
    |                             |                             |
    |── POST /token               |                             |
    |   code + code_verifier ────>|                             |
    |                             |                             |
    |<── Access Token (JWT) ──────|                             |
    |    Refresh Token            |                             |
    |                             |                             |
    |── GET /api/orders           |                             |
    |   Authorization: Bearer JWT |──────────────────────────>  |
    |                             |             JWT prüfen      |
    |                             |             Claims extrahieren
    |<─────────────────────────── Antwort ────────────────────  |
```

### 2. Keycloak Realm + Client einrichten

```bash
# Realm und Client via Keycloak Admin CLI anlegen
kcadm.sh create realms -s realm=myapp -s enabled=true

kcadm.sh create clients -r myapp \
  -s clientId=mobile-app \
  -s 'redirectUris=["myapp://callback", "https://app.example.com/callback"]' \
  -s publicClient=true \
  -s 'standardFlowEnabled=true' \
  -s 'attributes={"pkce.code.challenge.method":"S256"}'

# Custom Claims (z.B. Kundennummer) als Mapper hinzufügen
kcadm.sh create clients/<client-id>/protocol-mappers/models -r myapp \
  -s name=customer-id \
  -s protocolMapper=oidc-user-attribute-mapper \
  -s 'config={"user.attribute":"customerId","claim.name":"customer_id","access.token.claim":"true"}'
```

### 3. Istio: JWT validieren + Claims als Header setzen

```yaml
# RequestAuthentication: Istio prüft das JWT selbst (kein Code im Service nötig)
apiVersion: security.istio.io/v1beta1
kind: RequestAuthentication
metadata:
  name: customer-jwt
  namespace: production
spec:
  jwtRules:
  - issuer: "https://keycloak.example.com/realms/myapp"
    jwksUri: "https://keycloak.example.com/realms/myapp/protocol/openid-connect/certs"
    audiences:
    - "mobile-app"
    outputClaimToHeaders:
    - header: x-customer-id      # claim → HTTP-Header für Backend
      claim: customer_id
    - header: x-user-sub
      claim: sub
    - header: x-user-email
      claim: email
    forwardOriginalToken: false   # JWT nicht ans Backend weitergeben
```

```yaml
# AuthorizationPolicy: Requests OHNE gültiges JWT ablehnen
apiVersion: security.istio.io/v1beta1
kind: AuthorizationPolicy
metadata:
  name: require-customer-jwt
  namespace: production
spec:
  selector:
    matchLabels:
      app: order-service
  action: DENY
  rules:
  - from:
    - source:
        notRequestPrincipals: ["*"]   # kein JWT vorhanden → DENY
---
# Feinere Regel: GET ohne Auth, POST nur mit gültigem JWT
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
        paths: ["/api/v1/products*"]   # Produktliste: öffentlich
  - from:
    - source:
        requestPrincipals: ["*"]        # Eingeloggte Kunden
    to:
    - operation:
        methods: ["GET", "POST"]
        paths: ["/api/v1/orders*"]
```

### 4. Backend-Service liest Claims aus Headern (kein JWT-Parsing)

```go
// order-service/handler.go
// Istio hat das JWT validiert und Claims als Header gesetzt.
// Der Service braucht keine JWT-Bibliothek.
func CreateOrderHandler(w http.ResponseWriter, r *http.Request) {
    customerID := r.Header.Get("X-Customer-Id")
    userSub    := r.Header.Get("X-User-Sub")

    if customerID == "" {
        http.Error(w, "missing customer identity", http.StatusUnauthorized)
        return
    }

    order := Order{
        CustomerID: customerID,
        CreatedBy:  userSub,
    }
    // Datenbanklogik ...
    w.WriteHeader(http.StatusCreated)
    json.NewEncoder(w).Encode(order)
}
```

```python
# payment-service/app.py (FastAPI)
from fastapi import FastAPI, Header, HTTPException

app = FastAPI()

@app.post("/api/v1/payments")
async def create_payment(
    x_customer_id: str = Header(None, alias="x-customer-id"),
    x_user_sub:    str = Header(None, alias="x-user-sub"),
):
    if not x_customer_id:
        raise HTTPException(status_code=401, detail="No identity")

    # x_customer_id kommt gesetzt von Istio – keine weitere Validierung nötig
    return {"status": "ok", "customer": x_customer_id}
```

---

## Token-Refresh in der Mobile App

```swift
// iOS Swift – Token automatisch erneuern
class TokenManager {
    private var accessToken: String?
    private var refreshToken: String?
    private var expiresAt: Date?

    func getValidToken() async throws -> String {
        // Abgelaufen? Neu holen via Refresh Token
        if let exp = expiresAt, Date() > exp.addingTimeInterval(-60) {
            try await refreshAccessToken()
        }
        return accessToken ?? { throw AuthError.notAuthenticated }()
    }

    private func refreshAccessToken() async throws {
        let body = "grant_type=refresh_token&refresh_token=\(refreshToken!)&client_id=mobile-app"
        var req = URLRequest(url: URL(string: "https://keycloak.example.com/realms/myapp/protocol/openid-connect/token")!)
        req.httpMethod = "POST"
        req.httpBody = body.data(using: .utf8)

        let (data, _) = try await URLSession.shared.data(for: req)
        let tokens = try JSONDecoder().decode(TokenResponse.self, from: data)
        accessToken = tokens.accessToken
        expiresAt   = Date().addingTimeInterval(Double(tokens.expiresIn))
    }
}
```

---

## Zusammenfassung: Was validiert was?

![Zusammenfassung: Was validiert was?](/images/auth-zusammenfassung.svg)

---

## Login-Button in einer Web-App

Du baust **kein eigenes Login-Formular**. Keycloak stellt die Login-Seite bereit.
Deine App hat nur einen Button – Klick darauf startet den Redirect zu Keycloak.

![Login-Button Flow mit Keycloak](/images/auth-login-button-flow.svg)

Der entscheidende Punkt: **Ab Schritt ⑪ kontaktiert deine App Keycloak nicht mehr.**
Das JWT wird vom Istio Gateway lokal geprüft (Public Key wurde einmalig geholt und gecacht).

### React-Beispiel mit keycloak-js

```bash
npm install keycloak-js
```

```javascript
// keycloak.js – einmalig initialisieren
import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
  url:      'https://keycloak.example.com',
  realm:    'myrealm',
  clientId: 'my-webapp',
});

export default keycloak;
```

```jsx
// App.jsx
import { useEffect, useState } from 'react';
import keycloak from './keycloak';

export default function App() {
  const [ready, setReady] = useState(false);

  useEffect(() => {
    keycloak
      .init({ onLoad: 'check-sso', pkceMethod: 'S256' })
      .then(() => setReady(true));

    // Token automatisch erneuern bevor es abläuft
    setInterval(() => keycloak.updateToken(60), 30000);
  }, []);

  if (!ready) return <p>Laden...</p>;

  if (!keycloak.authenticated) {
    return <button onClick={() => keycloak.login()}>Login</button>;
  }

  return (
    <div>
      <p>Eingeloggt als {keycloak.tokenParsed.email}</p>
      <button onClick={() => keycloak.logout()}>Logout</button>
      <Orders />
    </div>
  );
}
```

```javascript
// api.js – Token zu jedem API-Call hinzufügen
import keycloak from './keycloak';

export async function fetchOrders() {
  const response = await fetch('/api/orders', {
    headers: {
      Authorization: `Bearer ${keycloak.token}`,
    },
  });
  return response.json();
}
```

```yaml
# Keycloak Client-Konfiguration (Public Client für SPA)
# In Keycloak Admin Console:
# Client ID:        my-webapp
# Client Protocol: openid-connect
# Access Type:     public          ← kein Client-Secret nötig
# Valid Redirect:  https://app.example.com/*
# Web Origins:     https://app.example.com
```

---

## Weiterführendes

- [Keycloak – Mobile App PKCE](https://www.keycloak.org/docs/latest/securing_apps/#_mobile_apps)
- [Istio RequestAuthentication](https://istio.io/latest/docs/reference/config/security/request_authentication/)
- [oauth2-proxy Docs](https://oauth2-proxy.github.io/oauth2-proxy/)
- [Kubernetes OIDC Auth](https://kubernetes.io/docs/reference/access-authn-authz/authentication/#openid-connect-tokens)
- [RFC 7636 – PKCE](https://datatracker.ietf.org/doc/html/rfc7636)
