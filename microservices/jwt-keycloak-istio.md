# JWT mit Keycloak und Istio

## Was ist JWT?

Ein **JSON Web Token (JWT)** ist ein kompaktes, selbstbeschreibendes Token,
das Informationen (Claims) ueber einen User oder Service traegt.
Es besteht aus drei Base64Url-kodierten Teilen, getrennt durch Punkte.

![JWT Aufbau – Header, Payload, Signatur](/images/jwt-aufbau.svg)

| Teil | Inhalt | Zweck |
|------|--------|-------|
| **Header** | Algorithm (RS256), Token-Typ | Wie wurde signiert? |
| **Payload** | sub, email, roles, exp | Wer bin ich, was darf ich? |
| **Signatur** | RSA-signiert mit Keycloak Private Key | Manipulationsschutz |

Die Signatur kann jeder mit dem **oeffentlichen Key** von Keycloak pruefen –
ohne dass Keycloak dafuer erreichbar sein muss.

---

## Weg 1: User meldet sich an (Browser/App)

Der Standard-Flow fuer Webanwendungen heisst **Authorization Code Flow**.
Kein Passwort wandert durch die App – nur ein kurzlebiger "Code".

![Authorization Code Flow – User Login](/images/jwt-login-flow.svg)

### Was passiert Schritt fuer Schritt

| Schritt | Wer | Was |
|---------|-----|-----|
| 1 | Browser | Redirect zu Keycloak Login-Page |
| 2 | Keycloak | User gibt Credentials ein, Keycloak prueft |
| 3 | Keycloak | Redirect zurueck mit `?code=abc123` (kurzlebig, Sekunden) |
| 4 | Browser | Sendet Code an Backend-App |
| 5 | Backend | Tauscht Code gegen Token (`client_id + client_secret`) |
| 6 | Keycloak | Gibt `access_token`, `refresh_token`, `id_token` zurueck |
| 7 | Backend | Speichert JWT in Session oder Cookie |
| 8 | Backend | Jeder API-Call traegt `Authorization: Bearer <JWT>` |

**Warum dieser Umweg ueber einen Code?**
Der eigentliche JWT verlasst nie den Browser. Nur das Backend kennt
den `client_secret` und kann Token anfordern. Das schuetzt vor Token-Leakage
in Browser-History oder Logs.

---

## Weg 2: Service holt sich selbst ein JWT (Service-to-Service)

Wenn kein User beteiligt ist – z.B. ein Cronjob oder Microservice ruft einen
anderen Service auf – gibt es keinen Login-Dialog.
Der Flow heisst **Client Credentials**.

![Client Credentials Flow – Service-to-Service](/images/jwt-client-credentials.svg)

```
POST https://keycloak/realms/myrealm/protocol/openid-connect/token
Content-Type: application/x-www-form-urlencoded

grant_type=client_credentials
&client_id=service-a
&client_secret=geheim123
```

Response:
```json
{
  "access_token": "eyJhbGciOiJSUzI1NiJ9...",
  "expires_in": 300,
  "token_type": "Bearer"
}
```

Der Service speichert den Token und sendet ihn bei jedem Folge-Request mit:

```
GET /api/orders
Authorization: Bearer eyJhbGciOiJSUzI1NiJ9...
```

**Wichtig:** Den Token bis kurz vor Ablauf cachen – nicht bei jedem Call
neu anfordern. `expires_in` gibt die Lebensdauer in Sekunden an.

---

## Wo landet das JWT im Request?

```
GET /api/orders HTTP/1.1
Host: my-service.example.com
Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyLTQyIiwiZW1haWwiOiJ1c2VyQGV4YW1wbGUuY29tIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImFkbWluIl19LCJleHAiOjE3MTYwMDAzMDB9.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

Das Token ist einfach ein langer String im `Authorization`-Header.
Der Empfaenger entschluesselt ihn (kein Netzwerkaufruf noetig) und
liest die Claims direkt aus dem Payload.

---

## Wie Istio das JWT prueft – ohne App-Code-Aenderung

Istio's Envoy-Sidecar interceptet jeden eingehenden Request **bevor**
er den App-Container erreicht. Die Validierung laeuft vollautomatisch.

![Istio JWT-Validierung](/images/jwt-istio-validation.svg)

### Konfiguration: RequestAuthentication

```yaml
apiVersion: security.istio.io/v1beta1
kind: RequestAuthentication
metadata:
  name: jwt-keycloak
  namespace: production
spec:
  jwtRules:
  - issuer: "https://keycloak/realms/myrealm"
    jwksUri: "https://keycloak/realms/myrealm/protocol/openid-connect/certs"
```

Istio holt die **Public Keys automatisch** vom JWKS-Endpoint und prueft:
- Signatur korrekt? (RSA)
- Token nicht abgelaufen? (`exp`)
- Issuer stimmt? (`iss`)

### Konfiguration: AuthorizationPolicy

```yaml
apiVersion: security.istio.io/v1beta1
kind: AuthorizationPolicy
metadata:
  name: require-admin
  namespace: production
spec:
  rules:
  - from:
    - source:
        requestPrincipals: ["*"]
    when:
    - key: request.auth.claims[realm_access/roles]
      values: ["admin"]
```

### Was Istio mit validierten Claims macht

Nach erfolgreicher Pruefung extrahiert Istio Claims als HTTP-Header:

| Claim im JWT | HTTP-Header | Beispielwert |
|-------------|-------------|--------------|
| `sub` | `x-forwarded-client-cert` / `x-user-sub` | `user-42` |
| `email` | `x-user-email` | `user@example.com` |
| `realm_access.roles` | `x-user-role` | `admin` |

Die App-Container lesen einfach diese Header – kein JWT-Parsing,
keine Signaturpruefung im Code.

---

## Zusammenfassung: Wer macht was?

| Komponente | Aufgabe |
|-----------|---------|
| **Keycloak** | Ausstellen und Signieren von JWTs (OIDC-Server) |
| **Browser/App** | Leitet zur Login-Page weiter, tauscht Code gegen Token |
| **Backend-Service** | Speichert Token, sendet im `Authorization`-Header |
| **Microservice** | Holt Token per Client Credentials, cacht ihn |
| **Istio Sidecar** | Validiert JWT, blockiert invalide Requests, extrahiert Claims |
| **App-Container** | Muss JWT nicht kennen – liest nur Header |

**Der Kernvorteil von Istio:** Authentication wird aus dem App-Code
herausgezogen und zentral konfiguriert. Neue Services sind automatisch
geschuetzt, ohne dass Entwickler Auth-Bibliotheken einbinden muessen.
