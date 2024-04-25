# Statefulset 

##

```
cd
mkdir -p manifests
cd manifests
mkdir sts
cd sts
```

```
nano sts.yaml 
```

## 

```
apiVersion: v1
kind: Service
metadata:
  name: nginx
  labels:
    app: nginx
spec:
  ports:
  - port: 80
    name: web
  clusterIP: None
  selector:
    app: nginx
---
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: web
spec:
  serviceName: "nginx"
  replicas: 2
  selector:
    matchLabels:
      app: nginx
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: registry.k8s.io/nginx-slim:0.8
        ports:
        - containerPort: 80
          name: web
```

```
kubectl apply -f .
```


## Auflösung Namen.

```
ping web-0.nginx 
ping web-1.nginx 
```

## Test der Auflösung 

```
kubectl run --rm -it podtester --image=busybox
```

```
# ping web-0.nginx
# ping web-1.nginx
# exit 
```

## Referenz 

  * https://kubernetes.io/docs/tutorials/stateful-application/basic-stateful-set/

