## OpenAPI-Spec als Contract-Basis

### 1. OpenAPI Definition (Inventory Service)

```yaml
# inventory-api.yaml
openapi: 3.0.0
info:
  title: Inventory Service
  version: 1.0.0
paths:
  /api/products/{id}:
    get:
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: integer
      responses:
        '200':
          description: Product found
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Product'
        '404':
          description: Not found

components:
  schemas:
    Product:
      type: object
      required: [id, name, stock, price]
      properties:
        id:
          type: integer
        name:
          type: string
        stock:
          type: integer
        price:
          type: number
```

### 2. Provider-Test (validiert Implementation gegen Spec)

```python
# test_provider_openapi.py
from schemathesis import from_uri

schema = from_uri("http://localhost:8080/openapi.yaml")

@schema.parametrize()
def test_api_against_spec(case):
    response = case.call()
    case.validate_response(response)
```

### 3. Consumer-Test (generiert Mock aus Spec)

```python
# test_consumer_openapi.py
import prism  # Prism Mock Server
from openapi_core import Spec

def test_order_service_with_mock():
    # Prism generiert Mock-Server aus OpenAPI
    mock = prism.start("inventory-api.yaml", port=4010)
    
    # Order Service nutzt Mock
    result = order_service.get_product(123)
    
    assert result["id"] == 123
    assert "name" in result
```

### 4. CI/CD Integration

```bash
# Provider prüft gegen Spec
spectral lint inventory-api.yaml
dredd inventory-api.yaml http://localhost:8080

# Consumer nutzt Spec für Mocks
prism mock inventory-api.yaml
```

**Vorteil:** OpenAPI = Single Source of Truth für beide Seiten.
