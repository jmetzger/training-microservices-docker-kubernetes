# Umgang mit Joins beim Pattern database-per-service 

## 1 Pattern: api composition 

### Nachteile:

  * Bei grossen Datenmengen, kann das sehr speicher und zeitintensiv sein

### Schaubild 

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/09f1d1b4-bcc4-423a-a82c-9b46b291b49e)

### Generell 

  * Oftmals wird dafür ein API-Gateway verwendet 

### Ref:

 * https://microservices.io/patterns/data/api-composition.html

## 2 Pattern: CQRS (Command Query Responsibility Segregation)

### Wie ? 

  * Datenbank erzeugen, die nur eine Leseansicht hat.
  * Synchrnónisierung erfolgt über Subscribition zu Domain Events

### Schaubild 

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/eb670d23-186f-4bf6-8d6e-2d5970a4ca1c)

### Vorteile 

  * Unterstützt mehrere denormalisierte Views, die performant und skalierbar sind
  * Abfragen sind einfache

### Wann ? 

  * Notwendig bei Joins, wenn wir das Event Sourcing - Pattern verwenden 

### Nachteile 

  * Eventuell doppelter Code
  * Lag bei den Views / NUR Eventuell Konsistente Views (keine Sicherheit)

### Ref:

  * https://microservices.io/patterns/data/cqrs.html
