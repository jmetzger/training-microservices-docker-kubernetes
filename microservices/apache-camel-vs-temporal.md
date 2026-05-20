# Apache Camel vs. Temporal

## Herkunft

**Apache Camel** implementiert die **Enterprise Integration Patterns (EIP)** —
ein Katalog von Loesungen fuer typische Integrationsprobleme aus dem gleichnamigen Buch
von Gregor Hohpe & Bobby Woolf (2003).

![EIP-Grafik: Routing, Splitter, Translator, Aggregator](/images/eip-grafik.svg)

Die zentralen EIP-Bausteine in Camel:

| Pattern | Was es tut |
|---|---|
| **Content-Based Router** | Nachricht je nach Inhalt an verschiedene Ziele leiten |
| **Splitter** | Eine Nachricht in mehrere Teile aufteilen |
| **Aggregator** | Mehrere Nachrichten zu einer zusammenfassen |
| **Translator** | Format einer Nachricht umwandeln (z.B. XML → JSON) |
| **Dead Letter Channel** | Fehlgeschlagene Nachrichten separat behandeln |

---

## Direkter Vergleich

| | **Apache Camel** | **Temporal** |
|---|---|---|
| **Herkunft** | Enterprise Integration Patterns (EIP) | Workflow-Orchestrierung |
| **Kernfrage** | Wie verbinde und transformiere ich Systeme? | Wie orchestriere ich Geschaeftsprozesse zuverlaessig? |
| **Denkweise** | Nachrichten fliessen durch Routen | Code beschreibt Schritt-fuer-Schritt-Ablaeufe |
| **State** | Zustandslos (Nachrichten sind ephemer) | Zustandsbehaftet (Workflow-State wird persistiert) |
| **Fehlerbehandlung** | Dead Letter Channel, Retry in der Route | Automatische Retries, Saga-Kompensation |
| **Laufzeit** | Millisekunden bis Sekunden | Sekunden bis Monate (Long-Running) |
| **Staerke** | Protokoll-Uebersetzung, Routing, ETL | Business-Prozesse, Sagas, verteilte Transaktionen |
| **DSL** | Java, XML, YAML (deklarativ) | Java, Go, Python, TypeScript (Code) |

---

## Wann welches Tool?

**Apache Camel nehmen, wenn:**
- Systeme mit verschiedenen Protokollen verbunden werden muessen
  (HTTP, Kafka, FTP, SFTP, JDBC, S3, ...)
- Nachrichten transformiert oder geroutet werden sollen
- ETL-Pipelines gebaut werden
- Die Logik in Routing-Regeln ausgedrueckt werden kann

```
Beispiel: Bestellungen aus einer REST-API lesen,
          in XML umwandeln und per SFTP an ein ERP schicken.
```

**Temporal nehmen, wenn:**
- Geschaeftsprozesse mit mehreren Schritten orchestriert werden
- Kompensationen (Saga) benoetigt werden
- Workflows Tage oder Wochen laufen koennen
- Crash-Recovery ohne Datenverlust wichtig ist

```
Beispiel: Reisebuchung (Hotel → Flug → Zahlung),
          bei Fehler alles in umgekehrter Reihenfolge stornieren.
```

**Apache Camel fuer SAGA nehmen, wenn:**
- Camel bereits fuer Integration im Einsatz ist
- Die Saga kurzlebig ist (Sekunden bis Minuten)
- Kompensationslogik einfach und flach ist
- Kein persistierter State ueber Crashes hinweg benoetigt wird

Camel hat seit Version 2.21 einen eigenen **Saga EIP** eingebaut:

```java
// Saga-Route: Schritte definieren
from("direct:bookTrip")
    .saga()
        .to("direct:bookHotel")
        .to("direct:bookFlight")
        .to("direct:chargePayment")
    .end();

// Hotel buchen — mit Kompensation
from("direct:bookHotel")
    .saga()
    .compensation("direct:cancelHotel")
    .log("Hotel gebucht")
    .to("http://hotel-service/book");

// Kompensation: Hotel stornieren
from("direct:cancelHotel")
    .log("Hotel storniert (Kompensation)")
    .to("http://hotel-service/cancel");
```

Schlaegt ein Schritt fehl, ruft Camel automatisch die registrierten
Kompensations-Routen in umgekehrter Reihenfolge auf — gleiches Prinzip wie bei Temporal,
aber ohne persistierten State. Faellt der Prozess zwischen zwei Schritten aus,
gibt es keine automatische Wiederherstellung.

**Temporal fuer SAGA nehmen, wenn:**
- Die Saga Stunden oder Tage dauern kann
- Crash-Recovery ohne Datenverlust Pflicht ist
- Komplexe oder verschachtelte Kompensationslogik benoetigt wird
- Observability ueber Web-UI wichtig ist

