# API Gateway

## Was ist ein API Gateway?

![API Gateway Übersicht](/images/api-gateway-overview.svg)

Ein API Gateway ist ein zentraler Einstiegspunkt, der alle eingehenden Anfragen von
Clients entgegennimmt und sie an die richtigen internen Microservices weiterleitet.

Der Client — egal ob Browser, Mobile App oder ein anderes System — kennt nur eine
einzige Adresse: `api.example.com`. Was dahinter passiert, ist ihm egal.

---

## Das Problem ohne API Gateway

![Ohne vs. Mit API Gateway](/images/api-gateway-ohne-mit.svg)

**Ohne Gateway** muss jeder Client jeden Service einzeln kennen und direkt
ansprechen:
- Browser ruft `user-service:8081/api/users` auf
- Mobile App ruft `order-service:8082/api/orders` auf
- Jeder Service implementiert selbst: Auth, Logging, Rate Limiting

Das führt zu Doppelarbeit, unübersichtlichen Abhängigkeiten und dazu, dass
jede interne Umstrukturierung auch alle Clients zwingt, sich anzupassen.

**Mit Gateway** gibt es nur noch eine URL. Das Gateway übernimmt die Querschnittsaufgaben
für alle Services zentral.

---

## Was macht ein API Gateway konkret?

| Aufgabe | Beschreibung |
|---------|-------------|
| **Routing** | `/api/users` → User Service, `/api/orders` → Order Service |
| **Authentifizierung** | JWT-Token prüfen, bevor die Anfrage den Service erreicht |
| **Autorisierung** | Darf dieser Nutzer auf diese Route zugreifen? |
| **Rate Limiting** | Max. 100 Anfragen pro Minute pro Client |
| **TLS-Terminierung** | HTTPS endet am Gateway, intern läuft HTTP |
| **Load Balancing** | Anfragen auf mehrere Instanzen eines Service verteilen |
| **Request-Transformation** | Header umschreiben, Payloads anpassen |
| **Logging & Monitoring** | Alle Requests zentral erfassen |
| **Circuit Breaker** | Fehlgeschlagene Services abkapseln, Fallback liefern |

---

## Bekannte API Gateways

| Produkt | Einsatz |
|---------|---------|
| **Kong** | Open Source, plugin-basiert, sehr verbreitet |
| **Traefik** | Cloud-native, automatische Kubernetes-Integration |
| **AWS API Gateway** | Managed Service bei AWS |
| **NGINX** | Klassischer Reverse Proxy, auch als Gateway nutzbar |
| **Istio Ingress Gateway** | Teil des Service Mesh, für interne und externe Routen |

---

## API Gateway vs. Service Mesh — was ist der Unterschied?

Ein häufiges Missverständnis: API Gateway und Service Mesh lösen ähnliche Probleme,
aber auf unterschiedlichen Ebenen.

| | API Gateway | Service Mesh (z.B. Istio) |
|---|---|---|
| **Richtung** | Nord-Süd (außen → innen) | Ost-West (Service → Service) |
| **Für wen?** | Externe Clients | Interne Service-zu-Service-Kommunikation |
| **Authentifizierung** | Nutzer/App gegenüber dem System | Service gegenüber Service (mTLS) |
| **Typischer Einsatz** | Eingangstor für die API | Absicherung im Cluster |

**Faustregel:** Das API Gateway schützt den Eingang. Das Service Mesh schützt
die interne Kommunikation. Beides zusammen ergibt eine vollständige Sicherheitsarchitektur.

---

## Zusammenfassung

Ein API Gateway ist kein optionales Extra, sondern bei jeder Microservices-Architektur
mit externen Clients praktisch unverzichtbar. Es löst das Problem, dass Clients nicht
die interne Struktur kennen müssen, und zentralisiert alle Querschnittsaufgaben
(Auth, Logging, Rate Limiting) an einem einzigen Ort.
