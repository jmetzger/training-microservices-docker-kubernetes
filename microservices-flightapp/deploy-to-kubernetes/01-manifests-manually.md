# Project flight-app  

## Schritt 1: configmap - flights

```
cd 
mkdir -p manifests
cd manifests
mkdir flight-app
cd flight-app
mkdir flights
cd flights 
nano 01-secret.yml 
```

```
# you could also create a secret with
#  kubectl create secret generic mariadb-secret --from-literal=MARIADB_ROOT_PASSWORD=11abc432 --dry-run=client -o yaml > 01-secrets.yml
```

```
## 01-secrets.yml
kind: Secret 
apiVersion: v1 
metadata:
  name: mariadb-secret
data:
  # als Wertepaare
  MARIADB_ROOT_PASSWORD: MTFhYmM0MzI=
```

```
cd
cd manifests/flight-app
# alle Unterverzeichnisse recursiv ausführen 
kubectl apply -Rf .
kubectl get secrets
kubectl get secrets mariadb-secret -o yaml
```

## Schritt 2: PersistentVolumeClaim 

```
cd
cd manifests/flight-app
cd flights
nano 02-pvc.yml 
```

```
# vi 02-pvc.yml
# now we want to claim space
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: pvc-do
spec:
  storageClassName: do-block-storage
  accessModes:
  - ReadWriteOnce
  resources:
     requests:
       storage: 1Gi
```

```
cd
cd manifests
cd flight-app
kubectl apply -Rf .  
```

  * Ref: https://docs.digitalocean.com/products/kubernetes/how-to/add-volumes/



## Schritt 3: mariadb Deployment 

```
cd
cd manifests/flight-app/flights 
nano 03-deploy-mariadb.yml
```

```
#deploy.yml 
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mariadb-deployment
spec:
  selector:
    matchLabels:
      app: mariadb
  replicas: 1 
  template:
    metadata:
      labels:
        app: mariadb
    spec:
      containers:
      - name: mariadb-cont
        image: mariadb:latest

        volumeMounts:
        -  mountPath: "/var/lib/mysql"
           name: do-volume

        envFrom:
        - secretRef:
            name: mariadb-secret
        
      volumes:
      - name: do-volume
        persistentVolumeClaim:
          claimName: pvc-do
```

```
cd
cd manifests/flight-app/
kubectl apply -Rf .
```

## Schritt 3.1 Service für MariaDB anlegen 

```
cd
cd manifests/flight-app/flights
nano 04-service-mariadb.yml
```


```
apiVersion: v1
kind: Service
metadata:
  name: ms-flights-db
spec:
  type: ClusterIP
  ports:
  - port: 3306
    protocol: TCP
  selector:
    app: mariadb
```

```
cd
cd manifests/flight-app/
kubectl apply -Rf .
```

## Schritt 3.2: Add flights deployment 

```
cd
cd manifests/flight-app/flights
nano 03-deploy-flights.yml
```

```
#deploy.yml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: flights-deployment
spec:
  selector:
    matchLabels:
      app: flights
  replicas: 1
  template:
    metadata:
      labels:
        app: flights
    spec:
      containers:
      - name: app
        image: dockertrainereu/flights-jm:v11
        command: [ "/bin/sh", "-c", "--" ]
        args: [ "while true; do sleep 30; done;" ]
          #volumeMounts:
          #-  mountPath: "/var/lib/mysql"
          # name: do-volume

        env:
        - name: NODE_ENV
          value: dev
              #- name: NODE_HOT_RELOAD
              #value: "1"
              #- name: NODE_LOGGER_GRANULARLEVELS
              #value: "1"
        - name: NODE_CONFIG_DISABLE_FILE_WATCH
          value: "Y"

              #volumes:
              #- name: do-volume
              #persistentVolumeClaim:
              #claimName: pvc-do


```

```
cd
cd manifests/flight-app/
kubectl apply -Rf .
```


## Schritt 4: Lokal kompose installieren 

  * als root

[Kompose installieren](tools/kompose.md)

```
# alle weiteren Schritte als kurs 
su - kurs 
```

## Schritt 5: ms-reservations clonen (zur Hilfe bzgl. der manifests)

```
cd 
git clone https://github.com/jmetzger/ms-reservations
cd ms-reservations

# create a dummy folder
mkdir dummy
cp -a docker-compose.yml dummy
cp -a database-dev.env dummy
cd dummy
kompose --file=docker-compose.yml convert
```

## Schritt 6: config für redis anlegen 

```
cd
mkdir -p  manifests/flight-app/reservations
cd manifests/flight-app/reservations
nano 01-redis-cm.yml
```

```
apiVersion: v1
kind: ConfigMap
metadata:
  name: redis-cm
data:

  REDIS_HOST: "ms-reservations-redis"
  REDIS_PORT: "6379"
  REDIS_DB: "0"
  REDIS_PWD: "4n_ins3cure_P4ss"

  redis-config: |
    appendonly yes
```

```
kubectl apply -f .
```

## Schritt 7: Redis ausrollen 

```
nano 02-redis-deploy.yml 
```


```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ms-reservations-redis
spec:
  replicas: 1
  selector:
    matchLabels:
      storage: redis
  strategy:
    type: Recreate

  template:
    metadata:
      labels:
        storage: redis

    spec:
      containers:
        - command:
            - redis-server
            - /usr/local/etc/redis/redis.conf
            - --requirepass
            - 4n_ins3cure_P4ss
          env:
            - name: REDIS_REPLICATION_MODE
              value: master
          image: redis:6-alpine
          name: ms-reservations-redis
          ports:
            - containerPort: 6379
          volumeMounts:
            - mountPath: /data
              name: data
            - mountPath: /usr/local/etc/redis/redis.conf
              name: config
      volumes:
        - name: data
          emptyDir: {}
        - name: config
          configMap:
            name: redis-cm
            items:
            - key: redis-config
              path: redis.conf
```

```
kubectl apply -f .
```


## Schritt 8: service für redis 

```
nano 03-redis-service.yml
```

```
apiVersion: v1
kind: Service
metadata:
  name: ms-reservations-redis
spec:
  ports:
    - name: "redis"
      port: 6379
  selector:
    storage: redis
```

```
kubectl apply -f .
```

## Schritt 9: deployment for reservations 

```
nano 04-reservations-deploy.yml
```

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ms-reservations
spec:
  replicas: 1
  selector:
    matchLabels:
      app: reservations
  template:
    metadata:
      labels:
        app: reservations
    spec:
      containers:
        - command: [ "/bin/bash", "-c", "--" ]
          args: [ "while true; do sleep 30; done;" ]
          #     - ./wait-for.sh
          #   - -t
          #   - "60"
          #   - ms-reservations-redis:6379
          #   - --
          #   - gunicorn
          #   - -b
          #   - 0.0.0.0:5000
          #   - --reload
          #   - -w
          #   - "1"
          #   - service:app
          env:
            - name: FLASK_ENV
              value: development
            - name: REDIS_DB
              valueFrom:
                configMapKeyRef:
                  key: REDIS_DB
                  name: redis-cm
            - name: REDIS_HOST
              valueFrom:
                configMapKeyRef:
                  key: REDIS_HOST
                  name: redis-cm
            - name: REDIS_PORT
              valueFrom:
                configMapKeyRef:
                  key: REDIS_PORT
                  name: redis-cm
            - name: REDIS_PWD
              valueFrom:
                configMapKeyRef:
                  key: REDIS_PWD
                  name: redis-cm
          image: dockertrainereu/reservations-jm:v16
          name: ms-reservations
          ports:
            - containerPort: 5000
          resources: {}
          volumeMounts:
            - mountPath: /app
              name: ms-reservations-claim0

      volumes:
        - name: ms-reservations-claim0
          emptyDir: {}
          #persistentVolumeClaim:
          #  claimName: ms-reservations-claim0
```

```
kubectl apply -f .
```
