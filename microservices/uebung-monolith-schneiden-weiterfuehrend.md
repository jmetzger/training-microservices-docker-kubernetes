# Uebung: Monolith schneiden — Weiterfuehrende Schritte (Schritt 3-7)

> Dieses Material baut auf Schritt 1 und 2 aus `uebung-monolith-schneiden.md` auf.
> Einsatz je nach verfuegbarer Zeit und Gruppenfortschritt.

---

## Schritt 3: Context Map zeichnen

> **Warum dieser Schritt?**
> Contexts interagieren miteinander. Die Context Map zeigt, wer von wem abhaengt
> und welche Integration-Patterns verwendet werden.
> Sie macht unsichtbare Kopplungen sichtbar — und damit sichtbar,
> wo wir anfangen sollten zu schneiden.

### Aufgabe

Zeichnet die Abhaengigkeiten zwischen euren Contexts aus Schritt 2.

Fuer jede Verbindung: Wer braucht Daten von wem? Wer publiziert Events, wer abonniert sie?

> **Was bedeutet "publiziert"?**
> Ein Service sendet ein Domain Event auf einen Event Bus (z.B. Kafka).
> Er interessiert sich nicht dafuer, wer zuhoert — er publiziert einfach.
> Andere Services *subscriben* selbst auf Events, die sie brauchen.
> Vorteil: Der Sender hat keine Abhaengigkeit zum Empfaenger.

### Integration-Patterns markieren

Tragt fuer jede Verbindung ein, wie die Kommunikation stattfindet:

| Von | Nach | Aktuell (Monolith) | Ziel (Microservices) |
|---|---|---|---|
| | | | |
| | | | |
| | | | |
| | | | |

> **Schluesserkenntnis:** Ueberall wo heute ein *direkter Methodenaufruf* steht,
> haben wir eine **enge Kopplung**. Diese muss vor dem Schneiden entkoppelt werden.

---

## Schritt 4: Strangler Fig — Migrationsreihenfolge planen

> **Warum dieser Schritt?**
> Wir koennen nicht alle Contexts gleichzeitig herausloesen — das waere ein Big-Bang-Rewrite
> mit hohem Risiko. Der Strangler Fig Plan legt fest, in welcher **Reihenfolge** wir
> vorgehen und wie der Monolith dabei weiterlaeuft.
>
> **Kriterien fuer den ersten Schnitt:**
> - Geringer Abhaengigkeiten zu anderen Contexts (wenige Verbindungen in der Context Map)
> - Hoher Business-Wert (z.B. Skalierungsproblem loesen)
> - Klare Sprache / klare Teamverantwortung
> - Keine Datenbankfremdzugriffe von anderen Contexts

### Bewertungsmatrix

Tragt eure Contexts aus Schritt 2 ein und bewertet jeden von 1 (schlecht) bis 5 (gut):

| Context | Wenige Abhaeng. | Business-Wert | Klare Verantwort. | Isolierte DB | **Score** |
|---|---|---|---|---|---|
| | | | | | |
| | | | | | |
| | | | | | |
| | | | | | |
| | | | | | |
| | | | | | |

**Welcher Context hat den hoechsten Score? Das ist euer Startkandidat.**

### Migrationsphasen

Plant anhand eures Startkandiaten, wie der Monolith schrittweise abgeloest wird:

```
Phase 0 (heute):  [Kompletter Monolith]

Phase 1:          [Monolith ohne Context A] + [Service A]
                       Strangler Proxy leitet Calls um

Phase 2:          [Monolith ohne A, ohne B] + [Service A] + [Service B]

Phase N:          [letzte Contexts als Services]
                       Monolith ist "stranguliert" = leer = abgeschaltet
```

---

## Schritt 5: Anti-Corruption Layer (ACL) einplanen

> **Warum dieser Schritt?**
> Wenn ein neuer Microservice mit dem alten Monolith kommunizieren muss,
> soll er **nicht** die interne Sprache des Monolithen uebernehmen.
> Der ACL uebersetzt zwischen den Modellen — er schuetzt den neuen Service
> vor der technischen Schuld des Monolithen.

### Beispiel: Notification Service greift auf Kundendaten zu

**Ohne ACL (falsch):**

```
Notification Service                 Monolith
      |                                  |
      |-- GET /internal/user/42 -------> |
      |<-- { user_id: 42,               |
      |       usr_mail: "...",           |  Monolith-internes Modell
      |       created_ts: 1234567 } ---- |  leckt in den neuen Service
```

**Mit ACL (richtig):**

```
Notification Service    ACL (Adapter)          Monolith
      |                      |                     |
      |-- getRecipient(42) -> |                     |
      |                      |-- GET /internal/user/42 -->|
      |                      |<-- { usr_mail, ... } ------|
      |<-- Recipient{         |  (uebersetzt Monolith-
      |     email: "...",     |   Modell in eigene Sprache)
      |     name: "..." } --- |
```

### Aufgabe

Skizziert fuer den **Notification Service** einen ACL:

1. Welche Daten braucht der Notification Service vom Monolith?
2. Wie soll das interne Modell des Notification Service aussehen?
3. Was uebersetzt der ACL?

---

## Schritt 6: Datenbankstrategie planen

> **Warum dieser Schritt?**
> Die **geteilte Datenbank** ist das groesste Hindernis beim Schneiden.
> Solange zwei Services in dieselbe DB schreiben, sind sie faktisch ein Monolith —
> egal wie viele Services man deployt.

### Database-per-Service Strategie

```
ZIEL: Jeder Service hat seine eigene, isolierte Datenbank

+-------------------+    +-------------------+    +-------------------+
| Notification DB   |    |  Product DB       |    |  Order DB         |
| (PostgreSQL)      |    |  (PostgreSQL)     |    |  (PostgreSQL)     |
|                   |    |                   |    |                   |
| notifications     |    | products          |    | orders            |
| templates         |    | categories        |    | order_items       |
+-------------------+    +-------------------+    +-------------------+
       ^                        ^                        ^
       |                        |                        |
[Notification Svc]        [Product Svc]            [Order Svc]
```

### Migrationsstrategie fuer die DB

**Problem:** Die `notifications`-Tabelle steht heute in der Monolith-DB,
mit Foreign Keys zu `users`, `orders`, etc.

**Loesungsschritte:**

```
1. Tabelle duplizieren (Strangler Phase):
   - Neue notifications-DB anlegen
   - Monolith schreibt in BEIDE DBs (dual-write)
   - Notification Service liest nur aus neuer DB

2. Consumers umstellen:
   - Monolith liest jetzt auch aus neuer DB

3. Alte Tabelle abschalten:
   - Monolith schreibt nur noch in neue DB (ueber API-Call)
   - Foreign Keys weg (durch Events ersetzt)
```

### Diskussionsfrage

Was passiert, wenn der Dual-Write fehlschlaegt? Eine DB wird geschrieben, die andere nicht.
Wie loest ihr das? *(Hinweis: Outbox Pattern, Saga Pattern)*

---

## Schritt 7: Den ersten Service ausloesen (praktisch)

> Jetzt wird es konkret. Wir loesen den **Notification Service** aus dem Monolith.

### Vorher: Notification-Code im Monolith

```
// OrderService.java (im Monolith)
public Order placeOrder(Cart cart, Customer customer) {
    Order order = createOrder(cart);
    paymentService.charge(customer, order.total());
    inventoryService.reserve(cart.items());

    // Direkte Kopplung - das wollen wir weg
    notificationService.sendOrderConfirmation(customer.email(), order);

    return order;
}
```

### Problem

`OrderService` muss wissen, wie Notifications funktionieren.
Wenn `notificationService.sendOrderConfirmation()` fehlschlaegt, schlaegt die ganze Bestellung fehl.

### Schritt 7a: Event statt direkter Aufruf

```
// OrderService.java (refactored, noch im Monolith)
public Order placeOrder(Cart cart, Customer customer) {
    Order order = createOrder(cart);
    paymentService.charge(customer, order.total());
    inventoryService.reserve(cart.items());

    // Kein direkter Aufruf mehr - nur Event publizieren
    eventBus.publish(new OrderPlacedEvent(
        order.id(),
        customer.email(),
        order.items(),
        order.total()
    ));

    return order;
}
```

**Was aendert sich?**
- `OrderService` kennt `NotificationService` nicht mehr
- Notification kann asynchron, spaeter, oder gar nicht ankommen — Bestellung ist trotzdem fertig
- Notification Service kann deployed werden, ohne den Monolith zu kennen

### Schritt 7b: Notification Service als eigener Prozess

```
// NotificationService (neuer, eigenstaendiger Service)
@EventHandler
public void on(OrderPlacedEvent event) {
    Email email = templateEngine.render(
        "order-confirmation",
        Map.of("orderId", event.orderId(),
               "items",   event.items())
    );
    emailGateway.send(event.customerEmail(), email);
}
```

### Schritt 7c: Strangler Proxy (falls REST-APIs umgeleitet werden muessen)

```
         Request: POST /api/notify/order-confirmation
                         |
               +--------------------+
               |   API Gateway /    |
               |  Strangler Proxy   |
               +--------------------+
                   /            \
          (alt)   /              \ (neu, wenn Feature-Flag aktiv)
                 /                \
    +------------------+    +-------------------+
    |     Monolith     |    | Notification Svc  |
    | /api/notify/...  |    | /api/notify/...   |
    +------------------+    +-------------------+
```

Der Proxy leitet Traffic schrittweise um — z.B. 10% zum neuen Service, dann 50%, dann 100%.

---

## Ergebnis-Praesentation (10 Min. pro Gruppe)

Praesentiert eurer Ergebnis:

1. **Context Map** — welche Contexts habt ihr identifiziert?
2. **Erste Priorisierung** — mit welchem Context fangt ihr an und warum?
3. **Ein kritischer Schnitt** — wo ist es schwierig? Was koennte schiefgehen?

---

## Zusammenfassung: Wann ist ein Schnitt gut?

| Kriterium | Gut | Schlecht |
|---|---|---|
| Kommunikation | Async Events oder klar definierte APIs | Direkter DB-Zugriff fremder Services |
| Daten | Jeder Service hat eigene DB | Geteilte DB-Tabellen |
| Deployment | Service deployed unabhaengig | Release braucht Koordination mit anderen |
| Sprache | Einheitliche Fachbegriffe im Context | "Bestellung" bedeutet in zwei Services verschiedenes |
| Teamverantwortung | Ein Team = ein Service | Mehrere Teams aendern denselben Service |
| Fehler | Ausfall isoliert (Circuit Breaker) | Ein Ausfall reisst alles |

> **Faustregel:** Wenn ihr mehr als 30 Minuten braucht, um zu erklaeren,
> welches Team fuer ein Feature verantwortlich ist — dann ist der Schnitt falsch.
