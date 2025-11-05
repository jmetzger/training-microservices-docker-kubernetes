# Chart my-app erstellen (Gruppenarbeit) 

## Chart erstellen 

```
cd 
mkdir my-charts
cd my-charts
```

```
helm create my-app
``` 

## Chart testen 

```
# nur template rendern 
helm template my-app-release my-app 
# chart trockenlauf (--dry-run) rendern und an den Server (kube-api-server) zur Überprüfung schickt 
helm upgrade --install my-app-release my-app --reset-values --dry-run 
```

## Install helm - chart 

```
# Variante 1:
helm -n my-app-<namenskuerzel> upgrade --install my-app-release my-app --create-namespace --reset-values  
```

```
# Variante 2:
cd my-app
helm -n my-app-<namenskuerzel> upgrade --install my-app-release . --create-namespace --reset-values 
```

```
kubectl -n my-app-<namenskuerzel> get all
kubectl -n my-app-<namenskuerzel> get pods 
```


```
version: 0.1.2
```

```
nano values.yaml
```


```
#### von --_>
service:
  port: 80

### auf --_>
service:
  port: 8080
```

```
helm -n my-app-<namenskuerzel> upgrade --install my-app-<namenskuerzel> . --create-namespace 
```

```
kubectl -n my-app-<namenskuerzel> get all
kubectl -n my-app-<namenskuerzel> get pods 
```

