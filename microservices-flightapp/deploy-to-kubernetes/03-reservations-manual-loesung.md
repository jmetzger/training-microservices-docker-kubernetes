# Loesung: Service und Reservierung fuer ms-reservations

## Loesung 1: Service-Manifest

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

### Erklaerung

| Feld | Bedeutung |
|------|-----------|
| `type: ClusterIP` | Service nur innerhalb des Clusters erreichbar |
| `port: 8000` | Port auf dem der Service lauscht und weiterleitet |
| `selector: app: reservations` | muss zum Label im Deployment-Pod passen |

Der `selector` ist der entscheidende Teil: Kubernetes sucht alle Pods mit diesem Label
und leitet Traffic dorthin weiter. Stimmt das Label nicht, gibt es keine Endpoints
und der Service antwortet mit Connection refused.

## Loesung 2: Reservierung durchfuehren

Wir verwenden `curlimages/curl` — das Image hat `curl` mit Shell und unterstuetzt
alle HTTP-Methoden (GET, PUT, POST, ...).

### Schritt 1: Reservierung anlegen (PUT)

```
kubectl run -it --rm curlpod --image=curlimages/curl --restart=Never \
  -n reservations-<dein-name> \
  -- curl -s -X PUT -H "Content-Type: application/json" \
     -d '{"flight_id":"FL001","seat_num":"12A","customer_id":"max"}' \
     http://ms-reservations:8000/reservations
```

Erwartete Ausgabe:
```
{"status": "success"}
```

### Schritt 2: Reservierung abfragen (GET)

```
kubectl run -it --rm curlpod --image=curlimages/curl --restart=Never \
  -n reservations-<dein-name> \
  -- curl -s "http://ms-reservations:8000/reservations?flight_id=FL001"
```

Erwartete Ausgabe:
```
{"12A": "max"}
```

### Schritt 3: Doppelbuchung versuchen

```
kubectl run -it --rm curlpod --image=curlimages/curl --restart=Never \
  -n reservations-<dein-name> \
  -- curl -s -X PUT -H "Content-Type: application/json" \
     -d '{"flight_id":"FL001","seat_num":"12A","customer_id":"erika"}' \
     http://ms-reservations:8000/reservations
```

Erwartete Ausgabe:
```
{"error": "Could not complete reservation for 12A", "description": "Seat already reserved. Cannot double-book"}
```

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
