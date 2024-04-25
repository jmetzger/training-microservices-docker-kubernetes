# Sollte ich Datenbanken innerhalb oder ausserhalb von Kubernetes betreiben

## Aspekt: Debugging (Expertise im Team) 

  * Kann kann ein Killer-Kriterium sein, weil ich jemand brauche,
    * der sowohl die DB beherrscht als auch Kubernetes
  * Wenn ich keinen solchen habe, sollte ich es NICHT in Kubernetes betreiben 
   
## Performance - Optimierung / Debugging 

  * Memory is key (Je mehr Arbeitsspeicher desto besser)
  * Funktionieren am besten, wenn alle häufig verwendeten Daten in den Arbeitsspeicher passen

## Nachteil Kubernetes:

  * Erhöhte Komplexität (wo muss ich hinlangen)
  * Manche Datenbanksystemen kommen nicht gut damit zurecht, wenn pods häufig mit der Datenbank
    * neu erstellt werden
   
 ## Wenn Kubernetes:

   * Gibt es einene Operator für diesen Datenbank

## Folgende Datenbanken gehen garnicht als Installation innerhalb des Kubernetes Clustter

  * Oracle

## Folgende Datenbanken sind nicht so gut geeignet eine Installation innerhalb des Kubernetes Cluster

  * mariadb und postgresql

## Referenz:

  * https://cloud.google.com/blog/products/databases/to-run-or-not-to-run-a-database-on-kubernetes-what-to-consider?hl=en
  * https://operatorhub.io/?keyword=mariadb

