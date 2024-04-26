# Deploying manifests from github actions 

## Step 1: Create Repo in github 


## Step 2: Clone Repo 


## Step 3: Populate Repo with sample manifest 


## Step 4: Push changes 


## Step 5: Setup authentication in kubernetes (service account) 

```
mkdir manifests
cd manifests
mkdir github-account
cd github-account 
nano 01-clusterrole.yml 
```

```
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
  name: continuous-deployment
rules:
  - apiGroups:
      - ''
      - apps
      - networking.k8s.io
    resources:
      - namespaces
      - deployments
      - replicasets
      - ingresses
      - services
      - secrets
    verbs:
      - create
      - delete
      - deletecollection
      - get
      - list
      - patch
      - update
      - watch
```

```
kubectl apply -f .
```



## Step 5: Setup github actions 

  * workflow folder: .github/workflows
  * manifests - folder: manifests/
  
```



```




## Reference 

  * https://github.com/marketplace/actions/deploy-to-kubernetes-cluster
