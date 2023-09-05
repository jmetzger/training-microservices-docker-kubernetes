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
