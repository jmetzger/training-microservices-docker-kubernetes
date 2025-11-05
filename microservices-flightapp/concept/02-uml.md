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

  * https://www.plantuml.com/plantuml/uml/SyfFKj2rKt3CoKnELR1Io4ZDoSa70000 (Achtung: Session-Problem ? Sonst über Startseite gehen https://plantuml.com/de/ und Online-Server - Menüpunkt


## Alternative mermaid 

  * [Mermaid Live Edit](https://mermaid.live/edit)

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
