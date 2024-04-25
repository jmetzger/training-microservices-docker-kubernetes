# Ablaufdiagram in UML 


## Schritt 1: UML 
```
@startuml

actor Customer as cust
participant "Web App" as app
participant "BFF API" as api
participant "ms-flights" as msf
participant "ms-reservations" as msr

cust -[#blue]-> app ++: "Flight Seats Page"
app -[#blue]-> api ++ : flight.getSeatingSituation()
api -[#blue]-> api: auth
api -> msf ++ : getFlightId()
msf --> api: flight_id
api -> msf: getFlightSeating()
return []flightSeating
api -> msr ++ : getReservedSeats()
return []reservedSeats
return []SeatingSituation
return "Seats Selection Page"
|||
cust -[#blue]->app ++: "Choose a seat & checkout"
app-[#blue]->app: "checkout workflow"
app-[#blue]->api ++: "book the seat"
api -[#blue]->api: auth
api->msr ++: "reserveSeat()"
return "success"
return "success"
return "Success Page"
@enduml
```

## Schritt 2: Online Rendern 

  * https://plantuml.com/de/
  * Ganz unten auf der Seite Daten in das "Textarea" - Feld pasten

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/245f026e-fd4f-4ec3-8d08-a55bf6bb91e5)


## Alternative mermaid 

  * https://mermaid.live/edit#pako:eNqNk8tqwzAQRX9l0KK0pO4HeBFIUwJZFEKz6CIORZHHtkhsqXo0lJB_70i2iZN4UW9kdM-8rpgTEypHljKL3x4bgW-Sl4bXWQP0ceGUAeGtA25hTqeq0bSa5sZJITVvSNQ6AJ-4g5nWY7oM-utiAbPV8l6vbRH02ibFQZaVs2OI6RCDFs0Pd1I1xLVkaDGZTifUSAqLmAPWyJ2FFS-xG0brFpEptGVeSnSBkk25ls7HlI9PPS2JjjD3rhpeTqjdFCi2LbTM-xi6T0hPBiW-ZD4MvY7sat-Ex-jNthgiN-VNTPIRjcA8DnpJYi49bLZmyFyytIQOxK0BF7MCFIxNOyvXeEARkM7UEevnlVIWgYOlCHgAUaHYK--GLxDBXoCjMvvioI73b7RTag-uwpjrP49CrnTjhn5HDbFeCLRjRtwoV9O3Ujc1e2a0AzWXOW3NKfAZoy5rzFhKvzkW3B9cxrLmTCi1qda_jWCpMx6fmdc5d_2S9ZeYS9qz93YR4z6e_wBczjCM

### UML abweichend bei mermaid 

```
sequenceDiagram
    actor cust as Customer
    participant app as Web App
    participant api as BFF API
    participant msf as ms-flights
    participant msr as ms-reservations

    cust->>+app: Flight Seats Page
    app->>+api: flight.getSeatingSituation()
    api->>api: auth
    api->>+msf: getFlightId()
    msf-->>-api: flight_id
    api->>msf: getFlightSeating()
    msf-->>api: []flightSeating
    api->>+msr: getReservedSeats()
    msr-->>-api: []reservedSeats
    api-->>-app: []SeatingSituation
    app-->>-cust: Seats Selection Page

    cust->>+app: Choose a seat & checkout
    app->>app: checkout workflow
    app->>+api: book the seat
    api->>api: auth
    api->>+msr: reserveSeat()
    msr-->>-api: success
    api-->>-app: success
    app-->>-cust: Success Page

```
