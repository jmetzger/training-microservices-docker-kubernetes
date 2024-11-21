# Local-Path provisioner from rancher

## Prerequisites :

  * Install storage-class local-path from rancher github 
  * https://github.com/rancher/local-path-provisioner

## Exercise 1

```
cd
mkdir -p manifests
cd manifests
mkdir local-path
cd local-path
nano 01-pvc.yaml
```

```
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: local-path-pvc
spec:
  accessModes:
    - ReadWriteOnce
  storageClassName: local-path
  resources:
    requests:
      storage: 128Mi
```

```
nano 02-pod.yaml
```

```
apiVersion: v1
kind: Pod
metadata:
  name: volume-test
spec:
  containers:
  - name: volume-test
    image: nginx:stable-alpine
    imagePullPolicy: IfNotPresent
    volumeMounts:
    - name: volv
      mountPath: /data
    ports:
    - containerPort: 80
  volumes:
  - name: volv
    persistentVolumeClaim:
      claimName: local-path-pvc
```


```
kubectl apply -f .
kubectl get pods volume-test 
```

