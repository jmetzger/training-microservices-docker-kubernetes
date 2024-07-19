# Chart erstellen 

## Schritt 1: Chart erstellen 

```
helm create beispielapp
```

## Schritt 2: Aufräumen 

```
cd beispielapp/templates
rm -fR tests
cd ..
mv templates templates.bkup
mkdir templates 
```

## Schritt 3: Bestücken 

```
cd
cd manifests
cp -a abi/* beispielapp
cd /tmp
cp -a storage-do/* ~/manifests/beispielapp
```

```
cd
cd manifests
cd beispielapp
cd templates
```

```
cd ..
mv apple.yml banana.yml 01-pvc.yaml 02-configmap.yml 03-deploy.yml templates
```

