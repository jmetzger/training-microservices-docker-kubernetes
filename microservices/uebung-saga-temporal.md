# Uebung: SAGA-Pattern mit Temporal

**Dauer:** ca. 90-120 Minuten
**Level:** Fortgeschritten
**Stack:** Java 17, Maven, Temporal, Docker Compose

---

## Lernziel

Du implementierst eine **Reisebuchungs-Saga** mit drei Schritten (Hotel, Flug, Zahlung).
Schlaegt ein Schritt fehl, werden die bereits erfolgreichen Schritte ueber
**Kompensationen** in umgekehrter Reihenfolge zurueckgenommen.

Nach der Uebung kannst du:

- Temporal als Workflow-Engine per Docker Compose betreiben
- Workflow und Activities trennen
- den `io.temporal.workflow.Saga`-Helper fuer Kompensationen nutzen
- Erfolgs- und Fehlerfall in der Temporal Web-UI beobachten

---

## Szenario

```
T1  bookHotel       --> C1  cancelHotel
T2  bookFlight      --> C2  cancelFlight
T3  chargePayment   --> (Pivot, keine Kompensation)
```

Schlaegt `chargePayment` fehl, laufen `cancelFlight` (C2) und `cancelHotel` (C1)
in umgekehrter Reihenfolge.

---

## Vorbereitung

```bash
git clone https://github.com/jmetzger/training-microservices-docker-kubernetes
cd training-microservices-docker-kubernetes/microservices/uebung-saga-temporal
```

---

## Schritt 1: Infrastruktur starten

```bash
docker compose up -d postgresql temporal temporal-ui
```

Warte bis Temporal healthy ist (ca. 30 Sekunden):

```bash
docker compose ps
# temporal muss Status "healthy" zeigen
```

Web-UI erreichbar unter: http://localhost:8233

---

## Schritt 2: Code-Struktur verstehen

```
src/main/java/de/t3isp/saga/
├── BookingActivities.java       # Activity-Interface mit 5 Methoden
├── BookingActivitiesImpl.java   # Implementierung mit Fehler bei amount > 1000
├── BookingWorkflow.java         # Workflow-Interface
├── BookingWorkflowImpl.java     # Saga-Logik mit Kompensationen
├── SagaWorker.java              # Registriert Workflow + Activities
├── SagaStarter.java             # Loest zwei Workflows aus (Erfolg + Fehler)
└── Main.java                    # Einstiegspunkt (worker | starter)
```

---

## Wie funktioniert das Zusammenspiel?

### Worker und Starter — zwei Rollen, ein Image

Beide Container werden aus demselben Dockerfile gebaut, uebernehmen aber verschiedene Aufgaben:

**`saga-worker`** — der Ausfuehrer (laeuft dauerhaft)
- Registriert `BookingWorkflowImpl` und `BookingActivitiesImpl` bei Temporal
- Wartet auf Aufgaben von der Task Queue `booking-saga`
- `restart: on-failure` — startet neu falls Temporal kurz nicht erreichbar ist

**`saga-starter`** — der Ausloser (laeuft einmalig)
- Loest zwei Workflows bei Temporal aus: 500 EUR (Erfolg) und 2000 EUR (Fehler)
- Beendet sich danach — `restart: "no"`
- Er fuehrt die Workflows nicht selbst aus, er beauftragt nur Temporal

```
saga-starter  -->  Temporal Server  -->  saga-worker
  (ausloesen)      (koordinieren,         (ausfuehren,
                    persistieren)          Activities laufen hier)
```

Temporal haelt den Workflow-State persistent. Faellt der Worker aus und kommt zurueck,
weiss Temporal genau wo der Workflow aufgehoert hat — und der Worker macht einfach weiter.

### Wie wird der Workflow definiert?

Der Kern der Uebung liegt in `BookingWorkflowImpl.java`:

```java
public String bookTrip(String bookingId, double amount) {
    Saga saga = new Saga(
        new Saga.Options.Builder()
            .setParallelCompensation(false)  // C2 vor C1, nicht gleichzeitig
            .build());

    try {
        activities.bookHotel(bookingId);
        saga.addCompensation(activities::cancelHotel, bookingId);  // C1

        activities.bookFlight(bookingId);
        saga.addCompensation(activities::cancelFlight, bookingId); // C2

        activities.chargePayment(bookingId, amount);               // Pivot (kein Cancel)

        return "Buchung " + bookingId + " erfolgreich";

    } catch (Exception e) {
        saga.compensate();        // ruft C2, dann C1 — umgekehrte Reihenfolge
        throw Workflow.wrap(e);   // Fehler an Temporal weiterleiten
    }
}
```

**Warum steht `addCompensation` immer NACH der jeweiligen Activity?**

Schlaegt `bookHotel` selbst fehl, gibt es nichts zu stornieren.
Stuende die Registrierung davor, wuerde `cancelHotel` fuer eine Buchung aufgerufen,
die nie stattgefunden hat. Durch die Reihenfolge "erst buchen, dann registrieren"
kompensiert die Saga nur, was tatsaechlich erfolgreich war.

### Wie loest der Starter einen Workflow aus?

```java
// 1. Verbindung zu Temporal aufbauen (gRPC, Port 7233)
WorkflowClient client = WorkflowClient.newInstance(
    WorkflowServiceStubs.newServiceStubs(
        WorkflowServiceStubsOptions.newBuilder()
            .setTarget("temporal:7233")
            .build()));

// 2. Workflow-Stub anlegen — nur lokales Proxy-Objekt, noch kein Netzwerkaufruf
BookingWorkflow workflow = client.newWorkflowStub(
    BookingWorkflow.class,
    WorkflowOptions.newBuilder()
        .setTaskQueue("booking-saga")   // welche Queue?
        .setWorkflowId("booking-001")   // eindeutige ID (fuer Deduplizierung)
        .build());

// 3. Workflow ausloesen — blockiert bis Workflow abgeschlossen ist
workflow.bookTrip("booking-001", 500.0);
```

Was dabei passiert:

```
Starter                    Temporal Server              Worker
   |                            |                          |
   |-- newWorkflowStub() -----> |                          |
   |   (nur lokal, kein Call)   |                          |
   |                            |                          |
   |-- bookTrip() ------------> |                          |
   |   (gRPC zu :7233)          | persistiert in PostgreSQL|
   |                            | <-- polling ------------ |
   |   (wartet...)              | -- Aufgabe ausgeben ----> |
   |                            |    Activities ausfuehren  |
   |                            | <-- Ergebnis ------------ |
   | <-- return / Exception --- |                          |
```

**Drei wichtige Punkte:**

- `newWorkflowStub()` erzeugt nur ein lokales Proxy-Objekt — noch kein Netzwerkaufruf
- `bookTrip()` sendet den Start-Request an Temporal per gRPC — Temporal persistiert den Auftrag sofort in PostgreSQL, bevor der Worker ihn abarbeitet
- Der Aufruf **blockiert** bis der Workflow fertig ist. Will man nicht warten, gibt es stattdessen `WorkflowClient.start(workflow, ...)` — dann laeuft der Workflow asynchron weiter

---

## Schritt 3: Worker und Starter ausfuehren

**Worker starten** (laeuft dauerhaft im Hintergrund):

```bash
docker compose up -d saga-worker
docker compose logs -f saga-worker
```

**Starter ausfuehren** (in neuem Terminal):

```bash
docker compose run --rm saga-starter starter
```

---

## Erwartete Ausgabe

**Starter:**

```
=== Fall 1: Erfolgsfall (500 EUR) ===
ERGEBNIS: Buchung booking-001 erfolgreich abgeschlossen

=== Fall 2: Fehlerfall (2000 EUR) — Kompensationen werden erwartet ===
ERGEBNIS: Workflow fehlgeschlagen (erwartet) — Kompensationen gelaufen
```

**Worker-Logs (Fall 1 — Erfolg):**

```
[booking-001] ✓ Hotel gebucht
[booking-001] ✓ Flug gebucht
[booking-001] ✓ Zahlung von 500.0 EUR erfolgreich
```

**Worker-Logs (Fall 2 — Fehler mit Kompensation):**

```
[booking-002] ✓ Hotel gebucht
[booking-002] ✓ Flug gebucht
[booking-002] ✗ Zahlung abgelehnt: 2000.0 EUR > Limit 1000 EUR
[booking-002] ↩ Flug STORNIERT (Kompensation C2)   <-- umgekehrte Reihenfolge
[booking-002] ↩ Hotel STORNIERT (Kompensation C1)
```

---

## Schritt 4: Web-UI beobachten

Oeffne http://localhost:8233 und vergleiche die Event-History beider Workflows:

- `booking-success-001` — alle Activities gruener Status, kein Fehler
- `booking-failure-001` — `ChargePayment` rot, danach `CancelFlight` und `CancelHotel` als Kompensation

**Frage:** In welcher Reihenfolge erscheinen die Kompensationen in der Event-History?
Erkennst du, dass C2 vor C1 laeuft?

---

## Kernkonzepte im Code

### Warum steht `addCompensation` nach der Activity?

```java
activities.bookHotel(bookingId);
saga.addCompensation(activities::cancelHotel, bookingId);  // NACH bookHotel
```

Schlaegt `bookHotel` selbst fehl, gibt es nichts zu stornieren.
Stuende die Registrierung davor, wuerde eine Buchung kompensiert, die nie stattgefunden hat.

### Wie laufen Kompensationen?

```java
} catch (Exception e) {
    saga.compensate();   // ruft C2, dann C1 — umgekehrte Reihenfolge
    throw Workflow.wrap(e);
}
```

`saga.compensate()` arbeitet die registrierten Kompensationen von hinten nach vorne ab.
Das entspricht dem Prinzip: zuletzt gebucht = zuerst storniert.

### Retry vs. Kompensation

In `BookingWorkflowImpl` ist `setMaximumAttempts(1)` gesetzt — keine Retries.
In echten Systemen wuerde Temporal die Activity bei transientem Fehler
automatisch wiederholen (konfigurierbar per `RetryOptions`).
Erst wenn alle Retries erschoepft sind, greift die Saga-Kompensation.

---

## Bonus-Aufgaben

### A) Retry einbauen

Entferne `setMaximumAttempts(1)` aus den `ActivityOptions` und lass `chargePayment`
beim ersten Aufruf fehlschlagen, beim zweiten erfolgreich sein (z.B. statischer Zaehler).
Beobachte: Temporal retryt die Activity — die Kompensation greift NICHT.
Warum ist das korrekt?

### B) Crash-Recovery

Baue ein `Thread.sleep(15000)` in `bookFlight` ein.
Starte den Worker (`docker compose restart saga-worker`) waehrend `bookFlight` schlaeft.
Beobachte in der Web-UI: der Workflow laeuft genau dort weiter, wo er aufgehoert hat.
Temporal persistiert den State — nicht der Worker.

### C) Parallele Kompensation

Setze in `BookingWorkflowImpl`:

```java
new Saga.Options.Builder().setParallelCompensation(true).build()
```

Frage: Wann ist parallele Kompensation sinnvoll, wann gefaehrlich?

---

## Aufraeumen

```bash
docker compose down -v
```

---

## Checkliste

- [ ] Infrastruktur gestartet, Web-UI erreichbar (http://localhost:8233)
- [ ] Erfolgsfall: drei Buchungen, keine Kompensation in den Logs
- [ ] Fehlerfall: `chargePayment` schlaegt fehl, `cancelFlight` + `cancelHotel` laufen
- [ ] Kompensationen laufen in umgekehrter Reihenfolge (C2 vor C1)
- [ ] Event-History beider Workflows in der Web-UI verglichen
