# Verbindung zu pod testen 

## Situation 

```
Managed Cluster und ich kann nicht auf einzelne Nodes per ssh zugreifen
```

## Behelf: Eigenen Pod starten mit busybox 

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
kubectl run podtest --rm -ti --image busybox -- /bin/sh
/ # wget -O - http://10.244.0.99
/ # ping 10.244.0.99
/ # exit 
```
