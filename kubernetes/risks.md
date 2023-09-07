# Kubernetes Risiken bei Einsatz (Microservices)

## Kubernetes 

### Team / Prozesse 

  * Fehlendes Knowledge im Team für Kubernetes
    * Implikation -> Single Point of Failure
    * Berührungsängste / Nicht-Handlung bzgl. Kubernetes  
  * Keinen klaren Prozess Updates der Infrastruktur
    * Implikation; Veraltete Versionsstände
    * Implikation: Angreifbarkeit durch Sicherheitslücken  
  * keinen klaren Prozess auf Updates von Applikation (Prozess Anpassung der Manifeste)
    * Implikaton: manifeste funktionieren nicht mehr oder unerwartet auf neueren Version
    * Implikation: bestimmte Feature sind nicht mehr verfügbar (PSP - Pod Security Policies (deprecated), PSA (Pod Security Admission) 

### Pods 

  * Pods (Container) als Root laufen lassen (Angriffsrisiko) 
    * Implikation: Wenn jemand den Pod hacked, kann er u.U. Rechte auf der Host-Maschinen bekommen.
   
### Images / Sicherheit 

  * Images verwenden alte Versionen von Software und nicht überprüft, gescannt. 

### Backup / Monitoring / Observeability 

   * Kubernetes Aware Backups-System (kasten.io) wird nicht verwendet 
     * Implikation: Das falsche wird gesichert.
     * Implikation: Hohe Komplexität beim Zurückspielen (falsche Volume wird zurückgesichert, unklar welches)  

   * Richtige Backupstrategie (manifeste, Grundeinrichtung Server, Konfigurationsdateien (speziell für Cluster-infrastruktur - besser Infrastructure by Code)  

### Performance

   * Pods falsch konfiguriert sind.
     * Implikation: die falschen Pods werden verschoben.
    

## Microservices 

### Fehlende Tools zur Analyse 

  * Entscheidende Tools für Monitoring, Tracing, Observeability nicht oder falsch eingerichtet.
  * das aller wichtigste Tracing (Jaeger) und pro Anfrage einen durchgängigen Key
  * Log Aggregation (ELK / EFK) -> Um aus der Vogelperspektive Problem erfassen und trigger zu setzen
    * Logs vom Server, Logs vom Kubernetes Cluster, Logs von den Pods -> zentral zusammenläuft
   
  * Implikation: Blindflug nach Ausrollen von MicroServices

### Wir machen mal microservices weil es cool ist 

   * Wichtig: Warum nehme ich microservices
   * Warum kein Monolith
   * Microservices: Kaufpreis für Effekt

   * Implikation: Dat Ding fliegt mir um die Ohren
     * Team: Zu aufwändig.
     * Team: fehlendes Knowlegdge.
     * Team: Falsche Einschätzung der Performance
     * Team: Grobe Konzeptionsfehler

### Zu schnell zu viel wollen 

   * Alles von Anfang bis Ende durchkonzipieren
   * Zuviele Microservices werden zu schnell online genommen
     * UND: die alte Funktionalität im Monolithen zu schnell abgeschaltet.
    
   * BESSER: Kleine Schritte, Erfahrungen sammeln, MESSEN ! 



