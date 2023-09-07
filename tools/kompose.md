# Install kompose 

## 

```
# als root
sudo su -
```

```
curl -L https://github.com/kubernetes/kompose/releases/download/v1.26.0/kompose-linux-amd64 -o kompose
chmod +x kompose
sudo mv ./kompose /usr/local/bin/kompose
```



## Ref:

  * https://kubernetes.io/docs/tasks/configure-pod-container/translate-compose-kubernetes/
