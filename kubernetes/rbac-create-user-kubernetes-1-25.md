# RBAC - Create user for kubeconfig with restricted permissions

## Schritt 1: Nutzer-Account auf Server anlegen und secret anlegen / in Client 

```
cd 
mkdir -p manifests/rbac
cd manifests/rbac
```

###  Mini-Schritt 1: Definition für Nutzer 

```
nano 01-sa.yml 
```

```
# vi service-account.yml 
apiVersion: v1
kind: ServiceAccount
metadata:
  name: training
```

```
kubectl apply -f .
```

### Mini-Schritt 1.5: Secret erstellen 

  * From Kubernetes 1.25 tokens are not created automatically when creating a service account (sa)
  * You have to create them manually with annotation attached 
  * https://kubernetes.io/docs/reference/access-authn-authz/service-accounts-admin/#create-token

```
nano 02-secret.yml
```

```
# vi secret.yml 
apiVersion: v1
kind: Secret
type: kubernetes.io/service-account-token
metadata:
  name: trainingtoken
  annotations:
    kubernetes.io/service-account.name: training
```

```
kubectl apply -f .
```


### Mini-Schritt 2: ClusterRolle festlegen - Dies gilt für alle namespaces, muss aber noch zugewiesen werden

```
nano 03-cr.yml 
```

```
## Bevor sie zugewiesen ist, funktioniert sie nicht - da sie keinem Nutzer zugewiesen ist 

# vi pods-clusterrole.yml 
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
  name: pods-clusterrole
rules:
- apiGroups: [""] # "" indicates the core API group
  resources: ["pods"]
  verbs: ["get", "watch", "list"]
```

```
kubectl apply -f -
```

### Mini-Schritt 3: Die ClusterRolle den entsprechenden Nutzern über RoleBinding zu ordnen 

```
nano 04-crb.yml
```

```
# vi rb-training-ns-default-pods.yml
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
  name: rolebinding-ns-default-pods
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: pods-clusterrole 
subjects:
- kind: ServiceAccount
  name: training
```

```
kubectl apply -f .
```

### Mini-Schritt 4: Testen (klappt der Zugang) 

```
# kubectl auth can-i get pods --as system:serviceaccount:<deinnamespace>:training
kubectl auth can-i get pods --as system:serviceaccount:jochen:training
```

## Schritt 2: Context anlegen / Credentials auslesen und in kubeconfig hinterlegen (bis Version 1.25.) 

### Mini-Schritt 1: kubeconfig setzen 

```
kubectl config set-context training-ctx --cluster do-fra1-ks-cluster --user training

# extract name of the token from here 

TOKEN=`kubectl get secret trainingtoken -o jsonpath='{.data.token}' | base64 --decode`
echo $TOKEN
kubectl config set-credentials training --token=$TOKEN
kubectl config use-context training-ctx
```

```
# kubectl config set-context --current --namespace <dein-name>
kubectl config set-context --current --namespace jochen 
```

```
# Hier reichen die Rechte nicht aus 
kubectl get deploy
# Error from server (Forbidden): pods is forbidden: User "system:serviceaccount:jochen:training" cannot list # resource "pods" in API group "" in the namespace "jochen"
```

### Mini-Schritt 2:
```
kubectl config use-context training-ctx
kubectl get pods 
```

### Mini-Schritt 3: Zurück zum alten Default-Context 

```
kubectl config get-contexts
```

```
CURRENT   NAME                          CLUSTER            AUTHINFO    NAMESPACE
          do-fra1-ks-cluster-admin      do-fra1-ks-cluster   admin
*         training-ctx                  do-fra1-ks-cluster   training
```

```
kubectl config use-context microk8s  
```


## Refs:

  * https://docs.oracle.com/en-us/iaas/Content/ContEng/Tasks/contengaddingserviceaccttoken.htm
  * https://microk8s.io/docs/multi-user
  * https://faun.pub/kubernetes-rbac-use-one-role-in-multiple-namespaces-d1d08bb08286

## Ref: Create Service Account Token 

  * https://kubernetes.io/docs/reference/access-authn-authz/service-accounts-admin/#create-token
