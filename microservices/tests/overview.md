# Tests - Overview 

## Pyramidenkonzept vs. Testing Trophy 

  * Meine Empfehlung: Testing Trophy ist mehr optimiert für Microservices. (Quick Deployment)

<img width="581" height="569" alt="image" src="https://github.com/user-attachments/assets/b68534e2-2608-4ad7-81ca-fc7104a06b86" />

  * Reference: https://kentcdodds.com/blog/the-testing-trophy-and-testing-classifications 

## Vorgehen 

  * Integrationstests stehen imn Vordergrund, dazu zählen auch "Contract Tests"
  * Ein sehr wichtiges Kernelement 

## Static Tests

  * Code braucht dazu nicht ausgeführt zu werden. 
  * siehe [Statische Tests](/microservices/tests/static.md)

### gitlab ci/cd 

  * Kann gut z.B. über den Schritt Linting in die CI/CD Pipeline integriert werden.

## Integration CI/CD - Pipeline 

  * Tests können alle (bis auf End-to-End (E2E) können sehr gut in gitlab ci/cd pipeline integriert werden
  * Empfehlung von E2E - Tests (möglichst nachts, da sie länger dauern können)

## Contract (= gehört in den Bereich der Integration-Tests) 

```  
Schnittstelle wird geprüft, ob sie alle Verträge erfüllt
Gibt sie die definierten Antworten mit den definierten Parametern
The contract specifies all the possible inputs and outputs with their data structures and side effects. 
The consumer and producer of the service must follow the rules stated in the contract for communication to be possible.
```

## E2E (End-to-End-Testing)

### Be Careful - E2E - Testing ! 

```
Aim for a pyramid where a small number of critical user journeys are covered by E2E tests (e.g. 5-10% of total tests), and the rest by faster tests
```

#### Design Tests for Resilience (E2E - Tests)

```
Design Tests for Resilience: Write your E2E test scripts to be resilient to minor delays and asynchronous behavior. This means adding appropriate retries, waits, and timeouts around steps that involve eventual consistency. For instance, if a user action triggers an email service, your test shouldn’t immediately fail if the email isn’t instant – instead poll an API or database until a reasonable timeout. However, avoid simply increasing waits to mask race conditions; whenever you add a wait, understand why it’s needed and whether the system provides a way to know when the state is ready (e.g., a webhook or a status poll). Use idempotency where possible: tests should be able to rerun without side effects. If your E2E tests occasionally hit external APIs or services, build in fallbacks or simulate those interactions to reduce flakiness from outside variability.
```

  * Ref: https://www.bunnyshell.com/blog/end-to-end-testing-for-microservices-a-2025-guide/





