# Best Practices fuer Multi-Cluster- und Hybrid-Umgebungen

## Das Grundproblem: Warum ein einzelner Cluster nicht ausreicht

### Die Latenz-Grenze: < 10 ms

Kubernetes-Cluster haben eine harte technische Grenze, die oft unterschaetzt wird:

> **Alle Nodes eines Clusters muessen mit einer Netzwerklatenz von unter 10 ms**
> **erreichbar sein — insbesondere die etcd-Knoten im Control Plane.**

Das bedeutet: Nodes in verschiedenen geografischen Regionen koennen **nicht** einfach
zu einem einzigen Cluster zusammengefasst werden.

![Latenz-Grenze in Kubernetes-Clustern](/images/multi-cluster-latenz.svg)

**Was passiert bei zu hoher Latenz?**
- etcd-Leader-Wahlen schlagen fehl
- Control Plane wird instabil
- Pods werden nicht mehr korrekt geplant

---

## Loesung: Multi-Cluster mit Geo-LoadBalancer

Statt einem grossen Cluster ueber alle Standorte: **ein Cluster pro Region**,
verbunden durch einen Geo-LoadBalancer, der Nutzer zur naechstgelegenen Region routed.

![Multi-Cluster mit Geo-LoadBalancer](/images/multi-cluster-geo-loadbalancer.svg)

**Wie der Geo-LoadBalancer entscheidet:**
- Anhand der IP-Geolokation des Nutzers
- Anhand von Healthchecks (faellt ein Cluster aus → Traffic zur naechsten Region)
- Anhand von Latenz-Messungen (Anycast oder Latency-Based Routing)

---

## Achtung: Multi-Cluster steigert die Komplexitaet erheblich

![Komplexitaetsvergleich: Ein Cluster vs. drei Cluster](/images/multi-cluster-komplexitaet.svg)

> **Faustregel:** Jeder zusaetzliche Cluster verdreifacht den Betriebsaufwand
> fuer das Platform-Team — nicht verdoppelt.

**Wann lohnt sich der Aufwand?**

| Situation | Empfehlung |
|-----------|------------|
| Startup, < 20 Entwickler | Ein Cluster, Namespaces zur Trennung |
| Regulatorik erfordert EU/US-Trennung | Multi-Cluster noetig |
| Prod-Ausfall kostet mehr als Overhead | Multi-Cluster noetig |
| Globale Nutzer mit Latenz-Anforderung | Multi-Cluster noetig |
| Kein globaler Traffic, keine Compliance | Ein Cluster reicht |

---

## Warum ueberhaupt mehrere Cluster?

Ein einzelner Kubernetes-Cluster hat praktische Grenzen:

- **Latenz**: Nodes muessen unter 10 ms erreichbar sein — schliesst Geo-Verteilung in einem Cluster aus
- **Skalierbarkeit**: Ab ca. 5.000 Nodes wird ein einzelner Cluster unhandlich
- **Isolation**: Prod und Dev im gleichen Cluster teilen das Schicksal bei einem Ausfall
- **Compliance**: Daten in der EU, Compute in den USA — rechtlich oft nicht mischbar
- **Verfuegbarkeit**: Ein Cluster = ein Single Point of Failure

![Single Cluster vs. Multi-Cluster](/images/multi-cluster-overview.svg)

---

## Die drei Multi-Cluster-Muster

### Muster 1: Umgebungs-Trennung (Dev/Staging/Prod)

Das einfachste und haeufigste Muster: Ein Cluster pro Umgebung.

| Cluster | Wer nutzt ihn | Besonderheiten |
|---------|--------------|----------------|
| dev-cluster | Entwickler (frei experimentieren) | Keine strengen Policies |
| staging-cluster | QA, Integrationstests vor Release | Gleiche Configs wie Prod |
| prod-cluster | Echte Nutzer | RBAC streng, PodDisruptionBudget |

**Wann sinnvoll:**
- Wenn Prod-Stabilitat kritisch ist
- Wenn Compliance getrennte Umgebungen fordert
- Einfach zu verstehen und zu betreiben

**Herausforderung:** Gleiche Manifests muessen in mehrere Cluster deployt werden
→ Loesung: GitOps (ArgoCD ApplicationSet, Flux Kustomization)

---

### Muster 2: Geografische Verteilung (Active-Active)

Mehrere Cluster in verschiedenen Regionen, alle nehmen Traffic an.

![Muster 2: Geografische Verteilung Active-Active](/images/multi-cluster-muster-geo.svg)

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

| Cluster | Workload | Hardware |
|---------|----------|----------|
| gpu-cluster | ML-Training | A100/H100 GPUs — teuer, intensiv |
| batch-cluster | Nacht-Jobs, Batch-Verarbeitung | Spot-Instanzen — guenstig |
| web-cluster | APIs, UIs, Standard-Services | Standard-Nodes, Autoscaling |

**Wann sinnvoll:**
- GPU-Workloads sollen regulaere Deployments nicht beeinflussen
- Kostenoptimierung durch Node-Typen pro Workload
- Teams mit sehr verschiedenen Anforderungen

---

## Hybrid-Cloud: On-Premise + Public Cloud

Hybrid bedeutet: eigene Rechenzentren (On-Premise) und Public Cloud gleichzeitig nutzen.

| Aspekt | On-Premise | Public Cloud (AWS/Azure/GCP) |
|--------|-----------|------------------------------|
| Hardware | Eigene Hardware | Elastisch, Pay-per-Use |
| Daten | Datenschutz-kritische Daten | Burst-Kapazitaet |
| Latenz | Gering zum Kernsystem | Abhaengig von Region |
| Kosten | Hohe Fixkosten | Variable Kosten |

### Warum Hybrid?

| Grund | Erklaerung |
|-------|-----------|
| Regulatorik | Kundendaten duerfen das eigene RZ nicht verlassen |
| Bestandsinvestitionen | Hardware wurde gekauft und muss genutzt werden |
| Burst-Kapazitaet | On-Premise fuer Grundlast, Cloud fuer Spitzen |
| Migration | Schrittweise aus dem RZ in die Cloud |

### Typische Hybrid-Architektur

![Hybrid-Architektur On-Premise und Public Cloud](/images/multi-cluster-hybrid-arch.svg)

---

## Verbindung zwischen Clustern: Netzwerk-Optionen

![Netzwerkoptionen zwischen Clustern: VPN, Service Mesh, Cilium ClusterMesh](/images/multi-cluster-netzwerk.svg)

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

Der SPIRE Server laeuft zentral und stellt jeder Workload eine kryptografische Identitaet aus
(z.B. `spiffe://prod-eu/ns/orders/sa/payment-svc`). Cluster B validiert dieses Zertifikat
automatisch — mTLS ohne manuelle Zertifikatsverwaltung.

### 2. Secrets-Management (Vault / External Secrets Operator)

**Anti-Pattern:** Secrets in Git oder als Kubernetes-Secrets ohne Verschluesselung.

**Best Practice:** Zentraler Vault, alle Cluster holen Secrets zur Laufzeit.

| Cluster | Auth-Token | Policy |
|---------|-----------|--------|
| prod-eu | Cluster-eigenes Token | Voller Prod-Zugriff |
| prod-us | Cluster-eigenes Token | Voller Prod-Zugriff |
| staging | Cluster-eigenes Token | Eingeschraenkte Policy, weniger Rechte |

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

Jeder Cluster betreibt eine lokale Prometheus-Instanz, die per `remote_write` an eine
zentrale Instanz (Grafana Cloud, Thanos oder VictoriaMetrics) schreibt. Ein Dashboard
zeigt alle Cluster, Alerts koennen uebergreifend korreliert werden.

**Ergebnis:** Ein Dashboard zeigt alle Cluster, Alerts koennen uebergreifend korreliert werden.

### Verteiltes Tracing (OpenTelemetry)

| Span | Service | Dauer | Hinweis |
|------|---------|-------|---------|
| 1 | EU-Frontend | 50 ms | |
| 2 | Cross-Cluster-Hop | 15 ms | Netzwerk zwischen Clustern! |
| 3 | US-Orders-API | 30 ms | |
| 4 | DB-Query | 10 ms | |

Die gleiche Trace-ID (`abc-123`) verbindet alle Spans uebergreifend — sichtbar in Jaeger/Tempo.

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

| Starte mit einem Cluster wenn ... | Wechsle zu Multi-Cluster wenn ... |
|----------------------------------|----------------------------------|
| Team &lt; 20 Entwickler | Regulatorik es erfordert (DSGVO, Region) |
| Keine Compliance-Anforderungen | Prod-Ausfall kostet mehr als Overhead |
| Proof of Concept / Startup | Team &gt; 3 dedizierte Platform Engineers |
| Kein globaler Traffic | Verschiedene Workload-Typen (GPU + Web) |
| | Echter Geo-Bedarf (&lt; 50 ms in US und EU) |

---

## Persistenter Storage im Cluster: Container Storage Interface (CSI)

### Was ist CSI?

Das **Container Storage Interface (CSI)** ist ein standardisiertes Interface, ueber das
Kubernetes mit beliebigen Storage-Systemen kommuniziert. Jeder Storage-Anbieter liefert
seinen eigenen CSI-Treiber — Kubernetes selbst muss nicht mehr veraendert werden.

**Wie war es vorher (in-tree)?**
Frueheer musste jeder Storage-Anbieter seinen Code direkt in den Kubernetes-Quellcode einpflegen
("in-tree"). Das bedeutete: warten auf den naechsten Kubernetes-Release fuer jeden Bugfix.
Mit CSI ist das entkoppelt — Treiber werden unabhaengig versioniert und deployt.

### Architektur

![CSI Architektur in Kubernetes](/images/csi-architektur.svg)

### Vorteile von CSI

| Vorteil | Erklaerung |
|---------|-----------|
| Automatische Erstellung | Storage wird on-demand bereitgestellt, wenn ein PVC erstellt wird |
| Portabilitaet | Pods koennen auf beliebige Nodes verschoben werden — Volume folgt mit |
| Automatische Bereinigung | Storage wird geloescht, wenn der PVC entfernt wird (ReclaimPolicy: Delete) |
| Herstellerunabhaengig | Jeder Anbieter liefert eigenen Treiber — kein Kubernetes-Patch noetig |

### Statische vs. dynamische Provisionierung

| | Statisch | Dynamisch |
|-|---------|----------|
| Wann? | Storage wird vorab manuell angelegt | Storage wird on-demand erstellt |
| Einsatz | Vorbefuellte Volumes, Legacy-Systeme | Produktionssysteme, Cloud-native |
| Aufwand | Admin legt PV manuell an | StorageClass und CSI-Treiber reichen |

### Komponenten

**StorageClass** — definiert, welcher Treiber und welche Parameter verwendet werden:

```yaml
apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
  name: nfs-csi
provisioner: nfs.csi.k8s.io
parameters:
  server: 10.135.0.18
  share: /var/nfs
reclaimPolicy: Delete
volumeBindingMode: Immediate
mountOptions:
  - nfsvers=3
```

**PersistentVolumeClaim (PVC)** — Anforderung eines Pods an Storage:

```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: pvc-nfs-dynamic
spec:
  accessModes:
    - ReadWriteMany
  resources:
    requests:
      storage: 2Gi
  storageClassName: nfs-csi
```

### Praxisbeispiel: NFS CSI-Treiber installieren

```bash
curl -skSL https://raw.githubusercontent.com/kubernetes-csi/csi-driver-nfs/v4.6.0/deploy/install-driver.sh \
  | bash -s v4.6.0 --
```

### Volume Snapshots

CSI unterstuetzt auch Snapshots — nuetzlich fuer Backups und Point-in-Time-Recovery.

**Schritt 1: VolumeSnapshotClass anlegen**

```yaml
apiVersion: snapshot.storage.k8s.io/v1
kind: VolumeSnapshotClass
metadata:
  name: csi-nfs-snapclass
driver: nfs.csi.k8s.io
deletionPolicy: Delete
```

**Schritt 2: Snapshot erstellen**

```yaml
apiVersion: snapshot.storage.k8s.io/v1
kind: VolumeSnapshot
metadata:
  name: test-nfs-snapshot
spec:
  volumeSnapshotClassName: csi-nfs-snapclass
  source:
    persistentVolumeClaimName: pvc-nfs-dynamic
```

**Schritt 3: Aus Snapshot wiederherstellen**

```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: pvc-nfs-snapshot-restored
spec:
  accessModes:
    - ReadWriteMany
  resources:
    requests:
      storage: 2Gi
  storageClassName: nfs-csi
  dataSource:
    name: test-nfs-snapshot
    kind: VolumeSnapshot
    apiGroup: snapshot.storage.k8s.io
```
