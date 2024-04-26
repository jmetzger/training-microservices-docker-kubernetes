# Deploying manifests from github actions 

## Step 1: Create Repo in github 

```
# url
https://github.com/new
```

## Step 2: Create personal access token 

  * you can do this here: https://github.com/settings/tokens/new

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/1ff54521-7f4d-4edb-8cba-f0c20a30782b)




## Step 2: Clone Repo to local system (machine where we use kubectl ) 

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


## Step 5: Setup authentication in kubernetes (service account) - in kubectl - client 

```
# wird in deinem namespace angelegt 
# create serviceaccount
kubectl create serviceaccount github-actions-tln<nr>
```

```
cd
mkdir -p manifests
cd manifests
mkdir github-account
cd github-account 
```

```
nano 01-sasecret.yaml
```

```
# Secret für service account anlegen / wichtig: muss
# in neueren Versionen von kubernetes gemacht werden
# da secrets nicht mehr automatisc angelegt werden
# beim Erstellen von service account (Stand: 26.04.2024) 
apiVersion: v1
kind: Secret
type: kubernetes.io/service-account-token
metadata:
  name: github-actions-secret 
  annotations:
    kubernetes.io/service-account.name: github-actions-tln<nr>
```

```
kubectl apply -f .
```

```
nano 02-clusterrole.yml 
```

```
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
  name: continuous-deployment-tln<nr>
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
