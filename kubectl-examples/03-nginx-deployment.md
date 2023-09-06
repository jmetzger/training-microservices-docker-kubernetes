# Example Deployment nginx 

## Schritt 1: Erstellen 

```
cd
cd manifests
mkdir 03-deploy
cd 03-deploy 
nano deploy.yml 
```

```

# vi deploy.yml 
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
spec:
  selector:
    matchLabels:
      app: nginx
  replicas: 8 # tells deployment to run 2 pods matching the template
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.21
        ports:
        - containerPort: 80
        
```

```
kubectl apply -f deploy.yml 
```

## Schritt 2: Erforschen 

```
kubectl get all 
```

```
# image ändern in deploy.yml
# vorher: image: nginx:1.21
# jetzt
image: nginx:1.23
```

```
# Anwenden und watchen 
kubectl apply -f . ; kubectl get all; kubectl get pods -w
```
