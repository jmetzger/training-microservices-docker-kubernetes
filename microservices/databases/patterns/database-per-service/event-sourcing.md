# Event Sourcing 

## Nachteile:

  * Um Daten zu bekommen, müssen die verschiedenen Events "geparsed" werden und daraus ein Ergebnis geschrieben werden
  * Das ist zeitraubend -> als Ergänzung kann CQRS (Command Query Responsibility Segregation) verwendet werden (Es werden dann finale Daten in ein VIEW basierte Datenbank geschrieben)
    * Hier ist allerdings wieder das Thema der Eventuell konsistenten Daten (Damit müssen die Applikationen umgehen)
 
## Schaubild 

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/b6679016-962e-48c1-a3c7-7d0cae9cb67f)

## Refs: 

  * https://microservices.io/patterns/data/event-sourcing.html
