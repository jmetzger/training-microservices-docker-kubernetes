# Unit Testing

## Was testen Unit Tests?

Unit Tests pruefen eine einzelne Klasse oder Funktion **isoliert** — ohne Datenbank,
ohne Netzwerk, ohne andere Services. Sie sind schnell (Millisekunden) und stabil.

**Wann Unit Tests schreiben:**
- Komplexe Geschaeftslogik (Berechnungen, Validierungen, Entscheidungen)
- Edge Cases (Was passiert bei leerem Input? Bei negativen Zahlen?)
- Code ohne externe Abhaengigkeiten

**Wann NICHT:** Wenn der Code nur eine Datenbank aufruft oder HTTP macht —
das gehoert in einen Integration Test mit Testcontainers.

---

## Solitaire vs Sociable Unit Tests

### Solitaire Tests (isoliert, alle Dependencies gemockt)

```python
# order_service.py
class OrderService:
    def __init__(self, inventory_client, payment_client):
        self.inventory = inventory_client
        self.payment = payment_client
    
    def create_order(self, product_id, quantity):
        stock = self.inventory.check_stock(product_id)
        if stock < quantity:
            raise InsufficientStockError()
        
        price = self.inventory.get_price(product_id)
        total = price * quantity
        
        payment_id = self.payment.charge(total)
        
        return Order(product_id, quantity, payment_id)
```

```python
# test_order_service_solitaire.py
from unittest.mock import Mock

def test_create_order_success():
    # Alle Dependencies gemockt
    inventory = Mock()
    inventory.check_stock.return_value = 10
    inventory.get_price.return_value = 50.0
    
    payment = Mock()
    payment.charge.return_value = "pay_123"
    
    service = OrderService(inventory, payment)
    order = service.create_order(product_id=1, quantity=2)
    
    assert order.payment_id == "pay_123"
    inventory.check_stock.assert_called_once_with(1)
    payment.charge.assert_called_once_with(100.0)

def test_insufficient_stock():
    inventory = Mock()
    inventory.check_stock.return_value = 1  # Nur 1 verfügbar
    payment = Mock()
    
    service = OrderService(inventory, payment)
    
    with pytest.raises(InsufficientStockError):
        service.create_order(product_id=1, quantity=5)
    
    payment.charge.assert_not_called()  # Kein Payment bei Fehler
```

### Sociable Tests (echte Kollaborationen)

```python
# test_order_service_sociable.py

def test_order_flow_with_real_objects():
    # Echte Objekte, nur externe Services gemockt
    db = InMemoryDatabase()
    inventory_repo = InventoryRepository(db)
    price_calculator = PriceCalculator()
    
    # Nur externe HTTP-Calls gemockt
    payment_gateway = Mock()
    payment_gateway.charge.return_value = "pay_456"
    
    inventory_service = InventoryService(inventory_repo, price_calculator)
    order_service = OrderService(inventory_service, payment_gateway)
    
    # Setup Test-Daten
    inventory_repo.add_product(Product(id=1, stock=10, base_price=50))
    
    # Echte Interaktion zwischen Order + Inventory
    order = order_service.create_order(product_id=1, quantity=3)
    
    assert order.total == 150.0
    assert inventory_repo.get_stock(1) == 7  # Stock reduziert
    payment_gateway.charge.assert_called_with(150.0)
```

### Microservice-Kontext

```python
# test_api_endpoint_solitaire.py
def test_create_order_endpoint_solitaire():
    # Controller isoliert testen
    service = Mock()
    service.create_order.return_value = Order(1, 2, "pay_123")
    
    app = create_app(order_service=service)
    client = app.test_client()
    
    response = client.post('/orders', json={
        'product_id': 1,
        'quantity': 2
    })
    
    assert response.status_code == 201
    assert response.json['payment_id'] == "pay_123"
```

```python
# test_api_endpoint_sociable.py
def test_create_order_endpoint_sociable():
    # Kompletter Stack, nur DB + externe APIs gemockt
    db = TestDatabase()
    inventory_repo = InventoryRepository(db)
    inventory_service = InventoryService(inventory_repo)
    
    payment_gateway = Mock()
    order_service = OrderService(inventory_service, payment_gateway)
    
    app = create_app(order_service=order_service)
    client = app.test_client()
    
    # Setup
    db.insert_product(id=1, stock=10, price=50)
    payment_gateway.charge.return_value = "pay_789"
    
    # Test kompletter Request-Flow
    response = client.post('/orders', json={
        'product_id': 1,
        'quantity': 2
    })
    
    assert response.status_code == 201
    assert db.get_product_stock(1) == 8  # Echte DB-Änderung
```

## Wann was nutzen?

**Solitaire:**
- Schnell, stabil, keine Seiteneffekte
- Gut für Business-Logik und Edge Cases
- Fokus auf einzelne Klasse

**Sociable:**
- Testet echte Zusammenarbeit
- Findet Integrationsprobleme
- Näher am Produktionsverhalten
