# Deploying manifests from github actions 

## Step 1: Create Repo in github 

```
# url
https://github.com/new
```

## Step 2: Clone Repo to local system 

```
# on local system -> clone to k8s-deploy
cd
mkdir -p github-test
cd github-test 
# so we all have the same folder in the training (for our ease) 
git clone <your-repo> k8s-deploy
cd k8s-deploy
```

```
mkdir -p manifests
cd manifests
nano 01-pod.yaml 
```

## Step 3: Populate Repo with sample manifest 

```
apiVersion: v1
kind: Pod
metadata:
  name: static-web
  labels:
    role: myrole
spec:
  containers:
    - name: web
      image: nginx
      ports:
        - name: web
          containerPort: 80
          protocol: TCP
```

## Step 4: Push changes 

```
git config --global user.email test@test.de
git config --global user.name "Jochen from m1"
```

```
git add -A
git commit -am "Initial Release"
git push -u origin main
```


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
