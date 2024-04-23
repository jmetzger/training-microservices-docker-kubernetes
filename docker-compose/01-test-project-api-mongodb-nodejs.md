# Kleines Testporjekt mit docker-compose nodejs und mongodb 

## Was macht es ?

  * Das Projekt wird direkt gebaut
  * Es startet eine mongodb.
  * Daten werden Über api calls geschrieben/gelesen gelöscht

## Wie verwende ich es ?

### Schritt 1: Aufsetzen 

```bash 
# Auf dem Docker - server direkt klonen und starten
cd
mkdir -p projects
cd projects
```

```bash 
git clone https://github.com/jmetzger/multiple-containers-in-docker.md mcid
cd mcid
docker compose up -d 
```

### Schritt 2: [Optional] Datenbankverbindung aufbauen 

```
# Z.B. in visual studio code
# Extensions MongoDB installieren 
```


### Schritt 3: Rest API-Calls absetzen

   * Diese finden sich bspw. hier: https://github.com/jmetzger/multiple-containers-in-docker/blob/main/rest.http

```
# Hierzu kann bspw. der REST-Client in Visual Studio Code verwendet werden 
```
