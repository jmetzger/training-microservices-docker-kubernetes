# Database patterns (Teil 2) 

## Was sollte ich zuerst aufteilen: Code oder Datenbank, oder gleichzeitig.

 * Datenbank nur zuerst, wenn ich Sorge wg. der Performance habe.
 * Code in der Regeln zuerst (Machen die meisten Teams so) 
   * Vorteil: Ich weiss dann welche Daten der Service braucht 

 * Gleichzeitig auf keinen Fall 

## Anwendungsfall 1: Zuerst die Daten aufteilen 


### Pattern: Repository per Bounded Context

  * Im Code habe ich pro Bounded Context ein Repository - Layer, der
    auf die Datenbank zugreift 

### Pattern: Database per Bounded Context 

  * Vorsichtsmaßnahme, falls ich später ein Service draus machen will

## Anwendungsfall 2: Zuerst den Code aufteilen 

### Pattern: Monolith as Data Access Layer 

  * [Monolith Data Access Layer - Schritt 1](https://photos.app.goo.gl/XhBKuGRAFaubN99CA)
  * [Monolith Data Access Layer - Schritt 2](https://photos.app.goo.gl/k3vg1BsoRonHi7pe9)

### Pattern: Multischema Storage 

  * Service speichert Teile der Daten selbst 
    * und Teile der Daten kommen aus dem Monolithen 



### Pattern: Split Table 

  * [Split Table - Teil 1](https://photos.app.goo.gl/DJ9oXXWwJg62qb199)
  * [Split Table - Teil 2](https://photos.app.goo.gl/T3yDBMCh5xr9NMKB8)
  * [Split Table - Teil 3](https://photos.app.goo.gl/ynrXyq4gD18HHAg66)

#### Grund

```
Tabellen die über Service - Grenzen hinweg existieren aufteilen
```

### Pattern: Move Foreign Key Relationship To Code 

  * [Schritt 1](https://photos.app.goo.gl/zgSYAeg6Qq5vWnYx7)
  * [Schritt 2](https://photos.app.goo.gl/cciUQwy71HZXdHB67)


## Anwendungsfall 3: Gemeinsame genutzte statische Daten

### Pattern: Duplicate Static Reference Data 

  * Jeder Service hat seine eigenen Ländercodes 
  * Zwar redundant, aber ändern sich fast nie.
  * Und ist es ein Problem, wenn sie sich ändern ? 
  * Letzte Änderung 2011 (bei Länder-Codes)

### Pattern: Static Dedicated Reference Data Schema 

  * Dediziertes Schema für Ländercodes in eigener Datenbank 
  * Alle Services greifen auf diese Datenbank direkt zu 
  * Es gibt dort einen Vertrag (Änderungen an der Struktur sind kritisch) 

#### Nachteil 

  * Probleme beim Ändern des Encodings in der Datenbank (Migration von alter auf neue Datenbank) 

### Pattern: Static Reference Data Library 

  * z.B. Ländercodes wandern von der Datenbank in eine Bibliothek 
    * die dann einfach eingebunden wird. 

#### Nachteil 
 
  * Schwierig, wenn verschiedene Programmier-Sprachen
 
### Pattern: Static Reference Data Service 

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
