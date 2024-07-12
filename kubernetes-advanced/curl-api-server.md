# Curl api - server 

## Walkthrough 

```
kubectl create ns app 
```

```
kubectl -n app create serviceaccount api-service-explorer
```

```
cd
mkdir -p manifests
cd manifests
mkdir curltest
cd curltest
```

```
nano 01-clusterrole.yml
```

```
kind: ClusterRole
apiVersion: rbac.authorization.k8s.io/v1beta1
metadata:
  name: service-reader
rules:
- apiGroups: [""] # "" indicates the core API group
  resources: ["services"]
  verbs: ["get", "list"]
```

```
kubectl -n app apply -f .
```

```
kubectl -n app create rolebinding api-service-explorer:service-reader --clusterrole service-reader --serviceaccount app:api-explorer
```
## Reference 

  * https://nieldw.medium.com/curling-the-kubernetes-api-server-d7675cfc398c
