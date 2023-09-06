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
https://raw.githubusercontent.com/jmetzger/ms-flights/master/docs/api.yml
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

## Step 3: Cleanup and restructure 

### Step 3.1 Delete and rename unneeded files 

```
# we will use the users module for something else 
cd
cd ms-flights
mv lib/users lib/flights
```

```
# / -> homedoc is no needed, so we delete it
rm -fR lib/homedoc
```

### Step 3.2 Adjust appConfig.js to change routings 

```
nano appConfig.js 
```

```
# 1. delete this line from appConfig.js
# app.use('/',      require('homedoc')); // attach to root route

# 2. change line
# app.use('/users', require('users')); // attach to sub-route
# to ->
# app.use('/flights', require('flights')); // attach to sub-route
```

### Step 3.3 Edit lib/flights/controllers/mappings.js for input validation and which functions to call

```
nano lib/flights/controllers/mappings.js
```

```
#the new version will look like this
https://raw.githubusercontent.com/jmetzger/ms-flights/master/lib/flights/controllers/mappings.js
```

### Step 3.4 Integrate MySQL - data 

```
# create migrations scripts (implemented in bootstrap)
make migration-create name=seat-maps
