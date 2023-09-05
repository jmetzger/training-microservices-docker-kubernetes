# Projekt Flightapp (Flights (Readonly) and Seat Reservations (ReadWrite) 

## Part 1: SEAT RESERVATIONS

### Block 1: The OpenAPI - Definition 

#### Getting the OpenAPI - Definition we ;o) created

```
# Get if from:
https://raw.githubusercontent.com/jmetzger/ms-reservations/master/docs/api.yml
```

#### Use it to render a beautiful out in swagger, and it is also the docs 

  * https://editor.swagger.io/

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/792bfdd7-4ddb-4f85-84e9-e3732fbc4333)


### Block 2: Clone ms-reservations and build it. 

```
cd
git clone https://github.com/jmetzger/ms-reservations.git
cd ms-reservations
sudo apt install -y make
make
```

```
# it then shows the logs
CTRL + C
# app will still be running as it is daemonized (see start in Makefile)
```

### Block 3: Open Client on redis-server to test 

#### Start redis-cli within redis-server 

```
make redis
```

#### These are direct calls to redis trough the redis cli

```
echo "in redis client - enter our first seat reservation"
```

```
HSETNX flight:40d1-898d-bf84a266f1b9 12B b4cdf96e-a24a-a09a-87fb1c47567c
```

```
# this means success -> (integer) 1
```

```
HSETNX flight:40d1-898d-bf84a266f1b9 12C e0392920-a24a-b6e3-8b4ebcbe7d5c
```

#### Now retrieve the occupied seats (in redis cli) 

```
HKEYS flight:40d1-898d-bf84a266f1b9
```

#### This is the output of the reserved seats (keys) 

```
1) "12B"
2) "12C"
```

#### Like so you can also! get the passenger-id of a seat (so including both)

```
HGETALL flight:40d1-898d-bf84a266f1b9
```

#### That is the output 

```
1) "12B"
2) "b4cdf96e-a24a-a09a-87fb1c47567c"
3) "12C"
4) "b4cdf96e-a24a-a09a-87fb1c47567c"
```

#### Let's try double-booking (of seat)

```
HSETNX flight:40d1-898d-bf84a266f1b9 12B b4cdf96e-a24a-a09a-87fb1c47567c
```

#### This is how the result looks like 

```
# this means success error -> (integer) 0
```

### Block 4: Test microservice with rest-api call 

```
# only works when project name is: msupandrunning
make ps 
```

```
curl --verbose --header "Content-Type: application/json" --request PUT --data '{"seat_num":"12C","flight_id":"werty", "customer_id": "dfgh"}' http://192.168.56.102:7701/reservations
```

```
curl --verbose --header "Content-Type: application/json" --request PUT --data '{"seat_num":"12D","flight_id":"werty", "customer_id": "dfgh"}' http://192.168.56.102:7701/reservations
```

```
# Try once again
# --verbose also shows the headers 
curl --verbose --header "Content-Type: application/json" --request PUT --data '{"seat_num":"12D","flight_id":"werty", "customer_id": "dfgh"}' http://192.168.56.102:7701/reservations
```

