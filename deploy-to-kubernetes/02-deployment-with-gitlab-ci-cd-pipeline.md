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
  



```

## Schritt 2: 
