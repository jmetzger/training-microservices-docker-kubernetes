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
cd ms-flights
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
