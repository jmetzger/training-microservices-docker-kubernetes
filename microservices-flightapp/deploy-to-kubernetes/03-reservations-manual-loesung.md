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

Das Tool in busybox ist `nc` (netcat) — es kann rohe TCP-Verbindungen oeffnen
und damit beliebige HTTP-Methoden senden.

### Schritt 1: busybox-Pod starten und Reservierung anlegen (PUT)

```
kubectl run -it --rm busybox --image=busybox --restart=Never \
  -n reservations-<dein-name> \
  -- sh -c '
BODY="{\"flight_id\":\"FL001\",\"seat_num\":\"12A\",\"customer_id\":\"max\"}"
LEN=$(echo -n "$BODY" | wc -c)
printf "PUT /reservations HTTP/1.0\r\nHost: ms-reservations\r\nContent-Type: application/json\r\nContent-Length: $LEN\r\n\r\n$BODY" \
  | nc ms-reservations 8000
'
```

Erwartete Ausgabe:
```
HTTP/1.0 200 OK
...
{
  "status": "success"
}
```

### Schritt 2: Reservierung abfragen (GET)

```
kubectl run -it --rm busybox --image=busybox --restart=Never \
  -n reservations-<dein-name> \
  -- wget -O- "http://ms-reservations:8000/reservations?flight_id=FL001"
```

Erwartete Ausgabe:
```
{
  "12A": "max"
}
```

### Schritt 3: Doppelbuchung versuchen

```
kubectl run -it --rm busybox --image=busybox --restart=Never \
  -n reservations-<dein-name> \
  -- sh -c '
BODY="{\"flight_id\":\"FL001\",\"seat_num\":\"12A\",\"customer_id\":\"erika\"}"
LEN=$(echo -n "$BODY" | wc -c)
printf "PUT /reservations HTTP/1.0\r\nHost: ms-reservations\r\nContent-Type: application/json\r\nContent-Length: $LEN\r\n\r\n$BODY" \
  | nc ms-reservations 8000
'
```

Erwartete Ausgabe (HTTP 403):
```
HTTP/1.0 403 FORBIDDEN
...
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
