# Projekt Flightapp (Flights (Readonly) and Seat Reservations (ReadWrite) 


## Block 2: Clone ms-reservations and build it. 

```
cd
git clone https://github.com/jmetzger/ms-reservations.git
cd ms-reservations
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
