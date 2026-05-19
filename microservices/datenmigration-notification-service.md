# Datenmigration: Notification Service aus dem Monolith loesen

Dieses Dokument zeigt anhand des **Notification Service** von ShopMax,
wie eine Datenbank-Migration beim Herausloesen eines Microservices konkret ablaeuft.

---

## Verwendete Patterns im Ueberblick

| Pattern | Zweck | Warum hier |
|---|---|---|
| **Database-per-Service** | Jeder Service bekommt eine eigene, isolierte DB | Zielarchitektur — ohne eigene DB sind Services faktisch noch ein Monolith |
| **Strangler Fig (DB-Ebene)** | Alte Tabelle bleibt waehrend der Migration erhalten | Kein Big-Bang-Cut — Monolith laeuft weiter, kein Datenverlustrisiko |
| **Backfill** | Historische Daten einmalig in neue DB kopieren | Neue DB muss vollstaendig sein, bevor Reads umgestellt werden koennen |
| **Dual Write** | Monolith schreibt waehrend Migration in beide DBs | Neue DB bleibt aktuell, bevor der Switch vollzogen ist |
| **Outbox Pattern** | Konsistenz beim Dual Write sicherstellen | Loest das Problem: Was passiert, wenn einer der beiden Writes fehlschlaegt? |

---

## Ausgangslage: Notifications in der Monolith-DB

Die `notifications`-Tabelle sitzt heute in der gemeinsamen Monolith-Datenbank
und haengt ueber Foreign Keys an `users` und `orders`:

```sql
-- Monolith-DB (PostgreSQL, gemeinsam mit allen anderen Services)

CREATE TABLE notifications (
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT NOT NULL REFERENCES users(id),
    order_id    BIGINT REFERENCES orders(id),
    type        VARCHAR(50)  NOT NULL,  -- ORDER_CONFIRMATION, SHIPPING, INVOICE
    channel     VARCHAR(20)  NOT NULL,  -- EMAIL, SMS
    content     TEXT,
    sent_at     TIMESTAMP,
    status      VARCHAR(20)  NOT NULL   -- SENT, FAILED, PENDING
);
```

```
+-----------------------------+
|       Monolith-DB           |
|                             |
|  users                      |
|  orders  <--+               |
|  notifications --+          |
|             |   |           |
|             FK  FK          |
+-----------------------------+
```

**Das Problem mit den Foreign Keys:**
Foreign Keys erzwingen, dass `users` und `orders` in derselben DB stehen wie `notifications`.
Solange das so ist, kann der Notification Service keine eigene DB haben —
er ist faktisch mit dem Monolith verdrahtet.

---

## Ziel: Eigene Datenbank fuer den Notification Service

Im Notification Service gibt es keine Foreign Keys mehr.
Statt `user_id` werden die benoetigten Daten denormalisiert gespeichert.

```sql
-- Notification-Service-DB (eigene PostgreSQL-Instanz)

CREATE TABLE notifications (
    id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    recipient_email  VARCHAR(255) NOT NULL,  -- denormalisiert, kein FK zu users
    recipient_phone  VARCHAR(50),
    order_reference  VARCHAR(100),           -- nur Referenz-ID, kein FK zu orders
    type             VARCHAR(50)  NOT NULL,
    channel          VARCHAR(20)  NOT NULL,
    content          TEXT,
    sent_at          TIMESTAMP,
    status           VARCHAR(20)  NOT NULL
);
```

```
+-----------------------------+     +-----------------------------+
|       Monolith-DB           |     |    Notification-Service-DB  |
|                             |     |                             |
|  users                      |     |  notifications              |
|  orders                     |     |    recipient_email (Text)   |
|  (notifications entfernt)   |     |    order_reference (Text)   |
|                             |     |    (keine Foreign Keys)     |
+-----------------------------+     +-----------------------------+
       [Monolith]                        [Notification Service]
```

**Warum Denormalisierung?**
Microservices duerfen nicht in fremde Datenbanken schauen.
Der Notification Service braucht nur die E-Mail-Adresse und die Bestellnummer —
diese Werte werden beim Schreiben mitgegeben, nicht nachtraeglich per JOIN geholt.

---

## Migrationspfad (Schritt fuer Schritt)

### Phase 1 — Neue Datenbank aufsetzen

**Pattern: Database-per-Service**

Als erstes wird nur die Infrastruktur bereitgestellt: eine eigene PostgreSQL-Instanz
fuer den Notification Service mit dem neuen Schema (ohne Foreign Keys).
Der neue Service laeuft noch nicht — die DB ist leer und wird noch nicht befuellt.

```
[Monolith-DB]                    [Notification-Service-DB]
  notifications (alt)              notifications (neu, leer)
  users
  orders
```

**Warum zuerst die DB, noch vor dem Code?**
Ohne DB kann der neue Service keine Daten speichern.
Der Backfill (Phase 2) braucht eine fertige Zieldatenbank.

---

### Phase 2 — Historische Daten migrieren

**Pattern: Backfill**

Die neue DB wird mit allen historischen Daten aus der Monolith-DB befuellt —
bevor der neue Service auch nur eine einzige neue Notification empfaengt.

```sql
-- Einmalige Backfill-Migration (laeuft gegen Monolith-DB)
-- Fuehrt den notwendigen JOIN noch einmal aus, um E-Mail + Telefon aufzuloesen

INSERT INTO notification_service_db.notifications
    (id, recipient_email, recipient_phone, order_reference,
     type, channel, content, sent_at, status)
SELECT
    gen_random_uuid(),
    u.email,
    u.phone,
    o.reference_number,
    n.type,
    n.channel,
    n.content,
    n.sent_at,
    n.status
FROM notifications n
JOIN users  u ON u.id = n.user_id
JOIN orders o ON o.id = n.order_id;
```

**Warum Backfill vor dem Code-Switch?**
Wuerde man den neuen Service zuerst aktivieren und erst danach den Backfill laufen lassen,
wuerde die neue DB neue Notifications empfangen, aber der historische Stand fehlt.
Reports, Retry-Logik und Kundenanfragen wuerden fehlschlagen.
Die neue DB muss vollstaendig sein, bevor der neue Service aktiv wird.

**Kontrolle nach dem Backfill:**

```sql
-- Zeilenanzahl muss uebereinstimmen
SELECT COUNT(*) FROM monolith_db.notifications;
SELECT COUNT(*) FROM notification_service_db.notifications;

-- Stichprobe: letzte 100 Eintraege manuell pruefen
SELECT * FROM notification_service_db.notifications
ORDER BY sent_at DESC LIMIT 100;
```

---

### Phase 3 — Strangler Fig auf Code-Ebene

**Pattern: Strangler Fig**

Erst jetzt — nachdem die neue DB vollstaendig befuellt ist — wird der Code umgeschaltet.
Der direkte Aufruf bleibt als Sicherheitsnetz erhalten, der neue Event-Pfad kommt dazu.

**Phase 3a: Beide Pfade parallel betreiben**

```java
// Uebergangsphase: alter Aufruf bleibt, neuer Event-Pfad kommt dazu
public Order placeOrder(Cart cart, Customer customer) {
    Order order = createOrder(cart);
    paymentService.charge(customer, order.total());

    // Alter Pfad: laeuft weiter (Sicherheitsnetz)
    notificationService.sendOrderConfirmation(customer.email(), order);

    // Neuer Pfad: Event fuer den Notification Service
    eventBus.publish(new OrderPlacedEvent(
        order.id(),
        customer.email(),
        customer.phone(),
        order.total()
    ));

    return order;
}
```

```
[Monolith]
    |
    |-- direkter Aufruf --> [Notification-Code im Monolith] --> [Monolith-DB]
    |
    +-- Event -----------> [Neuer Notification Service]     --> [Notification-Svc-DB]
```

Beide Pfade laufen gleichzeitig. Neue Notifications landen in beiden DBs —
die neue DB hat nun historische Daten (aus Phase 2) und neue Daten (aus Events).

**Phase 3b: Neuen Pfad verifizieren**

```sql
-- Vergleich: gleiche Anzahl neuer Notifications seit Aktivierung des neuen Pfads?
SELECT COUNT(*) FROM monolith_db.notifications        WHERE sent_at > '2024-01-15';
SELECT COUNT(*) FROM notification_svc_db.notifications WHERE sent_at > '2024-01-15';
```

**Phase 3c: Alten Pfad entfernen**

Erst wenn der neue Pfad bestaetigt ist:

```java
// Nur noch Event — direkter Aufruf entfernt
public Order placeOrder(Cart cart, Customer customer) {
    Order order = createOrder(cart);
    paymentService.charge(customer, order.total());

    eventBus.publish(new OrderPlacedEvent(
        order.id(),
        customer.email(),
        customer.phone(),
        order.total()
    ));

    return order;
}
```

**Warum parallel und nicht direkt umschalten?**
Ein sofortiger Switch ist ein Big-Bang-Cut ohne Fallback.
Beim parallelen Betrieb laeuft der Monolith weiter als Sicherheitsnetz,
bis der neue Pfad bewiesen ist.

---

### Phase 4 — Dual Write (nur zur Erklaerung — in der Praxis ueberspringen)

**Pattern: Dual Write**

> **Hinweis:** Phase 4 ist kein empfohlener Implementierungsschritt.
> Sie erklaert, was Teams ohne Outbox Pattern tun — und warum es ein Problem ist.
> In der Praxis direkt zu Phase 5 (Outbox) wechseln.

Ohne Outbox Pattern wuerde man versucht sein, einfach in beide DBs zu schreiben:

```java
// Im Monolith (naiver Ansatz)
public void sendNotification(Notification n) {
    monolithDb.insert(n);            // alte Tabelle
    notificationServiceDb.insert(n); // neue DB
}
```

```
[Monolith]
    |
    |-- INSERT --> [Monolith-DB: notifications]     (1. Write)
    |
    +-- INSERT --> [Notification-Service-DB]        (2. Write)
```

**Warum das nicht funktioniert:**
Die beiden INSERTs laufen nicht in einer Transaktion.
Schlaegt der zweite fehl (Netzwerkfehler, DB-Ausfall), ist die neue DB inkonsistent —
ohne Moeglichkeit zur automatischen Wiederholung. Datenverlust ist die Folge.
Deshalb: direkt zu Phase 5.

---

### Phase 5 — Outbox Pattern fuer Konsistenz

**Pattern: Outbox Pattern**

Statt direkt in beide DBs zu schreiben, schreibt der Monolith atomar
in seine eigene DB — aber zusaetzlich in eine `outbox`-Tabelle.
Ein separater Relay-Prozess liest die Outbox und schreibt in die neue DB.

```sql
-- Neue Tabelle in der Monolith-DB
CREATE TABLE notification_outbox (
    id          BIGSERIAL PRIMARY KEY,
    payload     JSONB NOT NULL,      -- serialisierte Notification
    created_at  TIMESTAMP DEFAULT NOW(),
    relayed_at  TIMESTAMP            -- NULL = noch nicht uebertragen
);
```

```java
// Im Monolith: alles in EINER Transaktion
@Transactional
public void sendNotification(Notification n) {
    monolithDb.insert(n);
    outboxDb.insert(toJson(n));   // atomar mit obigem INSERT
    // kein direkter Write in Notification-Service-DB mehr
}
```

**Ja — die Aktualisierung der Service-DB ist asynchron.**

Der Monolith kehrt sofort zurueck, nachdem er in seine eigene DB geschrieben hat.
Die Notification-Service-DB wird erst danach aktualisiert — durch einen
separaten Relay-Prozess, der unabhaengig laeuft:

```
[Monolith] --Transaktion--> [Monolith-DB]
    |                           |
    | (kehrt sofort zurueck)    | notification_outbox: neuer Eintrag
    v                           |
[Response an Client]            |
                                | (asynchron, Millisekunden bis Sekunden spaeter)
                                v
                       [Relay-Prozess]
                                |
                                v
                   [Notification-Service-DB]
```

**Variante A — Polling (einfacher):**

Ein Hintergrund-Thread fragt die Outbox-Tabelle direkt per SQL ab,
schreibt in die neue DB und markiert den Eintrag als verarbeitet:

```sql
-- Schritt 1: Offene Eintraege holen (Relay-Prozess liest Monolith-DB)
SELECT id, payload
FROM notification_outbox
WHERE relayed_at IS NULL
ORDER BY created_at
LIMIT 100;

-- Schritt 2: Eintrag in Notification-Service-DB schreiben
INSERT INTO notification_svc_db.notifications (...)
VALUES (...);  -- aus payload deserialisiert

-- Schritt 3: Eintrag als erledigt markieren (Empfehlung: UPDATE)
UPDATE notification_outbox
SET relayed_at = NOW()
WHERE id = 42;
```

Schritt 2 und 3 laufen in einer Transaktion — faellt die Notification-Service-DB aus,
wird auch der Status-Update nicht committed. Der Eintrag bleibt offen und wird
beim naechsten Polling-Durchlauf erneut verarbeitet.

**Warum UPDATE statt DELETE?**
Gerade waehrend einer Migration will man den Audit-Trail behalten:
- Bei Problemen sieht man *wann* welcher Eintrag uebertragen wurde
- Doppelt verarbeitete Eintraege sind erkennbar (`relayed_at` bereits gesetzt)

Die Tabellengroesse wird nicht im Relay-Prozess geloest, sondern durch einen
separaten Cleanup-Job:

```sql
-- Separater CronJob (z.B. taeglich)
-- 7 Tage Aufbewahrung fuer Fehleranalyse, danach loeschen
DELETE FROM notification_outbox
WHERE relayed_at < NOW() - INTERVAL '7 days';
```

Die 7 Tage sind ein Richtwert — je nach SLA und Debugging-Beduerfnis anpassen.

Verzoegerung: typisch < 1 Sekunde. Einfach umzusetzen, aber erzeugt staendige
DB-Abfragen auch wenn nichts zu tun ist.

**Variante B — CDC / Change Data Capture (robuster):**

Statt zu pollen, lauscht ein Tool wie **Debezium** auf den PostgreSQL Write-Ahead-Log (WAL).
Jeder neue Eintrag in `notification_outbox` loest sofort ein Event aus — ohne Polling.

```
[Monolith-DB: notification_outbox]
    |
    | WAL (Write-Ahead-Log) -- PostgreSQL schreibt jeden Commit ins Log
    v
[Debezium] (liest WAL, produziert Events)
    |
    v
[Kafka Topic: notification-outbox]
    |
    v
[Notification Service] -- konsumiert und schreibt in eigene DB
```

Verzoegerung: typisch < 100ms. Kein Polling, hohe Zuverlaessigkeit, aber
mehr Infrastruktur (Debezium, Kafka).

**Was passiert bei einem Fehler im Relay?**

Der Outbox-Eintrag bleibt in der Tabelle (`relayed_at` bleibt NULL).
Beim naechsten Durchlauf wird er erneut verarbeitet.
Die Notification-Service-DB ist damit **eventually consistent** —
sie wird garantiert aktualisiert, aber nicht zwingend sofort.

> **Konsequenz fuer den Notification Service:**
> Er darf keine Annahme treffen, dass ein Eintrag sofort da ist.
> Lese-Anfragen kurz nach einem Write koennen noch den alten Stand zeigen.
> Das ist in diesem Fall akzeptabel — eine E-Mail-Bestaetigung darf
> mit wenigen Sekunden Verzoegerung ankommen.

**Warum Outbox statt Dual Write?**
Dual Write ist nicht atomar — bei einem Fehler zwischen den beiden Writes
entsteht Datenverlust oder Inkonsistenz ohne Moeglichkeit zur Wiederholung.
Das Outbox Pattern schreibt atomar in eine DB und uebertraegt dann
mit Retry-Logik in die zweite. Jeder Eintrag wird garantiert genau einmal uebertragen.

---

### Phase 6 — Reads auf neue DB umstellen

Alle Stellen, die bisher aus `monolith_db.notifications` gelesen haben,
werden auf den Notification Service (bzw. seine DB) umgestellt.

```
Vorher: Monolith fragt SELECT * FROM notifications WHERE user_id = ?
Nachher: GET /notifications?userId=<ref>  -->  Notification Service
```

Dieser Schritt kann schrittweise erfolgen: erst Admin-UI, dann Reporting, dann Kundenbereich.

---

### Phase 7 — Alte Tabelle abschalten

Erst wenn alle Reads und Writes auf die neue DB umgestellt sind und der
Relay-Prozess keine offenen Eintraege mehr hat:

```sql
-- Outbox leeren (alle uebertragen)
SELECT COUNT(*) FROM notification_outbox WHERE relayed_at IS NULL;
-- Muss 0 sein

-- Alte Tabelle entfernen
DROP TABLE notification_outbox;
DROP TABLE notifications;  -- aus Monolith-DB
```

```
Vorher:                          Nachher:
+----------------+               +----------------+
| Monolith-DB    |               | Monolith-DB    |
|  notifications |               |  (keine noti.) |
|  outbox        |               +----------------+
+----------------+
                                 +----------------------+
                                 | Notification-Svc-DB  |
                                 |  notifications       |
                                 +----------------------+
```

---

## Zusammenfassung: Warum diese Reihenfolge?

```
Phase 1: Neue DB aufsetzen      (Database-per-Service) -- nur Infrastruktur, noch keine Daten
Phase 2: Backfill               -- neue DB vollstaendig befuellen, bevor neuer Service startet
Phase 3: Code entkoppeln        (Strangler Fig)        -- erst jetzt umschalten, DB ist bereit
Phase 4: (Dual Write)           -- nur zur Erklaerung, in der Praxis ueberspringen
Phase 5: Outbox                 -- zuverlaessiger Relay fuer laufende Writes
Phase 6: Reads umstellen        -- neuer Service uebernimmt
Phase 7: Alte Tabelle loeschen  -- Monolith vollstaendig entkoppelt
```

**Die entscheidende Reihenfolge:** DB aufsetzen → Backfill → Code umschalten.
Wer den Code zuerst umschaltet, hat einen aktiven Service mit einer leeren oder
unvollstaendigen DB — das fuehrt zu inkonsistenten Daten, die schwer rueckzusetzen sind.

Jede Phase ist einzeln rueckrollbar. Laeuft etwas schief, laeuft der
Monolith weiter auf der alten DB — kein Datenverlust, kein Ausfall.

> **Faustregel:** Die Datenmigration ist fertig, wenn kein Code mehr
> die alte Tabelle referenziert — weder fuer Reads noch fuer Writes.
> Erst dann ist der Schnitt wirklich vollzogen.
