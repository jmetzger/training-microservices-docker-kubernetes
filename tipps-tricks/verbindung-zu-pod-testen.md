# Verbindung zu pod testen 

## Situation 

```
Managed Cluster und ich kann nicht auf einzelne Nodes per ssh zugreifen
```

## Was wollen wir testen (auf der Verbindungsebene) ?

<img width="900" height="343" alt="image" src="https://github.com/user-attachments/assets/937221ca-20ff-4b1f-926c-cee1f5923f60" />


## Behelf: Eigenen Pod starten mit busybox 

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/e49012af-c278-4922-8029-53896402e85a)


```
kubectl run podtest --rm -it --image busybox
```

## Example test connection 

### Schritt 1: Die IP des Pods raussuchen, den ich den testen möchte 

```
kubectl get pods -o wide
```


### Schritt 2: Verbindung test 

```
# -O -> Output (grosses O (buchstabe)) 
kubectl run podtest --rm -ti --image busybox
/ # wget -O - http://10.244.0.99
/ # ping -c 4 10.244.0.99
/ # exit 
```
