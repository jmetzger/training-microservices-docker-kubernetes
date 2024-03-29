# Database Patterns Overview 

## Grundlegendes 

  * Grundlegende Entscheidung, ob
    * Database per Service
    * oder: Shared Database
  * Kann auch für einzelne Services unterschiedlich ausfallen

## Database per Service 

### Prämisse 


  * Ein anderer Service kann nur über die API zugreifen.
  * Synchronisierung kann auch über andere Weg als synchron erfolgen (z.B. Messaging -> Saga) 

#### Umsetzung: 

  * Private-tables-per-service – each service owns a set of tables that must only be accessed by that service
  * Schema-per-service – each service has a database schema that’s private to that service
  * Database-server-per-service – each service has it’s own database server.

### Vorteile

  * Das sichert die größe Unabhängigkeit
  * D.h. ein unabhängiges Deployment ist problemlos möglich

### Nachteile 

  * Transaktionen funktionieren auf DB-Ebene nicht mehr.
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

  * https://microservices.io/patterns/data/shared-database.html

  
