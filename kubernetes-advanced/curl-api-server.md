# Curl api - server 

## Step 1: Prepare Permissions  

```
kubectl create ns app 
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
apiVersion: rbac.authorization.k8s.io/v1
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
# Einfacher hack, wir verwenden den default-service - account
kubectl -n app create rolebinding api-service-explorer:default --clusterrole service-reader --serviceaccount app:default

```

## Schritt 2: curlimage/curl starten

```
kubectl run -it --rm curltest --image=curlimages/curl -- sh
```

## Schritt 3: in curl - shell 

```
cd /var/run/secrets/kubernetes.io/serviceaccount
TOKEN=$(cat token)
env | grep KUBERNETES_SERVICE
curl https://$KUBERNETES_SERVICE_HOST/openapi/v2 --header "Authorization: Bearer $TOKEN" --cacert ca.crt
curl https://$KUBERNETES_SERVICE_HOST/api/v1/namespaces/app/services/ --header "Authorization: Bearer $TOKEN" --cacert ca.crt
```

```
# Now look into one of the services
 curl https://$KUBERNETES_SERVICE_HOST/api/v1/namespaces/app/services/apple-service/ --header "Authorization: Bearer $TOKEN" --cacert ca.crt
```

## Reference 

  * https://nieldw.medium.com/curling-the-kubernetes-api-server-d7675cfc398c
