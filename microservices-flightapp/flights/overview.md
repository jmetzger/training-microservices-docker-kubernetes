# Setting up flights microservice

## Step 1: Use bootstrap github template 

  * https://github.com/jmetzger/nodebootstrap-microservice

```
# as unpriviliged user / root
cd
```

```
# either usetemplate
# but we will just clone it locally
git clone https://github.com/jmetzger/nodebootstrap-microservice ms-flights
```

## Step 2: Create documentation 

```
cd ms-flights/doc
```

```
# Replace content in api.yml
# with our own definition from
https://raw.githubusercontent.com/implementing-microservices/ms-flights/master/docs/api.yml
```

```
# now render the docs and open 3939 port with container running
make start
docker container ls 
```

```
# in browser of rdp
http://192.168.56.102:3939
```

