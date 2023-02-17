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

### References 

 * https://semaphoreci.com/blog/test-microservices



