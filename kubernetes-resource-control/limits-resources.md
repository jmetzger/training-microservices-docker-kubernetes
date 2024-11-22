# Working with Limits and Resources in Containers 

## Prerequisites - install metrics - server 

  * That one is already present in k3s

```
helm repo add metrics-server https://kubernetes-sigs.github.io/metrics-server/
helm install metrics-server metrics-server/metrics-server --namespace kube-system --version 3.12.2
```

## Szenario 1 

```
cd
mkdir -p manifests
cd manifests
mkdir res
cd res
```

```
nano 01-pod.yaml
```

```
apiVersion: v1
kind: Pod
metadata:
  name: cpu-demo
spec:
  containers:
  - name: cpu-demo-ctr
    image: vish/stress
    resources:
      limits:
        cpu: "1"
      requests:
        cpu: "0.5"
    args:
    - -cpus
    - "2"
```


```
kubectl apply -f .
```

```
kubectl get pods cpu-demo
kubectl get pods cpu-demo -o yaml 
kubectl top pod cpu-demo
```

## Szenario 2


```
nano 02-pod-toomuch.yaml
```

```
# overprovision
apiVersion: v1
kind: Pod
metadata:
  name: cpu-demo-2
  namespace: cpu-example
spec:
  containers:
  - name: cpu-demo-ctr-2
    image: vish/stress
    resources:
      limits:
        cpu: "100"
      requests:
        cpu: "100"
    args:
    - -cpus
    - "2"
```

```
kubectl apply -f .
kubectl describe pod cpu-demo-2
```


## Reference: 

  * https://kubernetes.io/docs/tasks/configure-pod-container/assign-cpu-resource/
