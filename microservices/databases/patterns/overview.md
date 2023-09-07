# Database Patterns Overview 

## Grundlegendes 

  * Grundlegende Entscheidung, ob
    * Database per Service
    * oder: Shared Database

## Database per Service 

### Prämisse 

  * Jeder Service hat seine eigene Datenbank (Credentials mit Rechten zu seiner Datenbank)
  * Ein anderer Service kann nur über die API zugreifen.

### Vorteile

  * Das sichert die größe Unabhängigkeit
  * D.h. ein unabhängiges Deployment ist problemlos möglich

### Nachteile 

  * Transaktionen funktionieren auf DB-Ebene nicht mehr sinnvll
  * JOINS sind schwierig umzusetzen.

## Reference:

  * https://microservices.io/patterns/data/database-per-service.html

  
## Shared Database 

### Vorteile 

  * Joins sind einfach möglich
  * Transaktionen funktionieren

### Nachteile 

  * Single Point of Failure (ausser natürlich Cluster)
  * Performance Engpässe (kann auch durch gute Optimierung behoben werden)

```
Development time coupling - a developer working on, for example, the OrderService will need to coordinate schema changes with the developers of other services that access the same tables. This coupling and additional coordination will slow down development.
```

### Wie ?

  * Services teilen sich eine Datenbank

### Ref:

  * 



### Ref:

  * https://microservices.io/patterns/data/shared-database.html

  
