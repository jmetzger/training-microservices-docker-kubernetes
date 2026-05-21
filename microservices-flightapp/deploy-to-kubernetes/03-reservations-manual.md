# ms-reservations: Manuelles Deployment auf Kubernetes

## Hintergrund

In dieser Uebung deployen wir nur den `ms-reservations` Microservice manuell auf Kubernetes.
Der Service benoetigt Redis als Datenspeicher — beides wird ausgerollt.

Am Ende testen wir die Erreichbarkeit des Services von innerhalb des Clusters mit einem
temporaeren busybox-Pod.

## Schritt 1: Verzeichnis anlegen

```
cd
mkdir -p manifests/reservations
cd manifests/reservations
```

## Schritt 2: ConfigMap fuer Redis anlegen

```
nano 01-redis-cm.yml
```

```
apiVersion: v1
kind: ConfigMap
metadata:
  name: redis-cm
data:
  REDIS_HOST: "ms-reservations-redis"
  REDIS_PORT: "6379"
  REDIS_DB: "0"
  REDIS_PWD: "4n_ins3cure_P4ss"
  redis-config: |
    appendonly yes
```

```
kubectl apply -f . -n reservations-<dein-name>
```

## Schritt 3: Redis Deployment anlegen

```
nano 02-redis-deploy.yml
```

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ms-reservations-redis
spec:
  replicas: 1
  selector:
    matchLabels:
      storage: redis
  strategy:
    type: Recreate
  template:
    metadata:
      labels:
        storage: redis
    spec:
      containers:
        - name: ms-reservations-redis
          image: redis:6-alpine
          command:
            - redis-server
            - /usr/local/etc/redis/redis.conf
            - --requirepass
            - 4n_ins3cure_P4ss
          env:
            - name: REDIS_REPLICATION_MODE
              value: master
          ports:
            - containerPort: 6379
          volumeMounts:
            - mountPath: /data
              name: data
            - mountPath: /usr/local/etc/redis/redis.conf
              name: config
      volumes:
        - name: data
          emptyDir: {}
        - name: config
          configMap:
            name: redis-cm
            items:
              - key: redis-config
                path: redis.conf
```

```
kubectl apply -f . -n reservations-<dein-name>
kubectl get pods -n reservations-<dein-name>
```

## Schritt 4: Redis Service anlegen

```
nano 03-redis-svc.yml
```

```
apiVersion: v1
kind: Service
metadata:
  name: ms-reservations-redis
spec:
  type: ClusterIP
  ports:
    - name: redis
      port: 6379
  selector:
    storage: redis
```

```
kubectl apply -f . -n reservations-<dein-name>
```

## Schritt 5: Reservations Deployment anlegen

```
nano 04-reservations-deploy.yml
```

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ms-reservations
spec:
  replicas: 1
  selector:
    matchLabels:
      app: reservations
  template:
    metadata:
      labels:
        app: reservations
    spec:
      containers:
        - name: ms-reservations
          image: dockertrainereu/reservations-jm:v1.1
          env:
            - name: FLASK_ENV
              value: development
            - name: REDIS_DB
              valueFrom:
                configMapKeyRef:
                  name: redis-cm
                  key: REDIS_DB
            - name: REDIS_HOST
              valueFrom:
                configMapKeyRef:
                  name: redis-cm
                  key: REDIS_HOST
            - name: REDIS_PORT
              valueFrom:
                configMapKeyRef:
                  name: redis-cm
                  key: REDIS_PORT
            - name: REDIS_PWD
              valueFrom:
                configMapKeyRef:
                  name: redis-cm
                  key: REDIS_PWD
          ports:
            - containerPort: 8000
```

```
kubectl apply -f . -n reservations-<dein-name>
kubectl get pods -n reservations-<dein-name>
```

## Schritt 6: Status pruefen

```
kubectl get pods -n reservations-<dein-name>
```

Erwartete Ausgabe (beide Pods Running):
```
NAME                                    READY   STATUS    RESTARTS   AGE
ms-reservations-xxxx                    1/1     Running   0          1m
ms-reservations-redis-xxxx              1/1     Running   0          2m
```

## Aufgabe: Service fuer ms-reservations erstellen

Das Deployment laeuft — aber von aussen (auch innerhalb des Clusters) ist der Pod
noch nicht erreichbar. Erstelle dafuer eine Datei `05-reservations-svc.yml`.

Hinweise:
- Typ: `ClusterIP`
- Port der App: `8000`
- Der `selector` muss zum Label im Deployment passen

Wende danach alle Manifests erneut an:

```
kubectl apply -f . -n reservations-<dein-name>
kubectl get svc -n reservations-<dein-name>
```

Teste anschliessend mit einem busybox-Pod:

```
kubectl run -it --rm busybox --image=busybox --restart=Never \
  -n reservations-<dein-name> \
  -- wget -O- http://ms-reservations:8000/reservations
```

Ziel: Die Ausgabe zeigt `{}` — der Service leitet den Request an den Pod weiter.

## Zusatzaufgabe: Image-Version aktualisieren

Wenn der Service funktioniert: Aendere den Image-Tag in `04-reservations-deploy.yml`
von `v1.1` auf `v17` und beobachte den Rolling Update:

```
kubectl apply -f . -n reservations-<dein-name>
kubectl rollout status deployment ms-reservations -n reservations-<dein-name>
kubectl get pods -n reservations-<dein-name>
```

## Aufraeumen

```
kubectl delete namespace reservations-<dein-name>
```
