# Consumer-Driven Contract Testing mit Pact

## Was ist das Problem?

Bei Microservices haengen Services voneinander ab.
Aendert der **Order Service** seine API, bricht der **Inventory Service** — ohne dass
es vor dem Deployment auffaellt.

```
Order Service  -->  Inventory Service
  (Consumer)          (Provider)

Order Service erwartet: { id, name, stock, price }
Inventory Service liefert nach Refactoring: { id, name, quantity, price }
                                                         ^^^^^^
                                               "stock" umbenannt -> bricht Order Service
```

**Contract Testing loest dieses Problem:** Der Consumer definiert, was er braucht.
Der Provider prueft, ob er das liefert.

---

## Pact: Consumer-Driven Contract Testing

Pact ist der Industriestandard fuer Consumer-Driven Contract Testing.
Der Consumer schreibt einen Test, der automatisch einen **Pact** (Vertrag) erzeugt.
Der Provider validiert seinen Code gegen diesen Vertrag.

```
Consumer-Test                Provider-Test
     |                            |
     | erzeugt Pact-Datei         | validiert gegen Pact-Datei
     v                            v
order-inventory.json  ---------> Pact Verifier
(Vertrag)
```

---

## Beispiel: Order Service (Consumer) in Python

```bash
pip install pact-python
```

```python
# test_order_consumer.py
import pytest
from pact import Consumer, Provider

pact = Consumer("OrderService").has_pact_with(
    Provider("InventoryService"),
    pact_dir="./pacts"
)

def test_get_product():
    # Consumer definiert: was erwarte ich vom Provider?
    (pact
     .given("Produkt 123 existiert")
     .upon_receiving("Anfrage fuer Produkt 123")
     .with_request("GET", "/api/products/123")
     .will_respond_with(200, body={
         "id": 123,
         "name": "Laptop",
         "stock": 42,
         "price": 999.99
     }))

    with pact:
        # Order Service ruft Inventory Service auf (gegen Pact-Mock)
        result = requests.get("http://localhost:1234/api/products/123")
        assert result.json()["stock"] == 42

# Ergebnis: pacts/OrderService-InventoryService.json wird erzeugt
```

---

## Beispiel: Inventory Service (Provider) in Python

```python
# test_inventory_provider.py
import pytest
from pact import Verifier

def test_provider_against_pact():
    verifier = Verifier(
        provider="InventoryService",
        provider_base_url="http://localhost:8080"
    )

    output, _ = verifier.verify_pacts(
        "./pacts/OrderService-InventoryService.json",
        provider_states_setup_url="http://localhost:8080/_pact/provider_states"
    )
    assert output == 0  # 0 = alle Vertraege erfuellt
```

---

## Ablauf im Team

```
1. Consumer-Entwickler schreibt Consumer-Test
   → Pact-Datei wird erzeugt (JSON)

2. Pact-Datei wird in Pact Broker hochgeladen
   (oder direkt im Repo geteilt)

3. Provider-Entwickler laeuft Provider-Test
   → prueft eigene Implementierung gegen Pact-Datei

4. CI/CD prueft bei jedem Build ob Provider Pact noch erfuellt
```

---

## Vergleich: OpenAPI vs. Pact

| | **OpenAPI Contract** | **Pact (Consumer-Driven)** |
|---|---|---|
| Wer schreibt den Vertrag? | Provider (top-down) | Consumer (bottom-up) |
| Was wird geprueft? | Schema/Typen | Konkrete Interaktionen |
| Ungenutzte Felder? | Werden mit geprueft | Werden ignoriert |
| Vorteil | Ein Vertrag fuer alle Consumer | Jeder Consumer prueft nur was er braucht |
| Nachteil | Provider kann Dinge liefern die niemand braucht | Viele Pact-Dateien bei vielen Consumern |

**Faustregel:** Pact fuer interne Service-zu-Service Kommunikation,
OpenAPI fuer externe APIs (Dokumentation + Validation).

---

## Pact Broker (optional)

Fuer Teams mit vielen Services empfiehlt sich ein **Pact Broker** —
er verwaltet alle Vertraege zentral:

```bash
# Pact Broker lokal starten
docker run -d -p 9292:9292 pactfoundation/pact-broker

# Pact hochladen
pact-broker publish ./pacts \
  --broker-base-url http://localhost:9292 \
  --consumer-app-version 1.0.0

# Provider validiert gegen Broker
pact-broker verify \
  --provider InventoryService \
  --broker-base-url http://localhost:9292
```
