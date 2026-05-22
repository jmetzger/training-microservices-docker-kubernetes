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
| 1 | Browser | Leitet zu Keycloak weiter — mit `redirect_uri=https://backend.com/callback` |
| 2 | Keycloak | Zeigt Login-Formular, prueft Credentials |
| 3 | Keycloak | Antwortet mit **HTTP 302 Redirect** zu `https://backend.com/callback?code=abc123` |
| 4 | Browser | **Folgt dem Redirect automatisch** — ladet die Callback-URL des Backends (Code steckt im Query-Parameter) |
| 5 | Backend | Schickt `code` + `client_secret` an Keycloak (Server-to-Server, Browser nicht beteiligt) |
| 6 | Keycloak | **Stellt den JWT aus** — prueft code, generiert Token, signiert ihn mit Private Key, schickt ihn ans Backend |
| 7 | Backend | Speichert JWT in Session oder Cookie |

**Der entscheidende Mechanismus in Schritt 3–4:**
Keycloak schickt keinen Token an den Browser — es schickt nur einen **Redirect**.
Der Browser folgt diesem Redirect blind und landet auf der Callback-URL des Backends.
Dabei uebertraegt er den `code` als URL-Parameter. Der Browser "weiss" nicht, was er tut —
er folgt nur einer HTTP-Weiterleitung.

**Warum laufen Schritt 5–6 am Browser vorbei?**
Das Backend ruft Keycloak direkt auf — ohne Browser-Beteiligung.
Dabei sendet es den `client_secret`, der **niemals** den Server verlassen darf.
Der Browser bekommt den `access_token` nie zu sehen — nur das Backend haelt ihn.

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
