# Database patterns 





## Gemeinsame genutzte statische Daten



### Pattern: Duplicate Static Reference Data 

  * Jeder Service hat seine eigenen Ländercodes 
  * Zwar redundant, aber ändern sich fast nie.
  * Und ist es ein Problem, wenn sie sich ändern ? 
  * Letzte Änderung 2011 (bei Länder-Codes)

### Static Dedicated Reference Data Schema 

  * Dediziertes Schema für Ländercodes in eigener Datenbank 
  * Alle Services greifen auf diese Datenbank direkt zu 
  * Es gibt dort einen Vertrag (Änderungen sind kritisch) 

### Static Reference Data Library 

  * z.B. Ländercodes wandern von der Datenbank in eine Bibliothek 
    * die dann einfach eingebunden wird. 

#### Nachteil 
 
  * Schwierig, wenn verschiedene Sprachen
 
### Static Reference Data Service 

  * z.B. dedizierter Service für Ländercodes 

#### Aufbau 

```
    Service        Service
   Warehouse       Finance 
       \              /
        \            /
            Service
         Country Code 

```
