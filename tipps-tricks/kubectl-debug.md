# Netzwerk des Pods debuggen (kommt er raus) 

## Andere Anwendungsfälle 

  * Tools die nicht auf dem Pod installiert sind, benötigen

## Walkthrough 

```
kubectl run my-nginx --image=nginx
# Daneben einen pod starten, der auf das gleiche Netzwerk zugreift (d.h. die gleiche IP-Adresse hat)
kubectl debug my-nginx --image=busybox
```

```
# Kann ich rauspingen ?
ping www.google.de
```

## Reference:

  * https://kubernetes.io/docs/reference/kubectl/generated/kubectl_debug/
  * https://kubernetes.io/docs/tasks/debug/debug-application/debug-running-pod/
