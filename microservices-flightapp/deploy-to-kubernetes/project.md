# Project flight-app  

## Schritt 1: configmap 

```
cd 
mkdir -p manifests
cd manifests
mkdir flight-app
cd flight-app
mkdir flights
cd flights 
nano 01-configmap.yml 
```

```
## 01-configmap.yml
kind: ConfigMap 
apiVersion: v1 
metadata:
  name: mariadb-configmap 
data:
  # als Wertepaare
  MARIADB_ROOT_PASSWORD: 11abc432
```

```
cd
cd manifests/flight-app
# alle Unterverzeichnisse recursiv ausführen 
kubectl apply -Rf .
kubectl get cm
kubectl get cm mariadb-configmap -o yaml
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



## Schritt 3: Deployment 

```
cd
cd manifests/flight-app/flights 
nano 03-deploy.yml
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
        - configMapRef:
            name: mariadb-configmap
        
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

## Schritt 4: Lokal kompose installieren 

[Kompose installieren](tools/kompose.md)



## Important Sidenode 

  * If configmap changes, deployment does not know
  * So kubectl apply -f deploy.yml will not have any effect
  * to fix, use stakater/reloader: https://github.com/stakater/Reloader

