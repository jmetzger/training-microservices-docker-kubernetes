# Loesung: Service fuer ms-reservations

## Service-Manifest

```
nano 05-reservations-svc.yml
```

```
apiVersion: v1
kind: Service
metadata:
  name: ms-reservations
spec:
  type: ClusterIP
  ports:
    - name: http
      port: 8000
  selector:
    app: reservations
```

```
kubectl apply -f . -n reservations-<dein-name>
kubectl get svc -n reservations-<dein-name>
```

## Test mit busybox

```
kubectl run -it --rm busybox --image=busybox --restart=Never \
  -n reservations-<dein-name> \
  -- wget -O- http://ms-reservations:8000/reservations
```

Erwartetes Ergebnis:
```
Connecting to ms-reservations:8000 (10.x.x.x:8000)
writing to stdout
{}
```

## Erklaerung

| Feld | Wert | Bedeutung |
|------|------|-----------|
| `type: ClusterIP` | — | Service nur innerhalb des Clusters erreichbar |
| `port: 8000` | — | Port auf dem der Service lauscht (und weiterleitet) |
| `selector: app: reservations` | — | muss zum Label `app: reservations` im Deployment-Pod passen |

Der `selector` ist der entscheidende Teil: Kubernetes sucht alle Pods mit diesem Label
und leitet Traffic dorthin weiter. Stimmt das Label nicht, gibt es keine Endpoints
und der Service antwortet mit Connection refused.

## Loesung Zusatzaufgabe: Image-Version aktualisieren

In `04-reservations-deploy.yml` den Tag aendern:

```
          image: dockertrainereu/reservations-jm:v17
```

```
kubectl apply -f . -n reservations-<dein-name>
kubectl rollout status deployment ms-reservations -n reservations-<dein-name>
kubectl get pods -n reservations-<dein-name>
```

Erwartete Ausgabe:
```
Waiting for deployment "ms-reservations" rollout to finish: 1 old replicas are pending termination...
deployment "ms-reservations" successfully rolled out
```
