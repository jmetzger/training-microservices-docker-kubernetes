# Service - Examples 

## Warum Services ? 

  * Wenn in einem Deployment bei einem Wechsel des images neue Pods erstellt werden, erhalten diese eine neue IP-Adresse
  * Nachteil: Man müsste diese dann in allen Applikation ständig ändern, die auf die Pods zugreifen.
  * Lösung: Wir schalten einen Service davor !

## Hintergrund IP-Wechsel 
 
 <img width="930" height="134" alt="image" src="https://github.com/user-attachments/assets/26c16134-1f2a-4b42-8cca-355099d08604" />

 * Image-Version wurde jetzt in Deployment geändert, Ergebnis:

<img width="939" height="137" alt="image" src="https://github.com/user-attachments/assets/fb5a665b-98a7-445b-8ec7-27f12c2267e1" />

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
# -> podip will disappear from service / kubectl describe svc-nginx 
kubectl delete po web-nginx-596cdd7d5c-2lsr6
kubectl get pods -o wide

kubectl get svc svc-nginx 

# New pod (with pod-ip) is detected by service
# and now in the list of the endpoints 
kubectl describe svc svc-nginx 
```


## Example II : Short version 

```
nano service.yml
# in Zeile type: 
# ClusterIP ersetzt durch NodePort 

kubectl apply -f .
kubectl get svc
# über welche externe IP können wir zugreifen ? 
kubectl get nodes -o wide
# im client 
curl http://164.92.193.245:30280
```

## Example II : Service with NodePort (long version)

```
apiVersion: v1
kind: Service
metadata:
  name: svc-nginx
spec:
  type: NodePort
  ports:
  - port: 80
    protocol: TCP
  selector:
    app: my-nginx
```        

## Ref.

  * https://kubernetes.io/docs/concepts/services-networking/connect-applications-service/
