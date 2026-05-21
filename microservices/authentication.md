# Authentication in Kubernetes – Externe Clients (Kunde / Mobile App)

## Wer authentifiziert sich wozu?

Zuerst die wichtigste Unterscheidung – es gibt **zwei völlig getrennte Auth-Ebenen**:

```svg
<svg viewBox="0 0 740 310" xmlns="http://www.w3.org/2000/svg" font-family="monospace" font-size="12">
  <defs>
    <marker id="a" markerWidth="10" markerHeight="7" refX="9" refY="3.5" orient="auto">
      <polygon points="0 0,10 3.5,0 7" fill="#555"/>
    </marker>
  </defs>

  <!-- Title -->
  <text x="370" y="22" text-anchor="middle" fill="#2C3E50" font-size="14" font-weight="bold">Zwei Auth-Ebenen in Kubernetes</text>

  <!-- Left: Kunden-Ebene -->
  <rect x="10" y="35" width="340" height="250" rx="10" fill="#e8f5e9" stroke="#27AE60" stroke-width="2"/>
  <text x="180" y="58" text-anchor="middle" fill="#1a6b3a" font-weight="bold" font-size="13">① Anwendungs-Ebene</text>
  <text x="180" y="74" text-anchor="middle" fill="#1a6b3a" font-size="11">(Kunden, Mobile Apps, Browser)</text>

  <rect x="25" y="88" width="90" height="40" rx="6" fill="#4A90D9" stroke="#2C5F8A" stroke-width="2"/>
  <text x="70" y="113" text-anchor="middle" fill="white" font-weight="bold">Kunde /</text>
  <text x="70" y="128" text-anchor="middle" fill="white" font-weight="bold">Mobile App</text>

  <line x1="115" y1="108" x2="160" y2="108" stroke="#555" stroke-width="2" marker-end="url(#a)"/>
  <text x="138" y="102" text-anchor="middle" fill="#E67E22" font-size="10">JWT/OAuth2</text>

  <rect x="160" y="88" width="110" height="40" rx="6" fill="#E67E22" stroke="#9A5A0A" stroke-width="2"/>
  <text x="215" y="108" text-anchor="middle" fill="white" font-weight="bold">Ingress /</text>
  <text x="215" y="123" text-anchor="middle" fill="white" font-weight="bold">Istio Gateway</text>

  <line x1="215" y1="128" x2="215" y2="158" stroke="#555" stroke-width="2" marker-end="url(#a)"/>

  <rect x="160" y="158" width="110" height="40" rx="6" fill="#27AE60" stroke="#1A6B3A" stroke-width="2"/>
  <text x="215" y="178" text-anchor="middle" fill="white" font-weight="bold">Microservices</text>
  <text x="215" y="193" text-anchor="middle" fill="#dff" font-size="10">(Anwendungs-Code)</text>

  <text x="180" y="240" text-anchor="middle" fill="#1a6b3a" font-size="11" font-weight="bold">Identity Provider:</text>
  <text x="180" y="256" text-anchor="middle" fill="#1a6b3a" font-size="11">Keycloak / Auth0 / Cognito</text>
  <text x="180" y="272" text-anchor="middle" fill="#888" font-size="10">→ stellt JWTs für Kunden aus</text>

  <!-- Right: Ops-Ebene -->
  <rect x="390" y="35" width="340" height="250" rx="10" fill="#e8f4fd" stroke="#4A90D9" stroke-width="2"/>
  <text x="560" y="58" text-anchor="middle" fill="#2C5F8A" font-weight="bold" font-size="13">② Kubernetes-Ebene</text>
  <text x="560" y="74" text-anchor="middle" fill="#2C5F8A" font-size="11">(Entwickler, CI/CD, Operatoren)</text>

  <rect x="405" y="88" width="90" height="40" rx="6" fill="#8E44AD" stroke="#5B2B72" stroke-width="2"/>
  <text x="450" y="108" text-anchor="middle" fill="white" font-weight="bold">Entwickler /</text>
  <text x="450" y="123" text-anchor="middle" fill="white" font-weight="bold">CI Pipeline</text>

  <line x1="495" y1="108" x2="545" y2="108" stroke="#555" stroke-width="2" marker-end="url(#a)"/>
  <text x="520" y="102" text-anchor="middle" fill="#4A90D9" font-size="10">kubeconfig</text>

  <rect x="545" y="88" width="120" height="40" rx="6" fill="#264653" stroke="#1a2f38" stroke-width="2"/>
  <text x="605" y="108" text-anchor="middle" fill="white" font-weight="bold">kube-apiserver</text>
  <text x="605" y="123" text-anchor="middle" fill="#aad4e5" font-size="10">kubectl / API-Calls</text>

  <line x1="605" y1="128" x2="605" y2="158" stroke="#555" stroke-width="2" marker-end="url(#a)"/>

  <rect x="545" y="158" width="120" height="40" rx="6" fill="#27AE60" stroke="#1A6B3A" stroke-width="2"/>
  <text x="605" y="178" text-anchor="middle" fill="white" font-weight="bold">RBAC-geschützte</text>
  <text x="605" y="193" text-anchor="middle" fill="#dff" font-size="10">Kubernetes-Ressourcen</text>

  <text x="560" y="240" text-anchor="middle" fill="#2C5F8A" font-size="11" font-weight="bold">Auth-Methoden:</text>
  <text x="560" y="256" text-anchor="middle" fill="#2C5F8A" font-size="11">Zertifikate / OIDC / SA-Token</text>
  <text x="560" y="272" text-anchor="middle" fill="#888" font-size="10">→ ServiceAccount für Workloads</text>
</svg>
```

> **ServiceAccount-Token ist Workload-Identität** – für Pod-zu-Pod oder CI/CD-zu-API-Server.
> Ein Kunde / eine Mobile App bekommt **niemals** einen ServiceAccount-Token zu sehen.

---

## Der kube-apiserver und seine Auth-Mechanismen

Der kube-apiserver ist der **einzige Einstiegspunkt** für alle Kubernetes-Operationen.
Er kennt mehrere Auth-Methoden – aber keine davon ist für Kunden gedacht.

```svg
<svg viewBox="0 0 740 380" xmlns="http://www.w3.org/2000/svg" font-family="monospace" font-size="12">
  <defs>
    <marker id="b" markerWidth="10" markerHeight="7" refX="9" refY="3.5" orient="auto">
      <polygon points="0 0,10 3.5,0 7" fill="#555"/>
    </marker>
  </defs>

  <!-- kube-apiserver center -->
  <rect x="270" y="150" width="200" height="80" rx="10" fill="#264653" stroke="#1a2f38" stroke-width="3"/>
  <text x="370" y="182" text-anchor="middle" fill="white" font-size="14" font-weight="bold">kube-apiserver</text>
  <text x="370" y="200" text-anchor="middle" fill="#aad4e5" font-size="11">:6443 (HTTPS)</text>
  <text x="370" y="216" text-anchor="middle" fill="#aad4e5" font-size="11">→ prüft Auth → RBAC</text>

  <!-- Auth method 1: Zertifikate -->
  <rect x="10" y="30" width="160" height="65" rx="6" fill="#E74C3C" stroke="#9B2222" stroke-width="2"/>
  <text x="90" y="52" text-anchor="middle" fill="white" font-weight="bold">Client-Zertifikat</text>
  <text x="90" y="68" text-anchor="middle" fill="#fdd" font-size="10">kubeconfig: client-cert</text>
  <text x="90" y="83" text-anchor="middle" fill="#fdd" font-size="10">→ Admin / Bootstrapping</text>
  <line x1="170" y1="62" x2="268" y2="172" stroke="#E74C3C" stroke-width="1.5" stroke-dasharray="4,3" marker-end="url(#b)"/>

  <!-- Auth method 2: ServiceAccount Token -->
  <rect x="10" y="130" width="160" height="65" rx="6" fill="#E67E22" stroke="#9A5A0A" stroke-width="2"/>
  <text x="90" y="152" text-anchor="middle" fill="white" font-weight="bold">ServiceAccount Token</text>
  <text x="90" y="168" text-anchor="middle" fill="#ffe" font-size="10">projected volume in Pod</text>
  <text x="90" y="183" text-anchor="middle" fill="#ffe" font-size="10">→ Pods / CI-Pipelines</text>
  <line x1="170" y1="162" x2="268" y2="182" stroke="#E67E22" stroke-width="1.5" stroke-dasharray="4,3" marker-end="url(#b)"/>

  <!-- Auth method 3: OIDC -->
  <rect x="10" y="230" width="160" height="65" rx="6" fill="#8E44AD" stroke="#5B2B72" stroke-width="2"/>
  <text x="90" y="252" text-anchor="middle" fill="white" font-weight="bold">OIDC Token</text>
  <text x="90" y="268" text-anchor="middle" fill="#ddd" font-size="10">--oidc-issuer-url Flag</text>
  <text x="90" y="283" text-anchor="middle" fill="#ddd" font-size="10">→ Entwickler via SSO</text>
  <line x1="170" y1="262" x2="268" y2="205" stroke="#8E44AD" stroke-width="1.5" stroke-dasharray="4,3" marker-end="url(#b)"/>

  <!-- Auth method 4: Webhook -->
  <rect x="10" y="320" width="160" height="50" rx="6" fill="#7f8c8d" stroke="#5d6d7e" stroke-width="2"/>
  <text x="90" y="342" text-anchor="middle" fill="white" font-weight="bold">Webhook Auth</text>
  <text x="90" y="358" text-anchor="middle" fill="#ddd" font-size="10">→ externe Systeme</text>
  <line x1="170" y1="345" x2="268" y2="218" stroke="#7f8c8d" stroke-width="1.5" stroke-dasharray="4,3" marker-end="url(#b)"/>

  <!-- RBAC -->
  <rect x="490" y="100" width="150" height="60" rx="6" fill="#27AE60" stroke="#1A6B3A" stroke-width="2"/>
  <text x="565" y="126" text-anchor="middle" fill="white" font-weight="bold">RBAC</text>
  <text x="565" y="142" text-anchor="middle" fill="#dff" font-size="10">Role / ClusterRole</text>
  <line x1="470" y1="180" x2="488" y2="140" stroke="#264653" stroke-width="2" marker-end="url(#b)"/>

  <!-- Kubernetes Resources -->
  <rect x="490" y="200" width="150" height="100" rx="6" fill="#ecf0f1" stroke="#bdc3c7" stroke-width="2"/>
  <text x="565" y="225" text-anchor="middle" fill="#333" font-weight="bold">Kubernetes-Objekte</text>
  <text x="565" y="245" text-anchor="middle" fill="#555" font-size="11">Pods, Deployments</text>
  <text x="565" y="261" text-anchor="middle" fill="#555" font-size="11">Services, Secrets</text>
  <text x="565" y="277" text-anchor="middle" fill="#555" font-size="11">ConfigMaps ...</text>
  <line x1="565" y1="160" x2="565" y2="198" stroke="#264653" stroke-width="2" marker-end="url(#b)"/>

  <!-- Red X annotation -->
  <rect x="490" y="320" width="230" height="50" rx="5" fill="#fdecea" stroke="#E74C3C" stroke-width="1.5"/>
  <text x="605" y="342" text-anchor="middle" fill="#c0392b" font-weight="bold">Kunden / Mobile Apps:</text>
  <text x="605" y="358" text-anchor="middle" fill="#c0392b" font-size="11">haben KEINEN Zugang zum API-Server</text>
</svg>
```

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

```svg
<svg viewBox="0 0 760 440" xmlns="http://www.w3.org/2000/svg" font-family="monospace" font-size="12">
  <defs>
    <marker id="c" markerWidth="10" markerHeight="7" refX="9" refY="3.5" orient="auto">
      <polygon points="0 0,10 3.5,0 7" fill="#555"/>
    </marker>
    <marker id="cg" markerWidth="10" markerHeight="7" refX="9" refY="3.5" orient="auto">
      <polygon points="0 0,10 3.5,0 7" fill="#27ae60"/>
    </marker>
    <marker id="cp" markerWidth="10" markerHeight="7" refX="9" refY="3.5" orient="auto">
      <polygon points="0 0,10 3.5,0 7" fill="#8E44AD"/>
    </marker>
  </defs>

  <!-- Internet zone -->
  <rect x="5" y="5" width="175" height="420" rx="10" fill="#fdecea" stroke="#E74C3C" stroke-width="2" stroke-dasharray="5,3"/>
  <text x="92" y="22" text-anchor="middle" fill="#E74C3C" font-weight="bold">Internet</text>

  <!-- Mobile App -->
  <rect x="20" y="35" width="145" height="60" rx="8" fill="#4A90D9" stroke="#2C5F8A" stroke-width="2"/>
  <text x="92" y="60" text-anchor="middle" fill="white" font-weight="bold">Kunde /</text>
  <text x="92" y="75" text-anchor="middle" fill="white" font-weight="bold">Mobile App</text>
  <text x="92" y="88" text-anchor="middle" fill="#ddf" font-size="10">React Native / Flutter</text>

  <!-- Keycloak (can be external or in cluster) -->
  <rect x="20" y="130" width="145" height="65" rx="8" fill="#8E44AD" stroke="#5B2B72" stroke-width="2"/>
  <text x="92" y="153" text-anchor="middle" fill="white" font-weight="bold">Keycloak / Auth0</text>
  <text x="92" y="169" text-anchor="middle" fill="#ddd" font-size="11">OIDC Provider</text>
  <text x="92" y="183" text-anchor="middle" fill="#ddd" font-size="10">stellt JWT aus</text>

  <!-- Arrows between App and Keycloak -->
  <line x1="92" y1="95" x2="92" y2="128" stroke="#8E44AD" stroke-width="2" marker-end="url(#cp)"/>
  <text x="130" y="115" fill="#8E44AD" font-size="10">① Login</text>
  <line x1="92" y1="195" x2="92" y2="218" stroke="#8E44AD" stroke-width="2" stroke-dasharray="3,2" marker-end="url(#cp)"/>
  <text x="130" y="212" fill="#8E44AD" font-size="10">② JWT</text>

  <!-- Mobile App -> Ingress API call -->
  <line x1="165" y1="55" x2="235" y2="55" stroke="#555" stroke-width="2" marker-end="url(#c)"/>
  <text x="200" y="47" text-anchor="middle" fill="#555" font-size="10">③ API-Call</text>
  <text x="200" y="62" text-anchor="middle" fill="#E67E22" font-size="10">Bearer JWT</text>

  <!-- Kubernetes Cluster -->
  <rect x="200" y="5" width="545" height="420" rx="10" fill="#f0f4f8" stroke="#4A90D9" stroke-width="2" stroke-dasharray="6,3"/>
  <text x="473" y="22" text-anchor="middle" fill="#4A90D9" font-weight="bold">Kubernetes Cluster</text>

  <!-- Ingress / Istio Gateway -->
  <rect x="215" y="35" width="145" height="80" rx="8" fill="#E67E22" stroke="#9A5A0A" stroke-width="2"/>
  <text x="287" y="58" text-anchor="middle" fill="white" font-weight="bold">Ingress /</text>
  <text x="287" y="73" text-anchor="middle" fill="white" font-weight="bold">Istio Gateway</text>
  <text x="287" y="90" text-anchor="middle" fill="#ffe" font-size="10">JWT prüfen (JWKS)</text>
  <text x="287" y="104" text-anchor="middle" fill="#ffe" font-size="10">TLS terminieren</text>

  <!-- JWKS fetch -->
  <line x1="215" y1="75" x2="165" y2="162" stroke="#8E44AD" stroke-width="1.5" stroke-dasharray="4,3" marker-end="url(#cp)"/>
  <text x="175" y="128" fill="#8E44AD" font-size="10">JWKS</text>
  <text x="165" y="140" fill="#8E44AD" font-size="10">(Public Keys)</text>

  <!-- Istio / Auth layer -->
  <rect x="215" y="145" width="145" height="75" rx="8" fill="#E74C3C" stroke="#9B2222" stroke-width="2"/>
  <text x="287" y="168" text-anchor="middle" fill="white" font-weight="bold">Istio /</text>
  <text x="287" y="183" text-anchor="middle" fill="white" font-weight="bold">oauth2-proxy</text>
  <text x="287" y="200" text-anchor="middle" fill="#fdd" font-size="10">RequestAuthentication</text>
  <text x="287" y="214" text-anchor="middle" fill="#fdd" font-size="10">AuthorizationPolicy</text>

  <line x1="287" y1="115" x2="287" y2="143" stroke="#555" stroke-width="2" marker-end="url(#c)"/>

  <!-- Services -->
  <rect x="420" y="60" width="145" height="60" rx="8" fill="#27AE60" stroke="#1A6B3A" stroke-width="2"/>
  <text x="492" y="85" text-anchor="middle" fill="white" font-weight="bold">Order Service</text>
  <text x="492" y="101" text-anchor="middle" fill="#dff" font-size="10">liest Claims aus Header</text>

  <rect x="420" y="160" width="145" height="60" rx="8" fill="#27AE60" stroke="#1A6B3A" stroke-width="2"/>
  <text x="492" y="185" text-anchor="middle" fill="white" font-weight="bold">Payment Service</text>
  <text x="492" y="201" text-anchor="middle" fill="#dff" font-size="10">liest Claims aus Header</text>

  <rect x="420" y="260" width="145" height="60" rx="8" fill="#27AE60" stroke="#1A6B3A" stroke-width="2"/>
  <text x="492" y="285" text-anchor="middle" fill="white" font-weight="bold">Product Service</text>
  <text x="492" y="301" text-anchor="middle" fill="#dff" font-size="10">liest Claims aus Header</text>

  <!-- Arrows to services -->
  <line x1="360" y1="175" x2="418" y2="90" stroke="#27ae60" stroke-width="2.5" marker-end="url(#cg)"/>
  <line x1="360" y1="185" x2="418" y2="190" stroke="#27ae60" stroke-width="2.5" marker-end="url(#cg)"/>
  <line x1="360" y1="195" x2="418" y2="285" stroke="#27ae60" stroke-width="2.5" marker-end="url(#cg)"/>
  <text x="395" y="172" fill="#27ae60" font-size="10">Claims</text>
  <text x="395" y="183" fill="#27ae60" font-size="10">als Header</text>

  <!-- mTLS between services -->
  <line x1="565" y1="120" x2="565" y2="158" stroke="#27ae60" stroke-width="2.5" stroke-dasharray="5,2" marker-end="url(#cg)"/>
  <line x1="565" y1="220" x2="565" y2="258" stroke="#27ae60" stroke-width="2.5" stroke-dasharray="5,2" marker-end="url(#cg)"/>
  <text x="590" y="143" fill="#27ae60" font-size="10">mTLS</text>
  <text x="590" y="243" fill="#27ae60" font-size="10">mTLS</text>

  <!-- Databases -->
  <rect x="610" y="60" width="120" height="50" rx="6" fill="#7f8c8d" stroke="#5d6d7e" stroke-width="2"/>
  <text x="670" y="90" text-anchor="middle" fill="white" font-weight="bold">Orders DB</text>
  <line x1="565" y1="90" x2="608" y2="90" stroke="#555" stroke-width="1.5" marker-end="url(#c)"/>

  <rect x="610" y="155" width="120" height="50" rx="6" fill="#7f8c8d" stroke="#5d6d7e" stroke-width="2"/>
  <text x="670" y="185" text-anchor="middle" fill="white" font-weight="bold">Payments DB</text>
  <line x1="565" y1="190" x2="608" y2="190" stroke="#555" stroke-width="1.5" marker-end="url(#c)"/>

  <!-- kube-apiserver -->
  <rect x="420" y="360" width="145" height="50" rx="8" fill="#264653" stroke="#1a2f38" stroke-width="2"/>
  <text x="492" y="381" text-anchor="middle" fill="white" font-weight="bold">kube-apiserver</text>
  <text x="492" y="397" text-anchor="middle" fill="#aad4e5" font-size="10">:6443 – NUR für Ops/CI</text>

  <!-- Red barrier between internet and apiserver -->
  <line x1="200" y1="385" x2="418" y2="385" stroke="#E74C3C" stroke-width="3" stroke-dasharray="6,3"/>
  <text x="310" y="400" text-anchor="middle" fill="#E74C3C" font-size="11" font-weight="bold">✗ Kein Zugang für externe Kunden</text>

  <!-- Istiod -->
  <rect x="215" y="290" width="145" height="50" rx="8" fill="#264653" stroke="#1a2f38" stroke-width="2"/>
  <text x="287" y="312" text-anchor="middle" fill="white" font-weight="bold">Istiod</text>
  <text x="287" y="328" text-anchor="middle" fill="#aad4e5" font-size="10">Certs verteilen (SVID)</text>
</svg>
```

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

```svg
<svg viewBox="0 0 740 310" xmlns="http://www.w3.org/2000/svg" font-family="monospace" font-size="12">
  <!-- Header -->
  <rect x="10" y="10" width="720" height="35" rx="5" fill="#2C3E50"/>
  <text x="155" y="33" text-anchor="middle" fill="white" font-weight="bold">Komponente</text>
  <text x="340" y="33" text-anchor="middle" fill="white" font-weight="bold">Validiert</text>
  <text x="510" y="33" text-anchor="middle" fill="white" font-weight="bold">Für wen</text>
  <text x="660" y="33" text-anchor="middle" fill="white" font-weight="bold">Ergebnis</text>

  <!-- Row 1 -->
  <rect x="10" y="45" width="720" height="48" fill="#fdecea" stroke="#dee2e6"/>
  <text x="155" y="65" text-anchor="middle" fill="#333" font-weight="bold">Keycloak / Auth0</text>
  <text x="155" y="81" text-anchor="middle" fill="#555" font-size="11">OIDC Provider</text>
  <text x="340" y="73" text-anchor="middle" fill="#555">Username + Passwort</text>
  <text x="510" y="73" text-anchor="middle" fill="#555">Kunden, Entwickler</text>
  <text x="660" y="73" text-anchor="middle" fill="#27ae60">→ JWT ausstellen</text>

  <!-- Row 2 -->
  <rect x="10" y="93" width="720" height="48" fill="#fff" stroke="#dee2e6"/>
  <text x="155" y="113" text-anchor="middle" fill="#333" font-weight="bold">Ingress / Istio GW</text>
  <text x="155" y="129" text-anchor="middle" fill="#555" font-size="11">RequestAuthentication</text>
  <text x="340" y="121" text-anchor="middle" fill="#555">JWT Signatur + Ablauf</text>
  <text x="510" y="121" text-anchor="middle" fill="#555">Externe Kunden</text>
  <text x="660" y="121" text-anchor="middle" fill="#27ae60">→ Claims → Header</text>

  <!-- Row 3 -->
  <rect x="10" y="141" width="720" height="48" fill="#e8f5e9" stroke="#27ae60" stroke-width="1.5"/>
  <text x="155" y="161" text-anchor="middle" fill="#1a6b3a" font-weight="bold">Istio AuthPolicy</text>
  <text x="155" y="177" text-anchor="middle" fill="#1a6b3a" font-size="11">Deklarative Regeln</text>
  <text x="340" y="169" text-anchor="middle" fill="#333">Claims (role, sub, ...)</text>
  <text x="510" y="169" text-anchor="middle" fill="#333">pro Route / Methode</text>
  <text x="660" y="169" text-anchor="middle" fill="#27ae60">→ ALLOW / DENY</text>

  <!-- Row 4 -->
  <rect x="10" y="189" width="720" height="48" fill="#fff" stroke="#dee2e6"/>
  <text x="155" y="209" text-anchor="middle" fill="#333" font-weight="bold">kube-apiserver</text>
  <text x="155" y="225" text-anchor="middle" fill="#555" font-size="11">Kubernetes RBAC</text>
  <text x="340" y="217" text-anchor="middle" fill="#555">Zertifikat / SA-Token / OIDC</text>
  <text x="510" y="217" text-anchor="middle" fill="#E74C3C" font-weight="bold">NUR Ops / CI/CD</text>
  <text x="660" y="217" text-anchor="middle" fill="#27ae60">→ K8s-Operationen</text>

  <!-- Row 5 -->
  <rect x="10" y="237" width="720" height="48" fill="#f8f9fa" stroke="#dee2e6"/>
  <text x="155" y="257" text-anchor="middle" fill="#333" font-weight="bold">Backend-Service</text>
  <text x="155" y="273" text-anchor="middle" fill="#555" font-size="11">(eigener Code)</text>
  <text x="340" y="265" text-anchor="middle" fill="#555">Header lesen (X-Customer-Id)</text>
  <text x="510" y="265" text-anchor="middle" fill="#555">Business-Logik</text>
  <text x="660" y="265" text-anchor="middle" fill="#27ae60">→ Antwort</text>

  <!-- Note -->
  <rect x="10" y="290" width="720" height="18" rx="4" fill="#e8f4fd" stroke="#4A90D9"/>
  <text x="370" y="303" text-anchor="middle" fill="#2C5F8A" font-size="11">ServiceAccount-Token = Workload-Identität für Pods/CI → niemals für externe Kunden</text>
</svg>
```

---

## Weiterführendes

- [Keycloak – Mobile App PKCE](https://www.keycloak.org/docs/latest/securing_apps/#_mobile_apps)
- [Istio RequestAuthentication](https://istio.io/latest/docs/reference/config/security/request_authentication/)
- [oauth2-proxy Docs](https://oauth2-proxy.github.io/oauth2-proxy/)
- [Kubernetes OIDC Auth](https://kubernetes.io/docs/reference/access-authn-authz/authentication/#openid-connect-tokens)
- [RFC 7636 – PKCE](https://datatracker.ietf.org/doc/html/rfc7636)
