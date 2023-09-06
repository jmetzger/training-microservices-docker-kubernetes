# Wichtige kubectl kommandos 

## Allgemein 

```
# Zeige Information über das Cluster 
kubectl cluster-info 

# Welche api-resources gibt es ?
kubectl api-resources 

# Hilfe zu object und eigenschaften bekommen
kubectl explain pod 
kubectl explain pod.metadata
kubectl explain pod.metadata.name 

```

## Arbeiten mit manifesten 

```
kubectl apply -f nginx-replicaset.yml 
# Wie ist aktuell die hinterlegte config im system
kubectl get -o yaml -f nginx-replicaset.yml 

# Änderung in nginx-replicaset.yml z.B. replicas: 4 
# dry-run - was wird geändert 
kubectl diff -f nginx-replicaset.yml 

# anwenden 
kubectl apply -f nginx-replicaset.yml 

# Alle Objekte aus manifest löschen
kubectl delete -f nginx-replicaset.yml 


```

## Ausgabeformate 

```
# Ausgabe kann in verschiedenen Formaten erfolgen 
kubectl get pods -o wide # weitere informationen 
# im json format
kubectl get pods -o json 

# gilt natürluch auch für andere kommandos
kubectl get deploy -o json 
kubectl get deploy -o yaml 
```



## Zu den Pods 

```
# Start einen pod // BESSER: direkt manifest verwenden
# kubectl run podname image=imagename 
kubectl run nginx image=nginx 

# Pods anzeigen 
kubectl get pods 
kubectl get pod
# Format weitere Information 
kubectl get pod -o wide 
# Zeige labels der Pods
kubectl get pods --show-labels 

# Zeige pods mit einem bestimmten label 
kubectl get pods -l app=nginx 

# Status eines Pods anzeigen 
kubectl describe pod nginx 

# Pod löschen 
kubectl delete pod nginx 

# Kommando in pod ausführen 
kubectl exec -it nginx -- bash 

```

## Zu den Pods (Logs) 

```
# log eines pods anzeigen
kubectl logs podname

# Logs aller pods im Deployment
# Wichtig Option --prefix
kubectl logs --prefix deploy/web-nginx 

```


## Arbeiten mit namespaces 

```
# Welche namespaces auf dem System 
kubectl get ns 
kubectl get namespaces 
# Standardmäßig wird immer der default namespace verwendet 
# wenn man kommandos aufruft 
kubectl get deployments 

# Möchte ich z.B. deployment vom kube-system (installation) aufrufen, 
# kann ich den namespace angeben
kubectl get deployments --namespace=kube-system 
kubectl get deployments -n kube-system 

# wir wollen unseren default namespace ändern 
kubectl config set-context --current --namespace <dein-namespace>
```



## Referenz

  * https://kubernetes.io/de/docs/reference/kubectl/cheatsheet/
