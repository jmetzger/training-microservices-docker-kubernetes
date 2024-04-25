# Design with SEED (Schritt für Schritt) 

## Schritt 1: 

```
Identifizierung der bounded contexts 

o Flights Management
o Reservations Management 

Am Anfang macht es Sinn microservices eher 
grob/weit (coarse-grained) zu gestalten 
in Ihrer Funktion. 
(von allgemein zu spezifischer ist nachher einfach 
als anders herum) 
```

## Schritt 2: Akteure/Konsumenten mit der SEED - Methode identifizieren

  1. Eine Kunden will den Flug buchen 
  1. Die User-App der Airline (web, mobile, usw.)
  1. Die Web API mit der die App interagiert.(dies nenne manche auch "backend for frontends" oder BFF APIs.)
  1. The flights management microservice: ms-flights
  1. Das Reservierungs Management microservice: ms-reservations 

## Schritt 3: Beispiele von JTBD's (Jobs to be Done) 

```
Fiktiv: Gesammelt von Kunden Interviews und Business Analyse Recherche

1. Wenn ein Kunde mit der UI interagiert, muss die app einen Sitzplan anzeigen, welcher die verfügbaren und die belegten Plätze zeigt, so dass der Kunde einen Sitzplatz auswählen kann. 
2. Wenn ein Kunde eine Buchung finalisiert, muss die web app einen Sitz reservieren für den Kunden (so kann die App verhinden, dass es im Verlauf Konflikten bei den 
Sitzen gibt)
```

## Schritt 4: BFF-Api als Ver-Mittler (JTBD)

```
(Backend for frontend) 

Empfehlung: Eine BFF-Api, die nur eine ganze schlanke Schicht hat ohne business
Logik Implementierung. 

Sie "orchestriert" nur die microservices 

Es gibt also jobs für die die BFF API microservices braucht.

Die folgende Liste an Job, die mehr technischen JTBD's beschreiben die Bedürfnisse ziwschen der BFF API und den microservices 

1. Wenn die BFF API angefragt wird einen Sitzplan zur Verfügung zu stellen, braucht die API msflights, um einen Plan der Sitze im Flugzeug zu bekommen, so dass die BFF API Verfügbarkeiten abholen kann und das finale Ergebnis erstellen kann.

2. Wenn die BFF API einen Sitzplan rendern soll, braucht die BFF API ms-reservations um eine Liste von bereits reservierten Sitzen zu bekommen, so dass die BFF API diese Daten dem Sitzplatz-Setup hinzufügen kann und ein Sitzplan zurückgibt. 

3. Wenn die BFF API gefragt wird, einen Sitzplatz zu reservieren, braucht die BFF API ms-reservations um die Reservierung auszuführen, so dass die API einen Sitzplatz reservieren kann

======================================
Achtung:
Wir lassen nicht ms-flights -> ms-reservations
aufrufen, um den Sitzplan zusammenzubauen 

Stattdessen lassen wir die BFF API diese 
interaktion tun. 

Direkt microservices-to-microservices call
sollten vermieden 
werden

============================================
```

## Schritt 5: Project Flight-Service: UML (Erstellen eines Ablaufdiagramms) 

  * https://github.com/jmetzger/training-microservices-docker-kubernetes/blob/main/microservices-flightapp/concept/02-uml.md

```
Folgendes kann man hier klar erkennen:

1. flight id muss ich abfragen, weil Kunden oft nicht direkt die Flugnummer eingeben ;o) 

##############################################
Randnotiz 
##############################################

Zwar müssten auch Tasks für die BFF API definiert werden,
lassen wir aber mal jetzt aussen vor. 
```

## Schritt 6: JBTD in actions und queries überführen 

```
Wir machen das für ms-flights und ms-reservations
```

### A. Flights Microservice 

#### Get flight details 

```
  o Input flight_no,
    departure_local_date_time 
    (ISO8601 format und in der lokalen zeitzone)

  o Response: A unique flight_id identifying a 
    specific flight on a specific date. 
    In der Praxis, wird dieser Endpunkt noch
    mehr relevanten Felder zurückgeben, aber 
    diese sind für unseren Context unrelevant 
    also überspringen wir diese
```

#### Get flight seating (the diagram of seats on a flight)

```
  o Input flight_id
  o Response: Seat Map Object in JSON Format
```

### B. Reservations Microservice 

#### Query already reserved seats on a flight

```
  o Input: flight_id
  o Response: A list of already-taken seat numbers,
              each seat number in a format like "2A" 
```
#### Reserve a seat on a flight 

```
  o Input: flight_id,customer_id,seat_num
  o Expected outcome: A seat is reserved and unavailable
    to others, or an error fired if the seat was unavailable 
  o Response: Success (200 Success) or failure (403 Forbidden) 
```

## Schritt 7: OpenAPI Spezifikation 

  * Wir werden das bei der Umsetzung testen. 
  * [s. UML - Seite:](microservices-flightapp/concept/02-uml.md) 

## Schritt 8: Implementieren 
