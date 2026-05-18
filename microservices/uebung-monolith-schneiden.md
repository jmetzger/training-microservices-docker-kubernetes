# Uebung: Monolith schneiden mit DDD und Strangler Fig Pattern

## Modell und Warum

Diese Uebung verwendet zwei aufeinander aufbauende Techniken:

| Technik | Zweck | Warum |
|---|---|---|
| **DDD Bounded Contexts** | Wo schneiden? | Fachliche Grenzen, nicht technische — der Code folgt der Sprache des Business |
| **Strangler Fig Pattern** | Wie migrieren? | Schrittweise Abloesung, kein riskanter Big-Bang-Rewrite |
| **Event Storming (vereinfacht)** | Was gehoert zusammen? | Sichtbar machen, welche Teile des Systems tatsaechlich zusammenhaengen |

> **Strangler Fig** = eine tropische Pflanze, die langsam um einen Baum waechst und ihn schliesslich ersetzt.
> Der alte Monolith laeuft weiter, waehrend neue Services ihn stueck fuer stueck ablösen.

---

## Ausgangslage: Der Monolith "ShopMax"

ShopMax ist ein 8 Jahre alter Online-Shop, entwickelt in Java/Spring Boot als einzelne
deploybare Einheit. Das Team klagt ueber:

- **Lange Deployments** (40 Min. Test + Build fuer jede Aenderung)
- **Angst vor Releases** (Bugfix in Zahlung bricht Produktsuche)
- **Skalierungsprobleme** (Black Friday: komplette App hochskalieren, obwohl nur Checkout bremst)
- **Team-Konflikte** (3 Teams arbeiten auf derselben Codebase, stoen sich gegenseitig)

### Aktuelle Architektur (vereinfacht)

```
+------------------------------------------------------------+
|                    ShopMax Monolith                        |
|                                                            |
|  UserService    ProductService    OrderService             |
|  CartService    PaymentService    ShippingService          |
|  InventoryService    NotificationService                   |
|                                                            |
|  +------------------+                                      |
|  |   PostgreSQL DB  |  (eine DB, alle Tabellen drin)       |
|  +------------------+                                      |
+------------------------------------------------------------+
         |
    Load Balancer
         |
      Frontend
```

**Alles in einem Prozess. Ein Fehler kann alles reissen.**

---

## Schritt 1: Domain Events identifizieren (Event Storming)

> **Warum dieser Schritt?**
> Bevor wir schneiden, muessen wir verstehen, was das System *tut*.
> Event Storming zeigt uns die wichtigen Geschaeftsereignisse — und damit die natuerlichen Grenzen.

### Aufgabe (15 Min., Gruppen zu 3-4 Personen)

Schreibt auf Post-its (oder Zettel) alle **Ereignisse** (Domain Events), die in ShopMax
passieren. Format: **Vergangenheitsform**, orange Post-it.

Beispiele als Einstieg:

```
KundeRegistriert       ProduktGespeichert      BestellungAufgegeben
ZahlungErfolgt         ZahlungFehlgeschlagen   BestellungStorniert
ArtikelInWarenkorbGelegt  WarenkorbGeleert      LieferungVersendet
LagerbestandAktualisiert  EmailVersendet        RechnungErstellt
```

**Ordnet die Events auf einer Zeitlinie an:**

```
[frueh]                                                     [spaet]

KundeRegistriert -> ProduktGesucht -> ArtikelInWarenkorbGelegt
  -> BestellungAufgegeben -> ZahlungErfolgt -> LagerbestandAktualisiert
  -> LieferungVersendet -> EmailVersendet -> RechnungErstellt
```

### Beobachtung

Markiert Events, die **thematisch zusammengehoeren** mit einer Farbe.
Ihr werdet feststellen: Einige Events gehoeren immer zusammen —
das sind Hinweise auf **Bounded Contexts**.

---

## Schritt 2: Bounded Contexts identifizieren

> **Warum dieser Schritt?**
> Ein Bounded Context ist ein fachlicher Bereich, der eine klare Sprache und
> klare Verantwortung hat. Innerhalb eines Contexts bedeutet "Bestellung" dasselbe.
> Ausserhalb koennte "Bestellung" etwas voellig anderes bedeuten.
>
> **Faustregeln:**
> - Ein Context = ein Team
> - Ein Context = eine Deployeinheit (spaeter)
> - Die Grenzen folgen der **Ubiquitous Language** (gemeinsamer Fachbegriffe)

### Kandidaten fuer ShopMax

Analysiert die Events und gruppiert sie:

```
+-------------------+    +-------------------+    +-------------------+
|   Kundenverwaltung |    |    Produktkatalog  |    |  Bestellprozess   |
|                   |    |                   |    |                   |
| KundeRegistriert  |    | ProduktGespeichert |    | BestellungAufgeg. |
| ProfilAktualisiert|    | PreisGeaendert     |    | BestellungStorn.  |
| KundeGeloescht    |    | LagerbestandAktual.|    | RechnungErstellt  |
+-------------------+    +-------------------+    +-------------------+

+-------------------+    +-------------------+    +-------------------+
|      Zahlung      |    |    Versand/Logistik|    |  Benachrichtigung |
|                   |    |                   |    |                   |
| ZahlungErfolgt    |    | LieferungVersendet |    | EmailVersendet    |
| ZahlungFehler     |    | LieferungZugestellt|    | SMSVersendet      |
| RueckzahlungInit. |    | RetourneEingeleitet|    | PushNotification  |
+-------------------+    +-------------------+    +-------------------+
```

### Diskussionsfragen

1. Warum ist "Lagerbestand" ein eigener Context und kein Teil von "Produktkatalog"?
   *(Hinweis: Wer aendert Lagerbestand? Wer aendert Produktdaten?)*

2. Koennte "Warenkorb" ein eigener Context sein? Was spricht dafuer, was dagegen?

3. Woran erkennt man, dass man einen Context **zu gross** oder **zu klein** geschnitten hat?

---

## Schritt 3: Context Map zeichnen

> **Warum dieser Schritt?**
> Contexts interagieren miteinander. Die Context Map zeigt, wer von wem abhaengt
> und welche Integration-Patterns verwendet werden.
> Sie macht unsichtbare Kopplungen sichtbar — und damit sichtbar,
> wo wir anfangen sollten zu schneiden.

### Aufgabe

Zeichnet die Abhaengigkeiten zwischen den Contexts:

```
                    +-------------------+
                    |   Produktkatalog  |
                    +-------------------+
                         ^         ^
                         |         |
          +--------------+         +-------------+
          |                                      |
+-------------------+                  +-------------------+
|   Bestellprozess  |                  |  Lagerbestand     |
+-------------------+                  +-------------------+
     ^         |
     |         | ---publiziert--->
     |         | BestellungAufgegeben
     |         v
+-------------------+    +-------------------+
|      Zahlung      |    |  Benachrichtigung |
+-------------------+    +-------------------+
          |                      ^
          | ---publiziert------> |
          | ZahlungErfolgt ------+
          v
+-------------------+
|  Versand/Logistik |
+-------------------+
```

### Integration-Patterns markieren

Tragt fuer jede Verbindung ein, wie die Kommunikation stattfindet:

| Von | Nach | Aktuell (Monolith) | Ziel (Microservices) |
|---|---|---|---|
| Bestellprozess | Zahlung | direkter Methodenaufruf | sync REST / async Event |
| Zahlung | Versand | direkter Methodenaufruf | async Event (ZahlungErfolgt) |
| Zahlung | Benachrichtigung | direkter Methodenaufruf | async Event |
| Bestellprozess | Lagerbestand | direkter DB-Zugriff | async Event / sync REST |

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

Bewertet jeden Context von 1 (schlecht) bis 5 (gut) fuer den ersten Schnitt:

| Context | Wenige Abhaeng. | Business-Wert | Klare Verantwort. | Isolierte DB | **Score** |
|---|---|---|---|---|---|
| Benachrichtigung | 5 | 2 | 5 | 4 | **16** |
| Produktkatalog | 3 | 4 | 4 | 3 | **14** |
| Versand/Logistik | 3 | 3 | 4 | 4 | **14** |
| Zahlung | 2 | 5 | 3 | 3 | **13** |
| Bestellprozess | 1 | 5 | 2 | 2 | **10** |
| Kundenverwaltung | 3 | 3 | 3 | 3 | **12** |

**Ergebnis:** Wir starten mit **Benachrichtigung**, dann **Produktkatalog**.

> **Warum Benachrichtigung zuerst?**
> - Keine anderen Services haengen davon ab (nur eingehende Events)
> - Kein kritischer Pfad (Ausfall = keine Email, kein Bestellungsfehler)
> - Einfach isolierbar: eigene DB-Tabellen, kein DB-Join mit anderen
> - Team kann lernen, ohne Risiko fuer den Checkout

### Migrationsphasen

```
Phase 0 (heute):    [Kompletter Monolith]

Phase 1 (Monat 1):  [Monolith ohne Notification] + [Notification Service]
                         Strangler Proxy leitet Notification-Calls um

Phase 2 (Monat 3):  [Monolith ohne Notification, ohne Produkt] + [Product Service]
                                                               + [Notification Service]

Phase 3 (Monat 6):  [Monolith-Kern: Bestellung+Zahlung] + [weitere Services]

Phase N:            [Bestellprozess Service] + [Zahlungs Service] + ...
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
