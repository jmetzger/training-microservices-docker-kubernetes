# Create flights microservice (use ready repo) with changes already

## Step 1: Use ready project 

  * https://github.com/jmetzger/ms-flights

```
# as unpriviliged user / root
cd
```

```
# either usetemplate
# but we will just clone it locally
git clone https://github.com/jmetzger/ms-flights ms-flights
```

## Step 2: Create documentation 

```
cd
cd ms-flights/docs
```

```
# now render the docs and open 3939 port with container running
# make start
docker run -d --rm --name ms-nb-docs -p 3939:80 -v ${PWD}/api.yml:/usr/share/nginx/html/swagger.yaml -e SPEC_URL=swagger.yaml redocly/redoc:v2.0.0-rc.8-1

docker container ls 
```

```
# in browser of rdp
http://<public-ip-of-server>:3939
```

## Step 3: Build 

```
cd
cd ms-flights
docker compose up -d
docker compose logs 
```

## Step 4: Testing 

```
# Flight 
curl http://<public-ip>:5501/flights?flight_no=AA34\&departure_date_time=2020-05-17T13:20
```

```
# Seat map (gibt nur o.k. zurück, nicht implementiert)
curl http://<public-ip>:5501/flights/AA34/seat_map
```

---

## Fehleranalyse vom 20.05.2026: ms-flights startet nicht (Race Condition mit MySQL)

### Was war das Problem?

Beim Start von `docker compose up` startete die Datenbank (`ms-flights-db`), aber der Service `ms-flights` schmierte sofort ab mit diesem Fehler in den Logs:

```
ms-flights  | running database migrations
ms-flights  | [ERROR] AssertionError: connect ECONNREFUSED 172.20.0.2:3306
```

**Warum passiert das?**

MySQL öffnet den TCP-Port 3306 schon bevor die Datenbank wirklich bereit ist, Anfragen anzunehmen. Das alte `wait-for.sh`-Script prüft nur ob der Port offen ist (per `nc -z`) — nicht ob MySQL wirklich läuft. Deshalb startet `ms-flights` zu früh, die Migration schlägt fehl.

```
ms-flights startet  →  wait-for.sh sieht Port 3306 offen  →  db-migrate läuft
                                                               ↓
                                                         ECONNREFUSED
                                                  (MySQL noch nicht bereit)
```

---

### Vorher (fehlerhaft)

`docker-compose.yml` — ms-flights hatte nur `links`, kein `depends_on`:

```yaml
services:
  ms-flights:
    ...
    links:
      - ms-flights-db
    command: ./shell/wait-for.sh ms-flights-db:3306 -- ./shell/start-dev.sh

  ms-flights-db:
    image: mysql:5.7
    ...
    restart: always
    # kein healthcheck!
```

`wait-for.sh` prüft nur TCP-Port (nicht ob MySQL wirklich Queries annimmt):

```sh
wait_for() {
  for i in `seq $TIMEOUT` ; do
    nc -z "$HOST" "$PORT" > /dev/null 2>&1   # nur TCP-Check!
    result=$?
    if [ $result -eq 0 ] ; then
      exec "$@"   # startet sofort, auch wenn MySQL noch nicht bereit
    fi
    sleep 1
  done
}
```

---

### Nachher (funktioniert)

`docker-compose.yml` — mit `healthcheck` auf der DB und `depends_on` mit Health-Bedingung:

```yaml
services:
  ms-flights:
    ...
    links:
      - ms-flights-db
    depends_on:
      ms-flights-db:
        condition: service_healthy    # wartet bis DB wirklich bereit
    command: ./shell/start-dev.sh    # wait-for.sh nicht mehr nötig

  ms-flights-db:
    image: mysql:5.7
    ...
    restart: always
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost", "-u", "root", "-pverysecretsomething"]
      interval: 5s        # alle 5 Sekunden prüfen
      timeout: 5s
      retries: 10
      start_period: 30s   # erst nach 30s anfangen zu prüfen (MySQL braucht Zeit)
```

**Ablauf jetzt:**

```
ms-flights-db startet  →  alle 5s: mysqladmin ping  →  nach ~30s: "healthy"
                                                          ↓
                                                    ms-flights startet
                                                          ↓
                                               Migrationen laufen durch
                                                          ↓
                                          Server läuft auf Port 5501
```

Logs nach dem Fix:

```
ms-flights-db  | mysqld: ready for connections.
ms-flights-db  | (healthy)
ms-flights     | running database migrations
ms-flights     | [INFO] No migrations to run
ms-flights     | [INFO] Done
ms-flights     | Express server instance listening on port 5501
```
