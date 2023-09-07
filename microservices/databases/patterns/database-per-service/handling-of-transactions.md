# Handling of transactions 

## Problem 

  * When we move to a database per service pattern, we cannot use transactions

## Example Problem 

  * You are using database-per-service-pattern

```
x e-commerce store
x customers have a credit limit.
x The application must ensure that a new order will not exceed the customer’s credit limit.
x Orders and Customers are in different databases owned by different services
x because of this: application cannot simply use a local ACID transaction.
```

## Schaubild (Wie funktioniert es ?) 

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/f4615f49-5937-476e-bff7-d32e7de870c9)

## Saga Execution Coordinator (SEC) as central component 

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/a33eb0a1-0e04-48a7-983c-9b6741202afe)

  * contains a Saga log that captures the sequence of events of a distributed transaction.
  * ON FAILURE:  the SEC component inspects the Saga log to identify the impacted components and the sequence in which the compensating transactions should run.
  * It can then identify transactions successfully rolled back, which ones are pending, and can take appropriate actions

## Implementation as Saga Choreography Pattern

### When ?

  * Greenfield (starting from scratch) microservices development 


## Implementation as Saga Orchestration Pattern 

### When ?

  * Brownfield (we already have a set of microservices
  * 


## Reference:

  * https://www.baeldung.com/cs/saga-pattern-microservices#introduction-to-saga
  * https://microservices.io/patterns/data/saga.html
