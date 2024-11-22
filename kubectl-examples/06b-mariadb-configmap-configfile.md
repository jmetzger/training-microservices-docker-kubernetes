# configmap - configfile 

## configmap zu fuss 

```
cd
mkdir -p manifests
cd manifests
mkdir mariadb-vol-cm
cd mariadb-vol-cm
```

```
nano 01-mariadb-config2.yml 
```

```
kind: ConfigMap 
apiVersion: v1 
metadata:
  name: example-configmap 
data:
  # als Wertepaare
  database: mongodb
  my.cnf: |
   [mysqld]
   slow_query_log = 1
   innodb_buffer_pool_size = 1G
  
```

```
kubectl apply -f .
```

```
nano 02-deployment.yaml 
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
        envFrom:
        - configMapRef:
            name: mariadb-configmap

        volumeMounts:
          - name: example-configmap-volume
            mountPath: /etc/mysql

      volumes:
      - name: example-configmap-volume
        configMap:
          name: example-configmap
```

```
kubectl apply -f .
```
