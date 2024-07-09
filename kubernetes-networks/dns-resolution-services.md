# DNS Resolution of services 

## Generic 

  * DNS - Names for Services are automatically created from the Service -> Name
  * + the namespace the service is in
  * + fixed subdomain svc.cluster.local 

## Example:

```
# Service Name: myservice
# Being in Namespace: app
# Results in

myservice
myservice.app
myservice.app.svc.cluster.local 

```

## Walkthrough 

```
kubectl run podtest --rm -ti --image busybox
If you don't see a command prompt, try pressing enter.
/ # wget -O - http://apple-service.jochen
Connecting to apple-service.jochen (10.245.39.214:80)
writing to stdout
apple-tln1
-                    100% |**************************************************************************************************************|    11  0:00:00 ETA
written to stdout
/ # wget -O - http://apple-service.jochen.svc.cluster.local
Connecting to apple-service.jochen.svc.cluster.local (10.245.39.214:80)
writing to stdout
apple-tln1
-                    100% |**************************************************************************************************************|    11  0:00:00 ETA
written to stdout
/ # wget -O - http://apple-service
Connecting to apple-service (10.245.39.214:80)
writing to stdout
apple-tln1
-                    100% |**************************************************************************************************************|    11  0:00:00 ETA
written to stdout
```
