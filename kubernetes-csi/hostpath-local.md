# HostPath Local 

## Prerequisites 

  * We need to create the folder on the file system
  * k3s (one node only) is running 

## Walkthrough 

```
# do this a root 
sudo su -
```

```
mkdir /data
chmod 777 /data 
```

```
cd
mkdir -p manifests
cd manifests
mkdir local-hostpath
cd local-hostpath
nano 01-storageclass.yml 
```

```
kind: StorageClass
apiVersion: storage.k8s.io/v1
metadata:
  name: local-storage
provisioner: kubernetes.io/no-provisioner
volumeBindingMode: WaitForFirstConsumer
```

```
kubectl apply -f .
```

```
nano 02-pv.yaml
```

```
## local value requires nodeAffinity 
apiVersion: v1
kind: PersistentVolume
metadata:
  name: test-local-pv
spec:
  capacity:
    storage: 10Gi
  accessModes:
  - ReadWriteOnce
  persistentVolumeReclaimPolicy: Retain
  storageClassName: local-storage
  local:
    path: /data
  nodeAffinity:
    required:
      nodeSelectorTerms:
      - matchExpressions:
        - key: kubernetes.io/hostname
          operator: In
          values:
          - tln1 
```

```
kubectl apply -f .
```

```
nano 03-pvc.yaml
```

```
kind: PersistentVolumeClaim
apiVersion: v1
metadata:
  name: test-pvc
spec:
  accessModes:
  - ReadWriteOnce
  storageClassName: local-storage
  resources:
    requests:
      storage: 10Gi
```

```
nano 04-pod.yaml
```

```
apiVersion: v1
kind: Pod
metadata:
  name: test-local-vol
  labels:
    name: test-local-vol
spec:
  containers:
  - name: app
    image: busybox
    command: ['sh', '-c', 'echo "The local volume is mounted!" > /mnt/test.txt && sleep 3600']
    volumeMounts:
      - name: local-persistent-storage
        mountPath: /mnt
  volumes:
    - name: local-persistent-storage
      persistentVolumeClaim:
        claimName: test-pvc
```

```
kubectl apply -f .
```

## Reference :

  * https://lapee79.github.io/en/article/use-a-local-disk-by-local-volume-static-provisioner-in-kubernetes/
