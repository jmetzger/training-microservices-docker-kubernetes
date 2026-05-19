# Umgang mit Transaktionen

## Problem

  * Wenn wir auf das Database-per-Service-Pattern wechseln, koennen wir keine klassischen Datenbanktransaktionen mehr verwenden

## Beispiel-Problem

  * Das Database-per-Service-Pattern ist im Einsatz

```
x Ein Online-Shop
x Kunden haben ein Kreditlimit
x Die Anwendung muss sicherstellen, dass eine neue Bestellung das Kreditlimit des Kunden nicht ueberschreitet
x Bestellungen und Kunden liegen in verschiedenen Datenbanken, die von verschiedenen Services verwaltet werden
x Deshalb: Die Anwendung kann keine einfache lokale ACID-Transaktion verwenden
```

## Schaubild (Wie funktioniert es?)

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/f4615f49-5937-476e-bff7-d32e7de870c9)

## Saga Execution Coordinator (SEC) als zentrale Komponente

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/a33eb0a1-0e04-48a7-983c-9b6741202afe)

  * Enthaelt ein Saga-Log, das die Abfolge der Ereignisse einer verteilten Transaktion aufzeichnet
  * BEI FEHLER: Die SEC-Komponente prueft das Saga-Log, um die betroffenen Komponenten und die Reihenfolge der Kompensationstransaktionen zu ermitteln
  * Sie kann erkennen, welche Transaktionen erfolgreich zurueckgerollt wurden, welche noch ausstehen, und entsprechende Massnahmen einleiten

## Implementierung als Saga-Choreography-Pattern

### Wann?

  * Greenfield (Entwicklung von Grund auf neu)

### Wie?

  * Jeder Microservice, der Teil der Transaktion ist, veroeffentlicht ein Event, das vom naechsten Microservice verarbeitet wird

### Schaubild (Erfolgsfall)

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/9261961c-41f7-4d96-b260-c64f332b6d14)

### Schaubild (Fehlerfall)

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/0118fe17-6e95-4281-b305-1e33c868062c)

## Implementierung als Saga-Orchestration-Pattern

### Wann?

  * Brownfield (es existieren bereits Microservices)

### Wie?

  * Ein Orchestrator uebernimmt die Steuerung des gesamten Transaktionsprozesses

### Schaubild

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/d71df512-af4d-4eef-a3ab-23d4b3f369e3)

## Kompensationstransaktionen

Eine Kompensationstransaktion macht den fachlichen Effekt eines bereits committeten
Schritts rueckgaengig — weil ein `ROLLBACK` ueber Service-Grenzen hinweg nicht moeglich ist.

### Beispiel: Bestellung schlaegt in Schritt 3 fehl

```
Schritt 1: Order-Service legt Bestellung an        → in Order-DB committed ✓
Schritt 2: Customer-Service reduziert Kreditlimit  → in Customer-DB committed ✓
Schritt 3: Payment-Service bucht Zahlung           → FEHLER ✗
```

Da Schritt 1 und 2 bereits in ihren jeweiligen Datenbanken committed sind,
kann kein technisches `ROLLBACK` mehr helfen. Der Saga Execution Coordinator
startet stattdessen Kompensationstransaktionen in umgekehrter Reihenfolge:

```
Kompensation zu Schritt 2: Customer-Service stellt Kreditlimit wieder her
Kompensation zu Schritt 1: Order-Service storniert die Bestellung
```

### Konkrete SQL-Beispiele

**Schritt 2 — Original (Kreditlimit reduzieren):**

```sql
-- Customer-DB
UPDATE customers
SET credit_limit = credit_limit - 150.00
WHERE id = 42;
```

**Kompensation zu Schritt 2 — Kreditlimit wiederherstellen:**

```sql
-- Customer-DB
UPDATE customers
SET credit_limit = credit_limit + 150.00
WHERE id = 42;
```

**Schritt 1 — Original (Bestellung anlegen):**

```sql
-- Order-DB
INSERT INTO orders (id, customer_id, amount, status)
VALUES ('ord-99', 42, 150.00, 'PENDING');
```

**Kompensation zu Schritt 1 — Bestellung stornieren:**

```sql
-- Order-DB
UPDATE orders
SET status = 'CANCELLED'
WHERE id = 'ord-99';
-- kein DELETE: Bestellung bleibt fuer Audit-Trail erhalten
```

### Wichtige Eigenschaften einer Kompensationstransaktion

- **Kein technisches Rollback** — sie ist eine neue, eigenstaendige Datenbankoperation
- **Idempotent** — sie muss mehrfach ausfuehrbar sein ohne zusaetzlichen Schaden
  (der SEC kann sie bei Fehler erneut aufrufen)
- **Kann selbst fehlschlagen** — der SEC muss auch das abfangen und wiederholen
- **Kein exakter Rueckgaengig-Effekt** — zwischen Original und Kompensation koennen
  andere Transaktionen gelaufen sein (z.B. hat der Kunde inzwischen etwas anderes bestellt)

> **Faustregel:** Kompensationstransaktionen implementieren "Business Undo",
> nicht "Technical Rollback". Der Zustand wird fachlich korrigiert,
> nicht technisch zurueckgesetzt.

## Produkte

  * Camunda (Framework)
  * Apache Camel

## Referenzen

  * https://www.baeldung.com/cs/saga-pattern-microservices#introduction-to-saga
  * https://microservices.io/patterns/data/saga.html
