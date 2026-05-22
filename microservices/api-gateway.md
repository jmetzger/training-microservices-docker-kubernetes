# API Gateway

## Was ist ein API Gateway?

![API Gateway Übersicht](/images/api-gateway-overview.svg)

Ein API Gateway ist ein zentraler Einstiegspunkt, der alle eingehenden Anfragen von
externen Clients entgegennimmt und sie an die richtigen internen Microservices weiterleitet.

Der Client — egal ob Browser, Mobile App oder ein anderes System — kennt nur eine
einzige Adresse: `api.example.com`. Was dahinter passiert, ist ihm egal.

---

## Was macht ein API Gateway konkret?

API Gateways bieten ein reiches Feature-Set, das weit über einfaches Routing hinausgeht:

| Aufgabe | Beschreibung |
|---------|-------------|
| **Routing** | `/api/users` → User Service, `/api/orders` → Order Service |
| **Authentifizierung** | JWT, OAuth2, API-Keys prüfen bevor die Anfrage den Service erreicht |
| **Autorisierung** | Darf dieser Nutzer auf diese Route zugreifen? |
| **Rate Limiting** | Granular pro Client, Route oder API-Plan (z.B. Free vs. Pro) |
| **TLS-Terminierung** | HTTPS endet am Gateway, intern läuft HTTP |
| **Load Balancing** | Anfragen auf mehrere Instanzen eines Service verteilen |
| **Request-Transformation** | Header umschreiben, Payloads anpassen, Protokolle konvertieren |
| **Logging & Monitoring** | Alle Requests zentral erfassen |
| **Circuit Breaker** | Fehlgeschlagene Services abkapseln, Fallback liefern |
| **API-Versionierung** | `/v1/users` und `/v2/users` parallel betreiben |
| **Developer Portal** | Self-Service für externe Entwickler: Doku, API-Keys, Playground |

---

## Bekannte API Gateways

| Produkt | Einsatz |
|---------|---------|
| **Kong** | Open Source, plugin-basiert, sehr verbreitet; Enterprise-Version mit Developer Portal |
| **APISIX** | Open Source, Apache-Projekt, hohe Performance, ebenfalls plugin-basiert |
| **Tyk** | Open Source, stark bei API-Key-Management und Developer Portal |

---

## API Gateway vs. Service Mesh — was ist der Unterschied?

Ein häufiges Missverständnis: Istio ist rein für Ost-West (intern). Das stimmt nicht ganz.

**Istio kann über sein Ingress Gateway auch Nord-Süd-Traffic (extern → intern) steuern.**
Der entscheidende Unterschied liegt nicht in der Verkehrsrichtung, sondern im Feature-Set.

![Auth und API Gateway Flow](/images/auth-api-gateway-flow.svg)

| | API Gateway | Service Mesh (z.B. Istio) |
|---|---|---|
| **Nord-Süd (extern → intern)** | Ja, Hauptaufgabe | Ja, möglich via Ingress Gateway |
| **Ost-West (intern → intern)** | Nein | Ja, Kernaufgabe |
| **Schwerpunkt** | Externe Clients, Developer-Erfahrung | Service-zu-Service-Kommunikation |
| **Authentifizierung** | OAuth2, API-Keys, JWT für Nutzer/Apps | mTLS zwischen Services |
| **Rate Limiting** | Granular pro Client, Route und Plan | Begrenzt |
| **Developer Portal** | Ja (z.B. Kong, Tyk) | Nein |
| **Request-Transformation** | Ja (Header, Payload, Protokoll) | Begrenzt |
| **API-Versionierung** | Ja | Nein |
| **Observability intern** | Begrenzt | Stark (Tracing, Metriken je Service) |
| **mTLS zwischen Services** | Nein | Ja |

### Wann reicht das Istio Ingress Gateway?

Das Istio Ingress Gateway ist ausreichend, wenn:
- kein Developer Portal benötigt wird
- kein komplexes Rate Limiting nach API-Plänen nötig ist
- die Nutzer intern sind (kein öffentliches API)

Ein dediziertes API Gateway ist sinnvoll, wenn:
- externe Entwickler das API nutzen sollen
- verschiedene API-Pläne (Free, Pro, Enterprise) verwaltet werden
- komplexe Auth-Flows (OAuth2 Authorization Code, API-Key-Verwaltung) nötig sind
- das API als Produkt vermarktet wird

**Faustregel:** Service Mesh und API Gateway schließen sich nicht aus — in produktiven
Setups laufen beide oft zusammen. Das API Gateway übernimmt den Eingang mit reichem
Feature-Set, das Service Mesh sichert und beobachtet die Kommunikation im Cluster.

---

## Zusammenfassung

Ein API Gateway ist kein optionales Extra, sondern bei jeder Microservices-Architektur
mit externen Clients praktisch unverzichtbar.

Der Unterschied zu einem Service Mesh wie Istio liegt nicht darin, dass Istio kein
Nord-Süd-Traffic kann — das kann es. Der Unterschied liegt im Feature-Set: API-Keys,
Developer Portals, granulares Rate Limiting nach Plänen und Request-Transformation
machen ein dediziertes API Gateway zur richtigen Wahl für externe APIs.
