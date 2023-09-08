# Deployment with gitlab ci/cd to kubernetes 

## Prerequisites 

  * 01-xxxx is done by you (manifests created) 


## Schritt 1: Neues repo anlegen und manifeste pushen 

```
## neues repo in gitlab anlegen
## achtung ohne README -> d.h. leer
z.B.
https://gitlab.com/training.tn1/ms-jochen-k8sdeploy
```

```
cd
cd manifests/flight-app/
git init
git add .
git status
git config --global user.email "you@email.com"
git config --global user.name "Phantomas"
git commit -am "initial release"
git log
#  Wo soll es hingehen, aus Startseite im Repo, wenn keine README gesetzt 
git remote add origin https://gitlab.com/training.tn1/ms-jochen-k8sdeploy.git
# In welchem Branch bin ich
git branch
git push -u origin master
```


## Schritt 2: KUBECONFIG_SECRET einrichten 

  * in Settings->CI/CD -> Variables -> KUBECONFIG_SECRET

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/ce299745-c478-409d-8416-0bb8261e8133)

```
# Inhalt kommt von meinem lokalen System, wo ich auch kubectl verwende
#  -> wenn eine Verbindung zum  Cluster besteht, ansonsten aus management tool des Clusters , z.B microk8s config 
cat .kube/config
```


## Schritt 3: pipeline mit kubectl einrichten 

  * Ich brauche ein image, das kubectl kann 


```
# on gitlab create a new pipeline
# by editing with pipeline editor
```

```
# use the following content 
deploy:
  image:
    name: bitnami/kubectl:latest
    entrypoint: ['']
  script:
    - kubectl cluster-info
    - ls -la

```



## Ref: 

  * https://docs.gitlab.com/ee/user/clusters/agent/ci_cd_workflow.html#update-your-gitlab-ciyml-file-to-run-kubectl-commands
  
  
