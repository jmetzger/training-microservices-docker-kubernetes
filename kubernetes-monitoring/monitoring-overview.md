# Monitoring mit Prometheus und Grafana

![Monitoring Stack Architektur](/images/monitoring-prometheus-grafana.svg)

## Überblick

Der Standard-Monitoring-Stack in Kubernetes besteht aus drei Komponenten:

| Komponente | Aufgabe |
|------------|---------|
| **Prometheus** | Sammelt Metriken per HTTP-Pull, speichert sie in einer Time Series DB |
| **Grafana** | Visualisiert die Daten aus Prometheus in Dashboards |
| **AlertManager** | Empfängt Alarme von Prometheus und leitet sie weiter (Mail, Slack, ...) |

## Wie funktioniert das Scraping?

Prometheus **pullt** aktiv Metriken — Services müssen einen `/metrics`-Endpunkt
bereitstellen. Prometheus fragt diesen Endpunkt periodisch ab (Standard: alle 15s).

Welche Endpunkte abgefragt werden, wird über **ServiceMonitor** oder
**PodMonitor** CRDs konfiguriert (Teil des Prometheus-Operators).

## Die wichtigsten Exporters

| Exporter | Was wird gemessen | Deployment |
|----------|------------------|------------|
| `node-exporter` | CPU, RAM, Disk, Netzwerk je Node | DaemonSet |
| `kube-state-metrics` | Zustand von Deployments, Pods, ReplicaSets | Deployment |
| `cAdvisor` | Container-Ressourcen (in kubelet integriert) | automatisch |

## Grafana Dashboards

Grafana fragt Prometheus über **PromQL** ab. Fertige Community-Dashboards
können direkt über die Dashboard-ID importiert werden:

| Dashboard | ID |
|-----------|----|
| Kubernetes Cluster Overview | 7249 |
| Node Exporter Full | 1860 |
| Kubernetes Pods | 6781 |

## Installation (Helm)

Der einfachste Weg ist der `kube-prometheus-stack` Chart — er installiert
Prometheus, Grafana und AlertManager in einem Schritt:

```
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update
helm upgrade --install monitoring prometheus-community/kube-prometheus-stack \
  --namespace monitoring --create-namespace
```

Grafana ist danach erreichbar über:

```
kubectl port-forward svc/monitoring-grafana 3000:80 -n monitoring
```

Default-Login: `admin` / `prom-operator`

## PromQL — Beispielabfragen

```
# CPU-Nutzung aller Pods
rate(container_cpu_usage_seconds_total[5m])

# RAM-Nutzung eines Namespace
sum(container_memory_usage_bytes{namespace="default"}) by (pod)

# Anzahl nicht-bereiter Pods
kube_pod_status_ready{condition="false"}
```

## Referenzen

  * https://prometheus.io/docs/
  * https://grafana.com/docs/grafana/latest/
  * https://github.com/prometheus-community/helm-charts/tree/main/charts/kube-prometheus-stack
