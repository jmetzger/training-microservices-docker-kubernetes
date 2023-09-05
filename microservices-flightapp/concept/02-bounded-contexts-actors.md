# Bounded Contexts  and actors

## Identify bounded contexts 

```
# In this case we have 2 bounded contexts.
Flights management
(seats Reservation management)
```

## What are the actors ?

 1. customer trying to book the flight
 1. the airline's consumer app (web,mobile,etc.)
 1. web api that the app interacts with (BFF apis) - Backends for Frontends
 1. flights managemnt microservice: ms-reservations
 1. reservation management microservice: ms-reservations 
