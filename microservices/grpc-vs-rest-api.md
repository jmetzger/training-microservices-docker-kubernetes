# gRPC für Microservices

## 1. Kernvergleich gRPC vs REST

| Kriterium | gRPC | REST |
|-----------|------|------|
| **Protokoll/Format** | HTTP/2 + Protocol Buffers (binär) | HTTP/1.1 + JSON (text) |
| **Performance** | ~7-10x schneller, kleinere Payloads | Langsamer, größere Payloads |
| **Streaming** | Unidirektional, bidirektional, Server/Client | Nicht nativ (Workarounds möglich) |
| **Browser-Support** | Eingeschränkt (gRPC-Web nötig) | Nativ unterstützt |
| **Typsicherheit** | Stark typisiert durch .proto | Schwach, manuelle Validierung |
| **Code-Generierung** | Automatisch für Client/Server | Manuell oder über OpenAPI |

## 2. Praxisbeispiel: User-Service ↔ Order-Service

### Proto-Definition (`user.proto`)
```protobuf
syntax = "proto3";

package user;

service UserService {
  // Einfacher Request
  rpc GetUser(UserRequest) returns (UserResponse);
  
  // Server-Streaming: Live-Updates zu User-Aktivitäten
  rpc StreamUserActivity(UserRequest) returns (stream ActivityEvent);
}

message UserRequest {
  string user_id = 1;
}

message UserResponse {
  string user_id = 1;
  string name = 2;
  string email = 3;
  int32 credit_score = 4;
}

message ActivityEvent {
  string user_id = 1;
  string event_type = 2;
  int64 timestamp = 3;
}
```

### Server-Implementierung (Python)
```python
import grpc
from concurrent import futures
import user_pb2
import user_pb2_grpc
import time

class UserService(user_pb2_grpc.UserServiceServicer):
    
    def GetUser(self, request, context):
        # Simuliere DB-Zugriff
        return user_pb2.UserResponse(
            user_id=request.user_id,
            name="Max Mustermann",
            email="max@example.com",
            credit_score=750
        )
    
    def StreamUserActivity(self, request, context):
        # Server-Streaming: Sende Live-Events
        events = ['login', 'purchase', 'logout']
        for event in events:
            yield user_pb2.ActivityEvent(
                user_id=request.user_id,
                event_type=event,
                timestamp=int(time.time())
            )
            time.sleep(2)  # Simuliere Events über Zeit

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    user_pb2_grpc.add_UserServiceServicer_to_server(UserService(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    print("User-Service läuft auf Port 50051")
    server.wait_for_termination()

if __name__ == '__main__':
    serve()
```

### Client-Implementierung im Order-Service (Python)
```python
import grpc
import user_pb2
import user_pb2_grpc

class OrderService:
    
    def __init__(self):
        # Verbindung zum User-Service
        self.channel = grpc.insecure_channel('user-service:50051')
        self.user_stub = user_pb2_grpc.UserServiceStub(self.channel)
    
    def create_order(self, user_id, items):
        # 1. User-Daten abrufen
        user = self.user_stub.GetUser(user_pb2.UserRequest(user_id=user_id))
        
        # 2. Credit-Check
        if user.credit_score < 600:
            return {"error": "Insufficient credit score"}
        
        # 3. Order erstellen
        order = {
            "user_id": user.user_id,
            "user_name": user.name,
            "items": items,
            "status": "confirmed"
        }
        return order
    
    def monitor_user_activity(self, user_id):
        # Server-Streaming: Empfange Live-Events
        print(f"Überwache Aktivität von User {user_id}...")
        
        for activity in self.user_stub.StreamUserActivity(
            user_pb2.UserRequest(user_id=user_id)
        ):
            print(f"Event: {activity.event_type} um {activity.timestamp}")
            
            # Reagiere auf Events
            if activity.event_type == 'purchase':
                print("→ Triggere Empfehlungs-Engine")

# Verwendung
order_service = OrderService()
order = order_service.create_order("user123", ["item1", "item2"])
print(order)

# Live-Monitoring
order_service.monitor_user_activity("user123")
```

### Code generieren
```bash
# Proto-Dateien kompilieren
python -m grpc_tools.protoc -I. --python_out=. --grpc_python_out=. user.proto
```

## 3. Wann was verwenden?

### Nutze gRPC wenn:
- **Interne Microservices** (Service-to-Service)
- **Performance kritisch** (High-Throughput, Low-Latency)
- **Streaming benötigt** (Real-time Updates, Logs)
- **Polyglotte Umgebung** (automatische Code-Generierung für viele Sprachen)

### Nutze REST wenn:
- **Public APIs** (externe Partner, Entwickler)
- **Browser-Clients** (Web-Apps ohne gRPC-Web)
- **Einfachheit bevorzugt** (schnelles Prototyping, Debugging)
- **HTTP/1.1-Infrastruktur** (Legacy-Systeme, bestimmte Proxies)

## Was wird generiert:

Aus `user.proto` generiert der Compiler:

```bash
python -m grpc_tools.protoc -I. --python_out=. --grpc_python_out=. user.proto
```

**Erzeugt automatisch:**
- `user_pb2.py` - Message-Klassen (UserRequest, UserResponse, etc.)
- `user_pb2_grpc.py` - Stub-Klassen und Service-Basis

```python
# user_pb2_grpc.py (generiert)
class UserServiceServicer(object):  # ← Basis für Server
    def GetUser(self, request, context):
        raise NotImplementedError()
    
    def StreamUserActivity(self, request, context):
        raise NotImplementedError()

class UserServiceStub(object):  # ← Für Client
    def __init__(self, channel):
        self.GetUser = channel.unary_unary(...)
        self.StreamUserActivity = channel.unary_stream(...)
```

## Was du selbst schreibst:

✍️ **Server:** Business-Logik in `UserService(UserServiceServicer)` - erbt nur von generierter Basis

✍️ **Client:** `OrderService` - nutzt generierten `UserServiceStub`, aber Logik ist manuell

**Zusammenfassung:** Der Compiler gibt dir typsichere Schnittstellen, die Implementierung schreibst du.
