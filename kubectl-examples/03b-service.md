# Service - Examples 

## Example I : Service with ClusterIP 

```
cd 
cd manifests
mkdir 04-service 
cd 04-service 
```

```
nano deploy.yml 
```

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: web-nginx
spec:
  selector:
    matchLabels:
      web: my-nginx
  replicas: 2
  template:
    metadata:
      labels:
        web: my-nginx
    spec:
      containers:
      - name: cont-nginx
        image: nginx
        ports:
        - containerPort: 80
```

```
nano service.yml
```


```
apiVersion: v1
kind: Service
metadata:
  name: svc-nginx
spec:
  type: ClusterIP
  ports:
  - port: 80
    protocol: TCP
  selector:
    web: my-nginx              
```        

```
kubectl apply -f . 
```

```
# find out endpoints, if they are working
kubectl get svc svc-nginx 
kubectl describe svc svc-nginx 
```

```
# now delete pod and see changes
# adjust pod-name 
kubectl delete po web-nginx-596cdd7d5c-2lsr6
kubectl get pods -o wide
kubectl get svc
# now the new podips should be found 
kubectl describe svc svc-nginx 
```


## Example II : Short version 

```
nano svc.yml
# in Zeile type: 
# ClusterIP ersetzt durch NodePort 

kubectl apply -f .
kubectl get svc
kubectl get nodes -o wide
# im client 
curl http://164.92.193.245:30280
```

## Example II : Service with NodePort (long version)

```
# you will get port opened on every node in the range 30000+
apiVersion: apps/v1
kind: Deployment
metadata:
  name: web-nginx
spec:
  selector:
    matchLabels:
      run: my-nginx
  replicas: 2
  template:
    metadata:
      labels:
        run: my-nginx
    spec:
      containers:
      - name: cont-nginx
        image: nginx
        ports:
        - containerPort: 80
---
apiVersion: v1
kind: Service
metadata:
  name: svc-nginx
  labels:
    run: svc-my-nginx
spec:
  type: NodePort
  ports:
  - port: 80
    protocol: TCP
  selector:
    run: my-nginx
       
```        





## Ref.

  * https://kubernetes.io/docs/concepts/services-networking/connect-applications-service/
