# kubectl aufsetzen und konfigurieren

## config einrichten 

```
# als unpriviligierter Benutzer z.B. kurs
cd
mkdir -p .kube
cd .kube
```

```
cp /tmp/config config
ls -la
```

```
kubectl cluster-info
```

## Arbeitsbereich konfigurieren 

```
kubectl create ns jochen
kubectl get ns
kubectl config set-context --current --namespace jochen
```
