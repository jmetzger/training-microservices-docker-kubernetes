# Deploying manifests from github actions 

## Step 1: Create Repo in github 

```
# url
https://github.com/new

# Bitte hier keine Dateien anlegen
# keine README.md
# keine .gitignore

```

## Step 2: Create personal access token (Optional) 

  * you can do this here: https://github.com/settings/tokens/new

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/1ff54521-7f4d-4edb-8cba-f0c20a30782b)


## Step 3: Lokal projekt anlegen (auf dem kubectl - client) 

```
cd
mkdir -p github-test
cd github-test 
```

```
mkdir -p manifests
cd manifests
nano 01-deployment.yaml 
```

## Step 4: Populate project with sample manifest 

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
spec:
  selector:
    matchLabels:
      app: nginx
  replicas: 8
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.23
        ports:
        - containerPort: 80
```

## Step 5: Projekt unter Versionsverwaltung stellen und pushen 

```
git init
git add .
git commit -am "Initial Release" 
git config --global user.email test@test.de
git config --global user.name "Jochen from m1"
git push -u origin master 
```


## Step 6: Setup authentication in kubernetes (service account) - in kubectl - client 

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

```
kubectl create clusterrolebinding continuous-deployment-tln<nr> \
    --clusterrole=continuous-deployment-tln<nr>
    --serviceaccount=<dein-namespace>:github-actions-tln<nr>
```

## Step 7: secrets auslesen und bei github eintragen 

```
kubectl get secrets github-actions-secret -o yaml 
```

```
# Copy the output
```

```
# Enter it here, by adding a new secret: KUBERNETES_SECRET
https://github.com/gittrainereu/<your-repo>/settings/secrets/actions/new
```

```
# Get the url of your kubernetes cluster
# And Copy it to clipboard
# We will need this for your pipeline 
kubectl config view -o 'jsonpath={.clusters[0].cluster.server}'
```

## Step 8: Setup github actions (in web ui of github)

  * workflow folder: .github/workflows
  * manifests - folder: manifests/
  
```
# create file .github/workflows/pipeline.yaml
# with content 
```

```
# adjust
# 1. server-url / use data from last step 
# 2. your-name / use your own namespace here
name: CI/CD
on: push
jobs:
  deploy:
    name: Deploy
    # needs: [ test, build ]
    runs-on: ubuntu-latest
    steps:
      - name: Set the Kubernetes context
        uses: azure/k8s-set-context@v2
        with:
          method: service-account
          k8s-url: <server-url>
          k8s-secret: ${{ secrets.KUBERNETES_SECRET }}

      - name: Checkout source code
        uses: actions/checkout@v3

      - name: Deploy to the Kubernetes cluster
        uses: azure/k8s-deploy@v1
        with:
          namespace: <yournamespace>
          manifests: |
            manifests/

```

## Step 9: watch and enjoy 




## Reference 

  * https://github.com/marketplace/actions/deploy-to-kubernetes-cluster
