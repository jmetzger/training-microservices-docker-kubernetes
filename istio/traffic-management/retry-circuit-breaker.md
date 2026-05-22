# Retry und Circuit Breaker in Istio

## Retry

Wenn ein Request fehlschlägt (z.B. HTTP 503), versucht Istio es automatisch
erneut — ohne dass die Anwendung das selbst implementieren muss.

Konfiguriert wird das in der `VirtualService`:

```
apiVersion: networking.istio.io/v1
kind: VirtualService
metadata:
  name: ratings
spec:
  hosts:
  - ratings
  http:
  - route:
    - destination:
        host: ratings
    retries:
      attempts: 3
      perTryTimeout: 2s
      retryOn: 5xx,reset,connect-failure
```

| Feld | Bedeutung |
|------|-----------|
| `attempts` | Maximale Anzahl Wiederholungen |
| `perTryTimeout` | Timeout pro Einzelversuch |
| `retryOn` | Wann wird wiederholt (HTTP-Codes, Netzwerkfehler) |

**Wann sinnvoll:** Kurze, sporadische Fehler (z.B. Pod-Neustart, kurze Überlast).
Nicht sinnvoll bei nicht-idempotenten Requests (z.B. Bezahlvorgang).

---

## Circuit Breaker

Verhindert, dass ein kranker Service das gesamte System mit sich zieht
("Kaskadenfehler"). Wenn ein Pod zu viele Fehler liefert, nimmt Istio ihn
aus dem Load-Balancing-Pool heraus.

Konfiguriert wird das in der `DestinationRule` via `outlierDetection`:

```
apiVersion: networking.istio.io/v1
kind: DestinationRule
metadata:
  name: ratings
spec:
  host: ratings
  trafficPolicy:
    outlierDetection:
      consecutive5xxErrors: 5
      interval: 30s
      baseEjectionTime: 30s
      maxEjectionPercent: 50
```

| Feld | Bedeutung |
|------|-----------|
| `consecutive5xxErrors` | Anzahl aufeinanderfolgender Fehler bis zur Sperrung |
| `interval` | Analyseintervall |
| `baseEjectionTime` | Wie lange wird der Pod ausgesperrt |
| `maxEjectionPercent` | Maximal wie viel Prozent der Pods dürfen gleichzeitig gesperrt sein |

**Ablauf:**

1. Pod liefert 5x hintereinander HTTP 5xx
2. Istio sperrt den Pod für 30 Sekunden aus dem Pool aus
3. Nach 30 Sekunden kommt er wieder rein — liefert er weiter Fehler, wird die
   Sperrzeit verdoppelt (exponential backoff)

---

## Zusammenspiel

Retry und Circuit Breaker ergänzen sich:

```
Retry:          kurzfristige Fehler abfangen (2-3 Versuche)
Circuit Breaker: dauerhaft kranke Pods rauswerfen
```

Ohne Circuit Breaker würden Retries immer wieder auf den gleichen kaputten
Pod treffen. Mit Circuit Breaker landen Retries automatisch bei gesunden Pods.

---

## Referenzen

  * https://istio.io/latest/docs/concepts/traffic-management/#retries
  * https://istio.io/latest/docs/reference/config/networking/virtual-service/#HTTPRetry
  * https://istio.io/latest/docs/reference/config/networking/destination-rule/#OutlierDetection
