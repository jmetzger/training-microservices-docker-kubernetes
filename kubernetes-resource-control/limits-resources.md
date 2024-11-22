# Working with Limits and Resources in Containers 

## Prerequisites - install metrics - server 

  * That one is already present in k3s

```
helm repo add metrics-server https://kubernetes-sigs.github.io/metrics-server/
helm install my-metrics-server metrics-server/metrics-server --version 3.12.2
```


## Reference: 

  * 
