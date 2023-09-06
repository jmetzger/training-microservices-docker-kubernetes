# Replicaset

## Schritt 1: Erstellen 

```
cd
mkdir -p manifests
cd manifests
mkdir 02-rs 
cd 02-rs 
nano rs.yml
```

```
apiVersion: apps/v1
kind: ReplicaSet
metadata:
  name: nginx-replica-set
spec:
  replicas: 10
  selector:
    matchLabels:
      tier: frontend
  template:
    metadata:
      name: template-nginx-replica-set
      labels:
        tier: frontend
    spec:
      containers:
        - name: nginx
          image: nginx:1.21
          ports:
            - containerPort: 80
             

             
```

```
kubectl apply -f rs.yml 
```

## Schritt 2: Erforschen 

```
kubectl get all
# Hash entsprechend anpassen
kubectl delete po nginx-replica-set-<hash>
# Dass einer neuer Pod dazugekommen ist (seht ihr an der Zeit) 
kubectl get all
```

```
# ändern image in rs.yml
# vorher
# image: 1.23
# jetzt
image: 1.22 
```

```
kubectl apply -f .
```

```
# Gibt es neue Pods ?
kubectl get all
# Welche Image - Version
kubectl describe pods nginx-replica-set-vh6cl
```

```
# FYI 
kubectl get deploy
kubectl get rs
kubectl get pods --showLabels
```
