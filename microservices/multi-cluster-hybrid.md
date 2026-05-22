# Best Practices fuer Multi-Cluster- und Hybrid-Umgebungen

## Warum ueberhaupt mehrere Cluster?

Ein einzelner Kubernetes-Cluster hat praktische Grenzen:

- **Skalierbarkeit**: Ab ca. 5.000 Nodes wird ein einzelner Cluster unhandlich
- **Isolation**: Prod und Dev im gleichen Cluster teilen das Schicksal bei einem Ausfall
- **Compliance**: Daten in der EU, Compute in den USA — rechtlich oft nicht mischbar
- **Verfuegbarkeit**: Ein Cluster = ein Single Point of Failure

```
Single Cluster:                  Multi-Cluster:

  ┌─────────────────┐            ┌──────────┐   ┌──────────┐
  │   ALLES drin    │            │  Prod EU │   │  Prod US │
  │  Prod + Dev     │            └──────────┘   └──────────┘
  │  EU + US        │                  │               │
  │  Alle Teams     │            ┌──────────┐   ┌──────────┐
  └─────────────────┘            │  Staging │   │    Dev   │
    → Ein Ausfall trifft alles   └──────────┘   └──────────┘
                                  → Isolation, Geo-Verteilung
```

---

## Die drei Multi-Cluster-Muster

### Muster 1: Umgebungs-Trennung (Dev/Staging/Prod)

Das einfachste und haeufigste Muster: Ein Cluster pro Umgebung.

```
dev-cluster          staging-cluster       prod-cluster
─────────────        ───────────────       ────────────
Entwickler           Integrationstests     Echte Nutzer
experimentieren      vor Release           RBAC streng
frei                 gleiche Configs       PodDisruptionBudget
```

**Wann sinnvoll:**
- Wenn Prod-Stabilitat kritisch ist
- Wenn Compliance getrennte Umgebungen fordert
- Einfach zu verstehen und zu betreiben

**Herausforderung:** Gleiche Manifests muessen in mehrere Cluster deployt werden
→ Loesung: GitOps (ArgoCD ApplicationSet, Flux Kustomization)

---

### Muster 2: Geografische Verteilung (Active-Active)

Mehrere Cluster in verschiedenen Regionen, alle nehmen Traffic an.

```
           ┌─────────────────┐
           │  Global LB /    │
           │  DNS-Routing    │
           └────────┬────────┘
                    │
          ┌─────────┴──────────┐
          │                    │
   ┌──────▼──────┐    ┌────────▼────┐
   │ cluster-eu  │    │ cluster-us  │
   │ Frankfurt   │    │ Virginia    │
   └─────────────┘    └─────────────┘
     EU-Nutzer           US-Nutzer
     GDPR-konform        CCPA-konform
```

**Was wird repliziert:**
- Stateless Services: problemlos in beiden Clustern
- Stateful Services (Datenbanken): komplexer — oft regionale DBs mit Replikation

**Wann sinnvoll:**
- Globale Anwendungen mit Latenz-Anforderungen
- Compliance erfordert Datensouveraenitat
- Hochverfuegbarkeit auch bei Regions-Ausfall

---

### Muster 3: Workload-Spezialisierung

Verschiedene Cluster fuer verschiedene Workload-Typen.

```
gpu-cluster          batch-cluster         web-cluster
───────────          ─────────────         ───────────
ML-Training          Nacht-Jobs            APIs, UIs
A100/H100 GPUs       Spot-Instanzen        Autoscaling
teuer, intensiv      guenstig              Standard-Nodes
```

**Wann sinnvoll:**
- GPU-Workloads sollen regulaere Deployments nicht beeinflussen
- Kostenoptimierung durch Node-Typen pro Workload
- Teams mit sehr verschiedenen Anforderungen

---

## Hybrid-Cloud: On-Premise + Public Cloud

Hybrid bedeutet: eigene Rechenzentren (On-Premise) und Public Cloud gleichzeitig nutzen.

```
On-Premise                          Public Cloud (AWS/Azure/GCP)
──────────                          ────────────────────────────
Eigene Hardware                     Elastisch, Pay-per-Use
Datenschutz-kritische Daten         Burst-Kapazitaet
Geringe Latenz zum Kernsystem       Managed Services
Hohe Fixkosten                      Variable Kosten
```

### Warum Hybrid?

| Grund | Erklaerung |
|-------|-----------|
| Regulatorik | Kundendaten duerfen das eigene RZ nicht verlassen |
| Bestandsinvestitionen | Hardware wurde gekauft und muss genutzt werden |
| Burst-Kapazitaet | On-Premise fuer Grundlast, Cloud fuer Spitzen |
| Migration | Schrittweise aus dem RZ in die Cloud |

### Typische Hybrid-Architektur

```
┌─────────────────────────────────────────────────────────┐
│                    Eigenes Rechenzentrum                 │
│                                                          │
│  ┌──────────────┐   ┌───────────────┐                   │
│  │  on-prem-k8s │   │  Datenbank    │                   │
│  │  (Kernlogik) │───│  (DSGVO-Daten)│                   │
│  └──────┬───────┘   └───────────────┘                   │
│         │  VPN/Direct Connect                            │
└─────────│───────────────────────────────────────────────┘
          │
┌─────────│───────────────────────────────────────────────┐
│         │         Public Cloud                           │
│  ┌──────▼───────┐   ┌───────────────┐                   │
│  │  cloud-k8s   │   │  CDN, Email,  │                   │
│  │  (Frontend,  │   │  Notifications│                   │
│  │  Reporting)  │   └───────────────┘                   │
│  └──────────────┘                                        │
└─────────────────────────────────────────────────────────┘
```

---

## Verbindung zwischen Clustern: Netzwerk-Optionen

### Option 1: VPN / Private Connectivity

```
Cluster A              Cluster B
──────────             ──────────
10.0.0.0/16 ──VPN──── 10.1.0.0/16
```

**Einfach, aber:** Manuelles Routing, keine automatische Service Discovery.
Gut fuer: wenige Verbindungen, On-Prem zu Cloud.

### Option 2: Service Mesh (Istio / Linkerd Multi-Cluster)

```
Cluster A                    Cluster B
──────────                   ──────────
Istiod                       Istiod
  │ Istio Gateway ───mTLS───── Istio Gateway
  │
Pods sehen remote Services
wie lokale Services
```

**Vorteil:** mTLS automatisch, Service Discovery funktioniert uebergreifend,
Traffic-Policies gelten ueberall.
**Nachteil:** Komplex, Istio-Expertise noetig.

### Option 3: Cilium ClusterMesh

Cilium verbindet Cluster auf Netzwerkebene ohne Service Mesh Overhead.

```
cluster-1              cluster-2
─────────              ─────────
Cilium Agent ──────── Cilium Agent
Pod A kann direkt     Pod B direkt erreichen
pod-to-pod ohne       ohne Proxy/Sidecar
Sidecar
```

**Vorteil:** Kein Sidecar-Overhead, Native Kubernetes NetworkPolicy uebergreifend.
**Wann:** Performance-kritische Microservices, grosses Cluster-Netz.

---

## GitOps fuer Multi-Cluster: Das Fundament

Ohne GitOps wird Multi-Cluster-Management schnell zum Chaos.
Goldene Regel: **Ein Git-Repo als einzige Quelle der Wahrheit fuer alle Cluster.**

### ArgoCD: ApplicationSet Pattern

```yaml
# Einmal definieren — in alle Cluster deployen
apiVersion: argoproj.io/v1alpha1
kind: ApplicationSet
spec:
  generators:
  - list:
      elements:
      - cluster: prod-eu
        url: https://prod-eu.example.com
      - cluster: prod-us
        url: https://prod-us.example.com
  template:
    spec:
      destination:
        server: '{{url}}'
      source:
        path: apps/myservice/overlays/{{cluster}}
```

**Ergebnis:** Ein Commit in Git → ArgoCD deployt automatisch in alle Cluster.

### Kustomize Overlay-Struktur fuer Multi-Cluster

```
apps/myservice/
  base/                   ← gemeinsame Manifests
    deployment.yml
    service.yml
  overlays/
    dev/                  ← Dev-spezifisch
      kustomization.yaml
      replicas.yml        ← 1 Replica
    prod-eu/              ← Prod EU-spezifisch
      kustomization.yaml
      replicas.yml        ← 5 Replicas
      configmap.yml       ← EU-Endpoints
    prod-us/              ← Prod US-spezifisch
      kustomization.yaml
      replicas.yml        ← 5 Replicas
      configmap.yml       ← US-Endpoints
```

**Vorteil:** Keine Dopplung von Manifests, Unterschiede sind klar sichtbar.

---

## Sicherheit in Multi-Cluster-Umgebungen

### 1. Cluster-uebergreifende Identitaet (SPIFFE/SPIRE)

Problem: Wie weiss Cluster A, dass eine Anfrage wirklich von Cluster B kommt?

```
SPIRE Server (zentral)
       │ stellt aus
       ▼
  workload identity
  spiffe://prod-eu/ns/orders/sa/payment-svc
       │ wird validiert von
       ▼
  Cluster B vertraut dem Zertifikat
  → mTLS ohne manuelle Zertifikatsverwaltung
```

### 2. Secrets-Management (Vault / External Secrets Operator)

**Anti-Pattern:** Secrets in Git oder als Kubernetes-Secrets ohne Verschluesselung.

**Best Practice:** Zentraler Vault, alle Cluster holen Secrets zur Laufzeit.

```
HashiCorp Vault (zentral)
       │
       ├── Cluster prod-eu  → liest Secrets mit cluster-eigenem Auth-Token
       ├── Cluster prod-us  → liest Secrets mit cluster-eigenem Auth-Token
       └── Cluster staging  → liest Secrets (eigene Policy, weniger Rechte)
```

External Secrets Operator synchronisiert automatisch:
```yaml
apiVersion: external-secrets.io/v1beta1
kind: ExternalSecret
spec:
  secretStoreRef:
    name: vault-backend
  target:
    name: db-password       # → wird zu normalem Kubernetes Secret
  data:
  - secretKey: password
    remoteRef:
      key: secret/prod/database
      property: password
```

### 3. RBAC-Strategie uebergreifend

| Cluster | Wer darf deployen | Wer darf lesen |
|---------|-------------------|----------------|
| dev | Alle Entwickler | Alle Entwickler |
| staging | CI/CD Pipeline | Entwickler, QA |
| prod | Nur CI/CD Pipeline | Senior Devs, Ops |

**Werkzeug:** OIDC-Integration (Keycloak, Okta) → ein Login, Rechte per Cluster-Rolle.

---

## Observability: Alles sichtbar machen

Ein grosses Problem bei Multi-Cluster: Logs und Metriken sind ueber alle Cluster verteilt.

### Zentrales Monitoring

```
Grafana Cloud / Thanos / VictoriaMetrics (zentral)
       │
       ├── Prometheus in cluster-eu  → remote_write
       ├── Prometheus in cluster-us  → remote_write
       └── Prometheus in staging     → remote_write
```

**Ergebnis:** Ein Dashboard zeigt alle Cluster, Alerts koennen uebergreifend korreliert werden.

### Verteiltes Tracing (OpenTelemetry)

```
Request: Browser → EU-Frontend → US-Orders-API → EU-DB

Trace-ID: abc-123  ← gleiche ID uebergreifend
  Span 1: EU-Frontend       50ms
  Span 2: Cross-Cluster     15ms (Netzwerk!)
  Span 3: US-Orders-API     30ms
  Span 4: DB-Query          10ms
```

OpenTelemetry Collector in jedem Cluster, zentral gesammeltes Jaeger/Tempo.

---

## Best Practices auf einen Blick

| Thema | Best Practice |
|-------|---------------|
| Cluster-Anzahl | Klein anfangen — 1 Prod, 1 Dev. Nur bei echtem Bedarf mehr. |
| Netzwerk | VPN fuer Start, Cilium/Istio wenn Service Discovery noetig |
| GitOps | ArgoCD ApplicationSet oder Flux mit Cluster-Tags |
| Secrets | Zentraler Vault, nie Secrets in Git |
| Observability | Zentrales Prometheus/Grafana von Anfang an |
| RBAC | OIDC-basiert, Rechte per Cluster unterschiedlich |
| DNS | Eindeutige Cluster-Namen: prod-eu.k8s.example.com |
| Kosten | Cluster-Overhead einrechnen (3 Control Plane Nodes pro Cluster!) |

---

## Anti-Patterns

**Anti-Pattern 1: Snowflake-Cluster**
Jeder Cluster hat andere Addons, andere Versionen, andere Netzwerk-Plugins.
→ Katastrophe beim Troubleshooting, jeder Cluster eine eigene Wissenschaft.

**Loesung:** Cluster-as-Code (Crossplane, Cluster API) — alle Cluster gleich gebaut.

---

**Anti-Pattern 2: Manuelle Deployments in mehrere Cluster**
Entwickler deployen manuell mit `kubectl` in 5 Cluster.
→ Cluster laufen auseinander, niemand weiss welcher Stand wo ist.

**Loesung:** Nur GitOps, keine manuellen Deployments in Prod.

---

**Anti-Pattern 3: Zu viele Cluster zu frueh**
10 Cluster fuer ein 5-Mann-Team.
→ Mehr Wartungsaufwand als Nutzen, jeder Cluster braucht Updates, Monitoring, Sicherheits-Patches.

**Faustregel:** Cluster kosten Betrieb. Jeder neue Cluster muss seinen Nutzen rechtfertigen.

---

## Wann Multi-Cluster (nicht) anfangen?

```
Starte mit einem Cluster wenn:          Wechsle zu Multi-Cluster wenn:

- Team < 20 Entwickler                  - Regulatorik es erfordert (DSGVO, Region)
- Keine Compliance-Anforderungen        - Prod-Ausfall kostet mehr als Overhead
- Proof of Concept / Startup            - Team > 3 dedizierte Platform Engineers
- Kein globaler Traffic                 - Verschiedene Workload-Typen (GPU + Web)
                                        - Echter Geo-Bedarf (< 50ms in US und EU)
```
