# Ressourcenmanagement im Namespace (ResourceQuota & LimitRange)

## Hintergrund

In einem geteilten Cluster muss verhindert werden, dass ein Namespace unbegrenzt CPU und RAM
belegt. Kubernetes bietet zwei Objekte dafuer:

| Objekt | Wirkung |
|--------|---------|
| `ResourceQuota` | Setzt eine harte Obergrenze fuer den gesamten Namespace (z.B. max. 4 CPU, max. 8Gi RAM) |
| `LimitRange` | Setzt Default-Werte und Grenzen pro Container/Pod, falls im Manifest keine Angaben stehen |

Zusammenspiel: Sobald eine `ResourceQuota` aktiv ist, **muss** jeder Pod `requests` und `limits`
definieren — sonst wird er abgelehnt. `LimitRange` loest dieses Problem, indem es automatisch
Defaults einsetzt.

---

## Schritt 1: Namespace anlegen

```
kubectl create namespace resource-<dein-name>
```

---

## Schritt 2: Manifests vorbereiten

```
cd
mkdir -p manifests/20-resource
cd manifests/20-resource
```

---

## Schritt 3: ResourceQuota erstellen

```
# vi 01-resourcequota.yml
apiVersion: v1
kind: ResourceQuota
metadata:
  name: quota-namespace
spec:
  hard:
    requests.cpu: "1"
    requests.memory: 512Mi
    limits.cpu: "2"
    limits.memory: 1Gi
    pods: "5"
```

```
kubectl apply -f 01-resourcequota.yml -n resource-<dein-name>
kubectl get resourcequota -n resource-<dein-name>
kubectl describe resourcequota quota-namespace -n resource-<dein-name>
```

Erwartete Ausgabe:

```
Name:            quota-namespace
Namespace:       resource-<dein-name>
Resource         Used  Hard
--------         ----  ----
limits.cpu       0     2
limits.memory    0     1Gi
pods             0     5
requests.cpu     0     1
requests.memory  0     512Mi
```

---

## Schritt 4: Pod OHNE limits anlegen (schlaegt fehl)

```
# vi 02-pod-no-limits.yml
apiVersion: v1
kind: Pod
metadata:
  name: pod-no-limits
spec:
  containers:
  - name: nginx
    image: nginx
```

```
kubectl apply -f 02-pod-no-limits.yml -n resource-<dein-name>
```

**Erwarteter Fehler:**

```
Error from server (Forbidden): error when creating "02-pod-no-limits.yml":
pods "pod-no-limits" is forbidden: failed quota: quota-namespace:
must specify limits.cpu for: nginx; must specify limits.memory for: nginx;
must specify requests.cpu for: nginx; must specify requests.memory for: nginx
```

---

## Schritt 5: LimitRange als Rettung

```
# vi 03-limitrange.yml
apiVersion: v1
kind: LimitRange
metadata:
  name: limits-per-container
spec:
  limits:
  - type: Container
    default:
      cpu: 200m
      memory: 128Mi
    defaultRequest:
      cpu: 100m
      memory: 64Mi
    max:
      cpu: "1"
      memory: 512Mi
    min:
      cpu: 50m
      memory: 32Mi
```

```
kubectl apply -f 03-limitrange.yml -n resource-<dein-name>
kubectl describe limitrange limits-per-container -n resource-<dein-name>
```

---

## Schritt 6: Pod OHNE limits nochmal anlegen (funktioniert jetzt)

```
kubectl apply -f 02-pod-no-limits.yml -n resource-<dein-name>
kubectl describe pod pod-no-limits -n resource-<dein-name> | grep -A6 Limits
```

Der LimitRange hat automatisch Defaults eingefuegt:

```
Limits:
  cpu:     200m
  memory:  128Mi
Requests:
  cpu:     100m
  memory:  64Mi
```

---

## Schritt 7: Quota-Verbrauch pruefen

```
kubectl describe resourcequota quota-namespace -n resource-<dein-name>
```

```
Resource         Used   Hard
--------         ----   ----
limits.cpu       200m   2
limits.memory    128Mi  1Gi
pods             1      5
requests.cpu     100m   1
requests.memory  64Mi   512Mi
```

---

## Schritt 8: Quota-Limit testen (zu viele Pods)

```
# vi 04-deployment-many.yml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-viele
spec:
  replicas: 6
  selector:
    matchLabels:
      app: nginx-viele
  template:
    metadata:
      labels:
        app: nginx-viele
    spec:
      containers:
      - name: nginx
        image: nginx
        resources:
          requests:
            cpu: 100m
            memory: 64Mi
          limits:
            cpu: 200m
            memory: 128Mi
```

```
kubectl apply -f 04-deployment-many.yml -n resource-<dein-name>
kubectl get pods -n resource-<dein-name>
kubectl get replicaset -n resource-<dein-name>
kubectl describe replicaset -n resource-<dein-name> | grep -A5 "Warning\|Error\|exceeded"
```

Nur 5 Pods werden gestartet (Quota: `pods: "5"`), der 6. schlaegt fehl mit:

```
exceeded quota: quota-namespace, requested: pods=1,
at limit: pods=5
```

---

## Aufraeumen

```
kubectl delete namespace resource-<dein-name>
```

---

## Zusammenfassung

| Szenario | Ergebnis |
|----------|----------|
| Pod ohne limits, keine LimitRange | Abgelehnt (Quota erzwingt Angaben) |
| Pod ohne limits, mit LimitRange | Akzeptiert (Defaults werden eingesetzt) |
| Deployment ueberschreitet Pod-Limit | Nur erlaubte Anzahl Pods laeuft |
| ResourceQuota describe | Zeigt aktuellen Verbrauch vs. Limit |
