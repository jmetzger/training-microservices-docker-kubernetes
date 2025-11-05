# Tests - Overview 

## Pyramidenkonzept

  * s. Referenz
 
## Testkategorien 

```
Klassisch (automatisiertes Testen):
   Unit tests
   Integration tests
   End-to-end tests.
```

```   
 
Bei microservices kommen noch 2 tests dazu: 
  Components Tests
  Contract Tests

so dass es dann so aussieht:
         
                      End-To-End Tests
                  Components Tests 
              Integration Tests 
           Contract Tests 
         Unit Tests 

```

## Contract 

```  
Schnittstelle wird geprüft, ob sie alle Verträge erfüllt
Gibt sie die definierten Antworten mit den definierten Parametern
The contract specifies all the possible inputs and outputs with their data structures and side effects. 
The consumer and producer of the service must follow the rules stated in the contract for communication to be possible.
```

### Components 
 
```
Components Test:
A component is a microservice or set of microservices that accomplishes a role within the larger system.
Component testing is a type of acceptance testing in which we examine the component’s behavior in isolation by substituting services with simulated resources or mocking.
```

### Be Careful - E2E - Testing ! 

```
Aim for a pyramid where a small number of critical user journeys are covered by E2E tests (e.g. 5-10% of total tests), and the rest by faster tests
```

#### Design Tests for Resilience 

```
Design Tests for Resilience: Write your E2E test scripts to be resilient to minor delays and asynchronous behavior. This means adding appropriate retries, waits, and timeouts around steps that involve eventual consistency. For instance, if a user action triggers an email service, your test shouldn’t immediately fail if the email isn’t instant – instead poll an API or database until a reasonable timeout. However, avoid simply increasing waits to mask race conditions; whenever you add a wait, understand why it’s needed and whether the system provides a way to know when the state is ready (e.g., a webhook or a status poll). Use idempotency where possible: tests should be able to rerun without side effects. If your E2E tests occasionally hit external APIs or services, build in fallbacks or simulate those interactions to reduce flakiness from outside variability.
```

  * Ref: https://www.bunnyshell.com/blog/end-to-end-testing-for-microservices-a-2025-guide/

### References 

 * https://semaphoreci.com/blog/test-microservices



