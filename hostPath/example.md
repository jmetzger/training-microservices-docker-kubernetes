# HostPath Example 

## Prerequisites 

  * Folder on Host needs to be present

## Walkthrough 

```
mkdir /data2
chmod 777 /data2
```

```
cd
mkdir -p manifests
cd manifests
mkdir hostPath
cd hostPath
nano 01-deployment.yaml
```

``
apiVersion: apps
kind: Deployment
metadata:
  name: helloworldhostpath
spec:
  replicas: 1
  template:
    metadata:
      labels:
        run: helloworldanilhostpath
    spec:
      volumes:
        - name: task-pv-storage
          hostPath:
            path: /data2
            type: Directory
      containers:
      - name: helloworldv1
        image: nginx
        volumeMounts:
         - name: task-pv-storage
           mountPath: /mnt/sample`

```

```
kubectl apply -f . 
```
