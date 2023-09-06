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

#### Step 3.4.1 Delete old stuff in migrations 

```
rm -fR migrations/* 
```

#### Step 3.4.2 Create files for upgrading/downgrading

```
# create migrations scripts (implemented in bootstrap)
make migration-create name=seat-maps
make migration-create name=flights
make migration-create name=sample-data
```

```
# if you are working as unprivileged user change permissions accordingly
# They are root after this process
sudo chown kurs:kurs ms-flights/migrations/sqls/*sql
```

#### Step 3.4.3 Populate files 

```
nano migrations/sqls/[date]-seat-maps-up.sql
```

```
# migrations/sqls/[date]-seat-maps-up.sql with data of
https://raw.githubusercontent.com/jmetzger/ms-flights/master/migrations/sqls/20200602055112-seat-maps-up.sql
````


```
nano migrations/sql/[date]-flights-up.sql 
```

```
# migrations/sqls/[date]-seat-maps-up.sql with data of
https://github.com/jmetzger/ms-flights/blob/master/migrations/sqls/20200602055121-flights-up.sql
```

```
nano migrations/sqls/[date]-sample-data-up.sql
```

```
# migrations/sqls/[date]-sample-data-up.sql with data of
https://github.com/jmetzger/ms-flights/blob/master/migrations/sqls/20200602055127-sample-data-up.sql
```


#### Step 3.4.4 Do the migration 

```
# Doing make restart instead of make migrate, because new data needs to be in docker container
make restart
```

#### Step 3.5 Renaming from ms-nodebootstrap-example to ms-flights 

```
cd ms-flights
make stop
# Change all entries that appear here
grep -r ms-nodebootstrap-example .
```

```
# when adjusted all entries doublecheck
grep -r ms-nodebootstrap-example
make restart
```
