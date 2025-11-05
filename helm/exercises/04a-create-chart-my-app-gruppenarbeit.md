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

## Fehler bei ocp debuggen 

```
kubectl -n my-app-<namenskuerzel> get pods
```
<img width="1716" height="117" alt="image" src="https://github.com/user-attachments/assets/ebbfe072-1015-4563-94b9-4aa2b4bd6609" />

```
# Wie debuggen -> Schritt 1:
kubectl -n my-app-<namenskuerzel> describe po my-app-release-7d9bd79cb7-9gbbd
```
<img width="1897" height="138" alt="image" src="https://github.com/user-attachments/assets/25fcf6e6-34ae-455d-a225-fc1cbf7baaf4" />

```
# Wenn Schritt 1 kein gesichertes Ergebnis liefert.
# Wie debuggen -> Schritt 2: Logs
kubectl -n my-app-jm2 logs my-app-release-7d9bd79cb7-9gbbd
```

<img width="1893" height="120" alt="image" src="https://github.com/user-attachments/assets/ec4477a6-703e-43fb-83d8-a49ad8187498" />


```
# Schritt 3: yaml von pod anschauen, warum tritt der Fehler auf 
kubectl -n my-app-<namenskuerzel> get pods -o yaml
```

```
# Dieser Block ist dafür verantwortlich, dass keine Pods als root ausgeführt werden, können. nginx will aber unter root laufen (bzw. muss)
```
<img width="929" height="419" alt="image" src="https://github.com/user-attachments/assets/2fa1974e-29e8-43d0-a071-5daf54a7292d" />

## Image verwenden was auch als nicht-root läuft 

```
cd
cd my-charts
nano my-app/values.yaml
```

```
# image Zeile ändern
# von ->
image:
  repository: nginx

# in ->
image:
  repository: nginxinc/nginx-unprivileged
```

```
# Auch wichtig version in Chart.yaml um 1 erhöhen z.B. 0.1.0 -> 0.1.1
```

```
helm -n my-app-<namenskuerzel> upgrade --install my-app-release my-app --create-namespace --reset-values 
```

```
kubectl -n my-app-<namenskuerzel> get all
kubectl -n my-app-<namenskuerzel> get pods 
```

```
# Schlägt fehl, weil readiness auf 80 abfragt, aber dort nichts läuft
```

## Port anpassen und version (Chart-Version) hochziehen (damit auch readinessCheck geht) 

```
cd my-app
nano Chart.yaml
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

