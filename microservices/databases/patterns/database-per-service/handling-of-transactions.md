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

## Produkte

  * Camunda (Framework)
  * Apache Camel

## Referenzen

  * https://www.baeldung.com/cs/saga-pattern-microservices#introduction-to-saga
  * https://microservices.io/patterns/data/saga.html
