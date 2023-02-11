# Event Bus Übersicht 

## Fertige Software, die einen Event Bus bereitsstellt

  * Kafka 
  * RabbitMQ
  * NATS 

## Was ist Ihre Aufgabe ? 

  * Events empfangen 
  * Events veröffentlichen (publish) für die Zuhörer (listener)
 

## Wie sehen Events aus ? 

  * Mit Events meinen wir Informations-Snippets 
    * Es ist nicht festgelegt, wie eine Event aussehen soll, es kann
      * Rohe Datenbytes
      * JSON
      * ein String
      * u.a. ... sein (was immer du verwenden willst)

## Was sind Listener ?

  * Listener sind Services, die von anderen Events von anderen Services erfahren wollen 
