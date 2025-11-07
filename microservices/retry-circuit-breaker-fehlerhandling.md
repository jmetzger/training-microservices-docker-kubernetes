# Fehlerhandliung mit Circuit - Breaker und Retry

# Circuit Breaker & Retry-Strategien in Python

## Was ist das?

**Retry**: Wiederhole fehlgeschlagene Requests automatisch (z.B. bei temporären Netzwerkfehlern)

**Circuit Breaker**: Stoppt Requests zu einem Service, der wiederholt fehlschlägt → verhindert Kaskadeneffekte

## Wann was nutzen?

- **Retry**: Transiente Fehler (Timeout, 503, Netzwerkstörung)
- **Circuit Breaker**: Anhaltende Ausfälle, Schutz vor Überlastung
- **Kombination**: Oft zusammen eingesetzt

## States im Circuit Breaker
1. **Closed**: Normal, Requests gehen durch
2. **Open**: Service ausgefallen, Requests werden sofort abgelehnt
3. **Half-Open**: Test, ob Service wieder verfügbar

## Python-Implementierung

```python
from tenacity import retry, stop_after_attempt, wait_exponential
from pybreaker import CircuitBreaker

# RETRY mit tenacity
@retry(
    stop=stop_after_attempt(3),
    wait=wait_exponential(multiplier=1, min=1, max=10)
)
def call_api():
    response = requests.get("https://api.example.com")
    response.raise_for_status()
    return response.json()

# CIRCUIT BREAKER mit pybreaker
breaker = CircuitBreaker(
    fail_max=5,        # Nach 5 Fehlern → Open
    reset_timeout=60   # Nach 60s → Half-Open
)

@breaker
def protected_call():
    return requests.get("https://api.example.com").json()
```

## Best Practices

- Circuit Breaker für externe Services
- Monitoring & Logging der Failures
- Fallback-Strategien definieren

**Bibliotheken**: `tenacity`, `pybreaker`, `resilience4py`
