# Testing-Strategie fuer Microservices

## Was testen wir — und wieviel davon?

Empfohlene Verteilung nach der **Testing Trophy** (optimiert fuer Microservices):

![Testing Trophy](images/testing-trophy.svg)

> **Warum so viele Integration Tests?**
> In Microservices stecken die meisten Fehler an den Grenzen zwischen Service und
> Datenbank, Message Broker oder anderem Service — nicht in der isolierten Logik.

---

## Was testen wir wo?

| Testart | Was wird getestet | Werkzeug |
|---|---|---|
| **Static** | Syntaxfehler, Formatierung, bekannte Sicherheitsluecken | ESLint, SpotBugs, Trivy |
| **Unit** | Reine Geschaeftslogik (keine DB, kein Netzwerk) | JUnit, pytest, Jest |
| **Integration** | Service + echte DB / Kafka / Redis | Testcontainers |
| **Contract** | API-Vertrag zwischen Consumer und Provider | Pact, OpenAPI + Dredd |
| **E2E** | Vollstaendige User Journey ueber alle Services | Playwright, Cypress |

---

## Wann im Entwicklungsprozess?

```
Entwickler        Pre-Commit       CI (jeder PR)      Nightly        Release
    |                  |                 |                |              |
    |-- schreibt Code  |                 |                |              |
    |                  |                 |                |              |
    |                  |-- Static -----> |                |              |
    |                  |-- Unit -------> |                |              |
    |                  |                 |                |              |
    |                  |                 |-- Static ----> |              |
    |                  |                 |-- Unit ------> |              |
    |                  |                 |-- Integration->|              |
    |                  |                 |-- Contract --> |              |
    |                  |                 |                |              |
    |                  |                 |                |-- E2E -----> |
    |                  |                 |                |              |
    |                  |                 |                |              |-- Smoke Test
```

| Phase | Tests | Dauer | Ziel |
|---|---|---|---|
| **Pre-Commit** (lokal) | Static, Unit | < 1 Min. | Sofortfeedback, kein kaputten Code committen |
| **CI — jeder PR** | Static, Unit, Integration, Contract | 5-15 Min. | Kein defekter PR in main |
| **Nightly** | E2E | 30-60 Min. | Vollstaendige Systemvalidierung |
| **Vor Release** | Alle + manueller Smoke Test | — | Letztes Sicherheitsnetz |

---

## Warum E2E nicht bei jedem PR?

E2E-Tests sind langsam, flakey (timing-abhaengig) und teuer.
Ziel: **5-10 kritische User Journeys**, nicht jedes Feature.

```
Falsch: 500 E2E-Tests, laufen 2h, schlagen oft zufaellig fehl
Richtig: 10 E2E-Tests fuer die wichtigsten Pfade (Checkout, Login, Zahlung)
```

---

## Praktische Faustregeln

**Unit Test schreiben wenn:**
- Die Logik komplex ist (Berechnungen, Entscheidungen, Transformationen)
- Keine externe Abhaengigkeit benoetigt wird

**Integration Test schreiben wenn:**
- Code mit einer Datenbank, Kafka oder einem anderen Service interagiert
- SQL-Abfragen, ORM-Mappings oder Transaktionen getestet werden sollen

**Contract Test schreiben wenn:**
- Zwei Services miteinander kommunizieren
- Ein Service eine API eines anderen Services aufruft

**E2E Test schreiben wenn:**
- Ein kritischer Geschaeftsprozess end-to-end abgesichert werden soll
- Mehrere Services zusammenspielen muessen

---

## Anti-Patterns

| Anti-Pattern | Problem | Besser |
|---|---|---|
| Nur Unit Tests | Findet keine Integrationsfehler (Mock weicht von Realitaet ab) | Integration Tests mit Testcontainers |
| Zu viele E2E Tests | Langsam, flakey, schwer zu debuggen | Auf wenige kritische Journeys reduzieren |
| Kein Contract Test | Aenderung am Provider bricht Consumer unbemerkt | Pact zwischen allen Services |
| Geteilte Test-DB | Tests beeinflussen sich gegenseitig | Testcontainers — jeder Test eigene DB |
| Mocks fuer alles | Tests bestehen, Produktion bricht | Echte Abhaengigkeiten via Testcontainers |
