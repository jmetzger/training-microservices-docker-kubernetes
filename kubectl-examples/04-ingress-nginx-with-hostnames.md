# Ingress Nginx 

## Prequisites 

  * An IngressController is running in your cluster. Ask the trainer if you are unsure.

## Aufsetzen mit Ingress-Fehler

### Step 1: pods and services

```
cd
mkdir -p manifests
cd manifests 
mkdir abi
cd abi
```

```
# apple.yml 
# vi apple.yml 
kind: Pod
apiVersion: v1
metadata:
  name: apple-app
  labels:
    app: apple
spec:
  containers:
    - name: apple-app
      image: hashicorp/http-echo
      args:
        - "-text=apple-<dein-name>"
```

```
kubectl apply -f .
nano apple-service.yml
```

```
kind: Service
apiVersion: v1
metadata:
  name: apple-service
spec:
  selector:
    app: apple
  ports:
    - protocol: TCP
      port: 80
      targetPort: 5678 # Default port for image
```

```
kubectl apply -f .
```

```
# banana
# vi banana.yml
kind: Pod
apiVersion: v1
metadata:
  name: banana-app
  labels:
    app: banana
spec:
  containers:
    - name: banana-app
      image: hashicorp/http-echo
      args:
        - "-text=banana-<dein-name>"

---

kind: Service
apiVersion: v1
metadata:
  name: banana-service
spec:
  selector:
    app: banana
  ports:
    - port: 80
      targetPort: 5678 # Default port for image
```

```
kubectl apply -f banana.yml
```

### Step 2: Ingress 

```
# Ingress
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: example-ingress
  annotations:
    ingress.kubernetes.io/rewrite-target: /
    # with the ingress controller from helm, you need to set an annotation 
    # otherwice it does not know, which controller to use
    # old version... use ingressClassName instead 
    # kubernetes.io/ingress.class: nginx
spec:
  ingressClassName: nginx
  rules:
  - host: "<euername>.lab1.t3isp.de"
    http:
      paths:
        - path: /apple
          backend:
            serviceName: apple-service
            servicePort: 80
        - path: /banana
          backend:
            serviceName: banana-service
            servicePort: 80
```

```
# ingress 
kubectl apply -f ingress.yml
kubectl get ing 
```

### Schritt 3: Wir finden/lösen das Problem 

#### (Mini-)Schritt 2.1 api-resource und ApiVersion identifizieren 

  * Den richtige api-resource and die richtige appVersion finden 

```
# Bitte nur die ersten 3 Zeilen anzeigen -> head -n 3  
kubectl explain ingress | head -n 3
```

```
# Nur Anzeige, nicht eingeben 
# GROUP:      networking.k8s.io
# KIND:       Ingress
# VERSION:    v1
```

```
# Wir erkennen das die richtige API-Ressource
# NICHT extensions/v1beta1
# SONDERN networking.k8s.io/v1
```

#### (Mini-)Schritt 2.2. api-resource und apiVersion ändern und ausführen 

```
# ist und korrigieren das.
nano ingress.yml 
```

```
# nur 
# apiVersion ändern wie folgt
apiVersion: networking.k8s.io/v1
# ändern 
```

```
# Das gesamte File sieht jetzt so aus:
# Ingress
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: example-ingress
  annotations:
    ingress.kubernetes.io/rewrite-target: /
    # with the ingress controller from helm, you need to set an annotation 
    # otherwice it does not know, which controller to use
    # old version... use ingressClassName instead 
    # kubernetes.io/ingress.class: nginx
spec:
  ingressClassName: nginx
  rules:
  - host: "<euername>.lab1.t3isp.de"
    http:
      paths:
        - path: /apple
          backend:
            serviceName: apple-service
            servicePort: 80
        - path: /banana
          backend:
            serviceName: banana-service
            servicePort: 80
```

``` unknown field "spec.rules[0].http.paths[0].backend.serviceName"
kubectl apply -f .
```


#### (Mini-)Schritt 2.3: Fehler verstehen 

```
# Hier der letzte Fehler aus Schritt 2.2.
Error from server (BadRequest): error when creating "ingress.yml": Ingress in version "v1" cannot be handled as a Ingress: strict decoding error: unknown field "spec.rules[0].http.paths[0].backend.serviceName", unknown field "spec.rules[0].http.paths[0].backend.servicePort", unknown field "spec.rules[1].http.paths[0].backend.serviceName", unknown field "spec.rules[1].http.paths[0].backend.servicePort"
```

```
# Auszug
unknown field "spec.rules[0].http.paths[0].backend.serviceName", unknown field "spec.rules[0].http.paths[0].backend.servicePort", unknown field

# Bedeutet
# Ich kenne das Feld spec..backend.servicePort nicht.

# Heisst aber auch:
# Das Feld spec.rules.http.paths.backend .. kenne ich schon
```

```
# Wir forschen
kubectl explain ingress.spec.rules.http.paths.backend
```

```
# Er kennt eine Eigenschaft service, aber eben nich serviceName
FIELD: backend <IngressBackend>
...
  service       <IngressServiceBackend>
    service references a service as a backend. This is a mutually exclusive
    setting with "Resource".
```

```
# Was möchte er unter service haben ? 
kubectl explain ingress.spec.rules.http.paths.backend.service
```

```
# Bingo: name 
GROUP:      networking.k8s.io
KIND:       Ingress
VERSION:    v1

FIELD: service <IngressServiceBackend>
[...]
FIELDS:
  name  <string> -required-
    name is the referenced service. The service must exist in the same namespace
    as the Ingress object.
```

```
# Ergebnis:
# Statt:
# ingress.spec.rules.http.paths.backend.serviceName
# Erwartet Kubernetes jetzt:
# ingress.spec.rules.http.paths.backend.service.name
```
 
#### (Mini-)Schritt 2.4: Lösung umsetzen

```
nano ingress.yml
````

```
# Das gesamte File sieht jetzt so aus:
# Ingress
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: example-ingress
  annotations:
    ingress.kubernetes.io/rewrite-target: /
    # with the ingress controller from helm, you need to set an annotation 
    # otherwice it does not know, which controller to use
    # old version... use ingressClassName instead 
    # kubernetes.io/ingress.class: nginx
spec:
  ingressClassName: nginx
  rules:
  - host: "<euername>.lab1.t3isp.de"
    http:
      paths:
        - path: /apple
          backend:
            service:
              name: apple-service
            servicePort: 80
        - path: /banana
          backend:
            service:
              name: banana-service
            servicePort: 80
```

```
kubectl apply -f .
# --> Fehler 
```

#### (Mini-)Schritt 2.5: Nächsten Fehler verstehen und umsetzen (servicePort) 

```
# Folgender Fehler nach kubectl apply -f .
Error from server (BadRequest): error when creating "ingress.yml": Ingress in version "v1" cannot be handled as a Ingress: strict decoding error: unknown field "spec.rules[0].http.paths[0].backend.servicePort", unknown field "spec.rules[1].http.paths[0].backend.servicePort"

# <- servicePort kennt er nicht
```

```
# Schrittweise debuggen
kubectl explain ingress.spec.rules.http.paths.backend
kubectl explain ingress.spec.rules.http.paths.backend.service
kubectl explain ingress.spec.rules.http.paths.backend.service.port
# Und er braucht auch nocht Number
kubectl explain ingress.spec.rules.http.paths.backend.service.port.number

# so würde die Eigenschaft dann im yml-file aussehen.
# service:
#   port:
#     number: 80
```

```
# Wir setzen das um
nano service.yml
```

```
# So sieht das korrigierte .yml file aus.
# Ingress
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: apps-ingress
  annotations:
    ingress.kubernetes.io/rewrite-target: /
spec:
  ingressClassName: nginx
  rules:
  - host: app1.dein-training.de
    http:
      paths:
        - path: /apple
          backend:
            service:
              name: apple-service
              port:
                number: 80
        - path: /banana
          backend:
            service:
              name: banana-service
              port:
                number: 80
```

```
kubectl apply -f .
# --> Fehler
```

#### (Mini-)Schritt 2.6: Wir beheben den letzten Fehler 

```
# Fehler
The Ingress "apps-ingress" is invalid:
* spec.rules[0].http.paths[0].pathType: Required value: pathType must be specified
* spec.rules[1].http.paths[0].pathType: Required value: pathType must be specified

# Bedeutet
pathType ist jetz ein Pflichtfeld und wir müssen es es ergänzen
```

```
# Welche Werte sind möglich ?
kubectl explain ingress.spec.rules.http.paths.pathType
```

```
[...]
Possible enum values:
     - `"Exact"` matches the URL path exactly and with case sensitivity.
     - `"ImplementationSpecific"` matching is up to the IngressClass.
     [...]
     identically to Prefix or Exact path types.
     - `"Prefix"` matches based on a URL path prefix split by '/'.
```

```
# Anpassen
nano ingress.yml
```

```
# So sieht das korrigierte .yml file aus.
# Ingress
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: apps-ingress
  annotations:
    ingress.kubernetes.io/rewrite-target: /
spec:
  ingressClassName: nginx
  rules:
  - host: app1.dein-training.de
    http:
      paths:
        - path: /apple
          pathType: Prefix  # <- EINGEFUEGT 
          backend:
            service:
              name: apple-service
              port:
                number: 80
        - path: /banana
          pathType: Prefix  # <- EINGEFUEGT 
          backend:
            service:
              name: banana-service
              port:
                number: 80

```

```
kubectl apply -f .
kubectl get ingress
```



## Reference 

  * https://matthewpalmer.net/kubernetes-app-developer/articles/kubernetes-ingress-guide-nginx-example.html

### Old Version: Find the problem 

```
# Hints 

# 1. Which resources does our version of kubectl support 
# Can we find Ingress as "Kind" here.
kubectl api-ressources 

# 2. Let's see, how the configuration works 
kubectl explain --api-version=networking.k8s.io/v1 ingress.spec.rules.http.paths.backend.service

# now we can adjust our config 
```

## Solution

```
# in kubernetes 1.22.2 - ingress.yml needs to be modified like so.
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: example-ingress
  annotations:
    ingress.kubernetes.io/rewrite-target: /
    # with the ingress controller from helm, you need to set an annotation 
    # old version useClassName instead 
    # otherwice it does not know, which controller to use
    # kubernetes.io/ingress.class: nginx 
spec:
  ingressClassName: nginx
  rules:
  - host: "app12.lab.t3isp.de"
    http:
      paths:
        - path: /apple
          pathType: Prefix
          backend:
            service:
              name: apple-service
              port:
                number: 80
        - path: /banana
          pathType: Prefix
          backend:
            service:
              name: banana-service
              port:
                number: 80                
```
