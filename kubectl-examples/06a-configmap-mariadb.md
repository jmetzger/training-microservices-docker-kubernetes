# ConfigMap Example (Mariadb) 

## Schritt 1: configmap 

```
cd 
mkdir -p manifests
cd manifests
mkdir cftest 
cd cftest 
nano 01-configmap.yml 
```

```
kind: ConfigMap 
apiVersion: v1 
metadata:
  name: mariadb-configmap 
data:
  # als Wertepaare
  MARIADB_ROOT_PASSWORD: 11abc432
```

```
kubectl apply -f .
kubectl get cm
kubectl get cm mariadb-configmap -o yaml
```


## Schritt 2: Deployment 
```
nano 02-deploy.yml
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

```

```
kubectl apply -f .
```

## Testing 

```
# Führt den Befehl env in einem Pod des Deployments aus  
kubectl exec deployment/mariadb-deployment -- env
# eigentlich macht er das:
# kubectl exec mariadb-deployment-c6df6f959-q6swp -- env
```


## Important Sidenode 

  * If configmap changes, deployment does not know
  * So kubectl apply -f deploy.yml will not have any effect
  * to fix, use stakater/reloader: https://github.com/stakater/Reloader

