# Database Patterns (Part 1)  



## Pattern Shared Database

  * Shared Database:Informations-Hiding ist schwierig 
  * Achtung: nur in 2 Situationen vernünftig
    1. Lesen statischer Referenzdaten (Postleitzahlen, Geschlecht, Bundesländer)
    2. Anbieten eines Service, der direkt eine Datenbank als definierten Endpunkt bereitstellt
       * Database as-a-Service-Interface Pattern 

### Wie häufig 
  
  * Eher selten ?
  

## Pattern: Database View 

  * Die Daten werden nicht als Tabelle, sondern als View bereitgestellt
  * Datenbank (View) ist dann aber ein öffentlicher Vertrag

### Wo ? 

  * Kann man dann machen, wenn man das monolithische Schema nicht auseinander nehmen kann
  * Achtung: performance views mysql
  * Wenn der Aufwand für die Aufteilung zu gross ist, kann das der 1. Schritt in die richtige Richtung sein


## Pattern: Database-as-a-Service Interface

  * Manchmal müssen Clients eine Datenbank nur abfragen
  * z.B. eine dedizierte Datenbank, als Read-Only-Endpunkt 
    * gefüllt wird diese wenn sich Daten in der zugrundeliegenden Datenbank ändern

  * Wir sollten die Datenbank, die wird nach draussen anbieten, von der Datenbank 
    * getrennt halten, die wir innerhalb unserer Service-Grenzen einsetzen

### Wie ?

  * Umsetzung durch eine Mapping - Engine.

### Wann ? 

   * Wenn legacy-client lesenden Zugriff benötigen 

## Pattern: Database Wrapping Service 

   * Eine Datenbank mit einem Service wrappen.
   * Damit kann man auch sicherstellen, dass sich die Datenbank nicht verändert.
   * Zugriffe müssen jetzt aber geändert werden, von direkt auf die service api

### Wann ? 

   * API davor setzen, um Veränderung der Datenbank zu hindern. 
   * Einschränken, was man machen darf. 

## Pattern: Aggregate Exposing Monolith 

  * Daten werden über einen Serviceendpunkt vom Monolithen selbst bereitgestellt
    * API oder ein Stream mit Events
  * Dadurch wird explizit, welche Informationen der neue Service benötigt

## Pattern: Change Data Ownership 

  * Der neue Dienst übernimmt die Ownership für die Daten 

## Pattern Synchronize Data in Application 

### Schritt 1: Daten Bulk - synchronisieren
  
  * z.B. durch Batch-Job 
  * dann z.B. Change-Data-Caputre Prozess 

### Schritt 2: Synchrones Schreiben, aus dem alten Schema lesen 

  * Erfolgt durch Deployment einer neuen Version der Anwendung

### Schritt 3: Synchrones Schreiben, aus dem neuen Schema lesen 

  * Erfolgt wieder durch Deployment einer neuen Version der Anwendung 

### Schritt 4: Alte Schema entfernen

  * Altes Schema kann jetzt gefahrlos entfernt werden


## Pattern Tracer Write 

  * Inkrementelle Verschiebung der Source of Truth. 
  * D.h. nicht komplette Datenbank, sondern einzelne Tabellen
