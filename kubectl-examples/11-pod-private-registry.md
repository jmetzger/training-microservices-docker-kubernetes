# Pod aus private registry verwenden

## Create config.json with login docker

### Step 1: On docker machine 

```
docker login
```

```
cat ~/.docker/config.json
```

```
{
        "auths": {
                "https://index.docker.io/v1/": {
                        "auth": "ZG9............"
                }
}
```

```
copy to kubectl client
```

### Step 2: on kubectl-client machine 

```
cd
mkdir -p manifests
cd manifests
mkdir docker
cd docker
```


```
kubectl create secret generic docker-credentials \
    --from-file=.dockerconfigjson=<path/to/.docker/config.json> \
    --type=kubernetes.io/dockerconfigjson
    --dry-run=client -o yaml > secret.yaml 
```

```
cat secret.yaml
```

```
# umbenennen, damit es nicht von kubectl apply gelesen wird
mv config.json config.json.bkup 
```

```
kubectl apply -f .
```

```
nano pod.yaml 
```

```
apiVersion: v1
kind: Pod
metadata:
  name: dockertrainereu-pod
spec:
  containers:
  - name: private-container
    image: dockertrainereu/jm-hello-web
  imagePullSecrets:
  - name: docker-credentials
```

```
kubectl apply -f .
```
