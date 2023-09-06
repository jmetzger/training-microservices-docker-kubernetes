# Verbindung zu pod testen 

## Situation 

```
Managed Cluster und ich kann nicht auf einzelne Nodes per ssh zugreifen
```

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
/ # ping 10.244.0.99
/ # exit 
```
