# Istio — Service Mesh Überblick

## Was ist ein Service Mesh?

Ab einer gewissen Anzahl von Microservices entsteht ein fundamentales Problem:
Jeder Service muss sich selbst um **Sicherheit, Fehlertoleranz, Logging und Routing** kümmern.
Das führt zu dupliziertem Code in jedem Service — in unterschiedlichen Sprachen, mit unterschiedlicher Qualität.

Ein **Service Mesh** löst das auf Infrastrukturebene — ohne Änderungen am Applikationscode.

**Istio** ist das bekannteste Service Mesh für Kubernetes.

---

## Architektur

Istio besteht aus zwei Ebenen:

| Ebene | Komponente | Aufgabe |
|---|---|---|
| **Control Plane** | Istiod | Konfiguration, Zertifikate, Service Discovery |
| **Data Plane** | Envoy Proxy (Sidecar) | Übernimmt den gesamten ein/ausgehenden Traffic |

Der **Envoy-Proxy** wird als zweiter Container automatisch in jeden Pod injiziert.
Die Applikation merkt davon nichts — der Traffic läuft transparent durch den Proxy.

![Istio Architektur](/images/istio-architektur.svg)

Aktivierung für einen Namespace:

```
kubectl label namespace default istio-injection=enabled
```

---

## Die 5 Kern-Features

![Istio Features](/images/istio-features.svg)

### 1. mTLS — Mutual TLS

Alle Verbindungen zwischen Services werden **automatisch verschlüsselt und authentifiziert**.
Kein Service kann sich gegenüber einem anderen als jemand anderes ausgeben.

```
# Alle Verbindungen im Namespace verschluesseln
apiVersion: security.istio.io/v1beta1
kind: PeerAuthentication
metadata:
  name: default
spec:
  mtls:
    mode: STRICT
```

### 2. Traffic Management

Feingranulares Routing ohne Ingress-Magie — direkt auf Service-Ebene.

```
# 90% auf v1, 10% auf v2 (Canary Release)
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: backend
spec:
  http:
  - route:
    - destination:
        host: backend
        subset: v1
      weight: 90
    - destination:
        host: backend
        subset: v2
      weight: 10
```

### 3. Observability — ohne Code

Istio generiert automatisch für jeden Service:
- **Metrics** (Requests/s, Latenz, Fehlerrate) → Prometheus
- **Distributed Tracing** → Jaeger
- **Service Graph** (wer spricht mit wem) → Kiali

Kein `import logging` oder SDK-Integration nötig.

### 4. Resilience

Circuit Breaker, Retries und Timeouts werden zentral konfiguriert:

```
apiVersion: networking.istio.io/v1alpha3
kind: DestinationRule
metadata:
  name: backend
spec:
  host: backend
  trafficPolicy:
    outlierDetection:
      consecutive5xxErrors: 3
      interval: 10s
      baseEjectionTime: 30s
```

Dazu: **Fault Injection** zum gezielten Testen von Fehlerszenarien (Chaos Engineering).

### 5. Authorization Policies

Wer darf wen aufrufen — auf Basis von Service-Identitäten, nicht IP-Adressen:

```
# Nur Frontend darf Backend aufrufen
apiVersion: security.istio.io/v1beta1
kind: AuthorizationPolicy
metadata:
  name: backend-allow-frontend
spec:
  selector:
    matchLabels:
      app: backend
  rules:
  - from:
    - source:
        principals: ["cluster.local/ns/default/sa/frontend"]
```

---

## Wann macht Istio Sinn?

| Situation | Empfehlung |
|---|---|
| < 5 Services, monolithisch | Kein Service Mesh nötig |
| 5–15 Services, wachsend | Service Mesh evaluieren |
| > 15 Services, Multi-Team | Service Mesh sinnvoll |
| Compliance/Zero Trust Pflicht | Service Mesh notwendig |

**Kosten:** Istio bringt Komplexität und Ressourcenoverhead (Envoy-Sidecars).
Der Break-Even liegt da, wo der Aufwand für manuelle Cross-Cutting-Concerns größer wird.

---

## Istio ohne Sidecars — Ambient Mesh

Seit Istio 1.18 gibt es den **Ambient Mode**: statt Sidecars ein Node-Level-Proxy (ztunnel).
Weniger Overhead, einfacheres Upgrade — noch in Entwicklung, aber produktionsreif.

Mehr dazu: https://istio.io/latest/blog/2022/introducing-ambient-mesh/
