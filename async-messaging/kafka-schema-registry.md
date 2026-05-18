# Kafka Schema Registry — Avro und Schema-Evolution

## Hintergrund

Die Confluent Schema Registry zentralisiert die Verwaltung von Avro-Schemas.
Statt das Schema in jedem Producer/Consumer zu hardcoden, wird es einmalig registriert.
Die Registry erzwingt Kompatibilitaetsregeln und verhindert Breaking Changes.

```
Producer  →  Schema Registry  →  Kafka Topic  →  Consumer
           (Schema-ID + Daten)                  (holt Schema per ID)
```

---

## Teil 1 — Zentraler Setup (Trainer)

> Dieser Teil laeuft **einmalig**. Teilnehmer koennen beobachten, muessen aber nicht selbst ausfuehren.

### 1.1 Namespace anlegen

```
kubectl create namespace kafka
```

### 1.2 Kafka deployen (apache/kafka:3.9.0, KRaft-Mode)

```
kubectl apply -n kafka -f - <<'EOF'
---
apiVersion: v1
kind: Service
metadata:
  name: kafka-controller-headless
  namespace: kafka
spec:
  clusterIP: None
  ports:
  - name: client
    port: 9092
  - name: controller
    port: 9093
  selector:
    app.kubernetes.io/name: kafka
---
apiVersion: v1
kind: Service
metadata:
  name: kafka
  namespace: kafka
spec:
  ports:
  - name: client
    port: 9092
  selector:
    app.kubernetes.io/name: kafka
---
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: kafka-controller
  namespace: kafka
spec:
  serviceName: kafka-controller-headless
  replicas: 3
  selector:
    matchLabels:
      app.kubernetes.io/name: kafka
  template:
    metadata:
      labels:
        app.kubernetes.io/name: kafka
    spec:
      securityContext:
        fsGroup: 1000
        runAsUser: 1000
      containers:
      - name: kafka
        image: apache/kafka:3.9.0
        command:
        - /bin/bash
        - -c
        - |
          NODE_ID=${HOSTNAME##*-}
          HOST="${HOSTNAME}.kafka-controller-headless.kafka.svc.cluster.local"
          export KAFKA_NODE_ID=$NODE_ID
          export KAFKA_ADVERTISED_LISTENERS="PLAINTEXT://${HOST}:9092,CONTROLLER://${HOST}:9093"
          mkdir -p /var/lib/kafka/data/kafka-logs
          exec /etc/kafka/docker/run
        env:
        - name: CLUSTER_ID
          value: "MkU3OEVBNTcwNTJENDM2Qk"
        - name: KAFKA_PROCESS_ROLES
          value: "broker,controller"
        - name: KAFKA_CONTROLLER_QUORUM_VOTERS
          value: "0@kafka-controller-0.kafka-controller-headless.kafka.svc.cluster.local:9093,1@kafka-controller-1.kafka-controller-headless.kafka.svc.cluster.local:9093,2@kafka-controller-2.kafka-controller-headless.kafka.svc.cluster.local:9093"
        - name: KAFKA_LISTENERS
          value: "PLAINTEXT://0.0.0.0:9092,CONTROLLER://0.0.0.0:9093"
        - name: KAFKA_CONTROLLER_LISTENER_NAMES
          value: "CONTROLLER"
        - name: KAFKA_INTER_BROKER_LISTENER_NAME
          value: "PLAINTEXT"
        - name: KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR
          value: "3"
        - name: KAFKA_DEFAULT_REPLICATION_FACTOR
          value: "3"
        - name: KAFKA_MIN_INSYNC_REPLICAS
          value: "2"
        - name: KAFKA_LOG_DIRS
          value: "/var/lib/kafka/data/kafka-logs"
        - name: PATH
          value: "/opt/kafka/bin:/opt/java/openjdk/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin"
        ports:
        - containerPort: 9092
          name: client
        - containerPort: 9093
          name: controller
        volumeMounts:
        - name: data
          mountPath: /var/lib/kafka/data
  volumeClaimTemplates:
  - metadata:
      name: data
    spec:
      accessModes: ["ReadWriteOnce"]
      resources:
        requests:
          storage: 4Gi
EOF
```

```
kubectl -n kafka rollout status statefulset/kafka-controller
```

> **Hinweis:** KRaft-Mode (kein Zookeeper), 3 Combined-Controller+Broker Nodes.

### 1.3 Schema Registry deployen (confluentinc/cp-schema-registry:7.7.1)

```
kubectl apply -n kafka -f - <<'EOF'
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: schema-registry
  namespace: kafka
spec:
  replicas: 1
  selector:
    matchLabels:
      app: schema-registry
  template:
    metadata:
      labels:
        app: schema-registry
    spec:
      enableServiceLinks: false
      containers:
      - name: schema-registry
        image: confluentinc/cp-schema-registry:7.7.1
        env:
        - name: SCHEMA_REGISTRY_HOST_NAME
          value: "schema-registry"
        - name: SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS
          value: "PLAINTEXT://kafka.kafka.svc.cluster.local:9092"
        - name: SCHEMA_REGISTRY_LISTENERS
          value: "http://0.0.0.0:8081"
        - name: SCHEMA_REGISTRY_KAFKASTORE_TOPIC_REPLICATION_FACTOR
          value: "3"
        ports:
        - containerPort: 8081
          name: http
---
apiVersion: v1
kind: Service
metadata:
  name: schema-registry
  namespace: kafka
spec:
  selector:
    app: schema-registry
  ports:
  - port: 8081
    name: http
    targetPort: 8081
EOF
```

### 1.4 Verifikation

```
kubectl -n kafka get pods
```

Erwartete Ausgabe:

```
NAME                               READY   STATUS    RESTARTS   AGE
kafka-controller-0                 1/1     Running   0          2m
kafka-controller-1                 1/1     Running   0          2m
kafka-controller-2                 1/1     Running   0          2m
schema-registry-...                1/1     Running   0          1m
```

```
kubectl -n kafka run verify --image=curlimages/curl --restart=Never --rm -i -- \
  curl -s http://schema-registry.kafka.svc.cluster.local:8081/subjects
```

Erwartete Ausgabe: `[]`

---

## Teil 2 — Pro Teilnehmer

> **Setze als Erstes deine TLN-Nummer:**
>
> ```
> export TLN_NR=1   # <- deine Nummer
> export NS=tln${TLN_NR}
> export TOPIC=tln${TLN_NR}-orders
> ```

### 2.1 Topic anlegen

```
kubectl -n kafka exec -it kafka-controller-0 -- \
  kafka-topics.sh --bootstrap-server localhost:9092 \
    --create --topic ${TOPIC} \
    --partitions 3 --replication-factor 2
```

Erwartete Ausgabe: `Created topic tln1-orders.`

### 2.2 Avro-Schema registrieren

```
kubectl -n ${NS} run schemareg --image=curlimages/curl --restart=Never -i --rm -- \
  curl -s -X POST \
    -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    --data '{"schema": "{\"type\":\"record\",\"name\":\"Order\",\"namespace\":\"de.tln'${TLN_NR}'\",\"fields\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"product\",\"type\":\"string\"},{\"name\":\"quantity\",\"type\":\"int\"}]}"}' \
    http://schema-registry.kafka.svc.cluster.local:8081/subjects/${TOPIC}-value/versions
```

Erwartete Ausgabe: `{"id":1}`

**Verifikation:**

```
kubectl -n ${NS} run verify --image=curlimages/curl --restart=Never -i --rm -- \
  curl -s http://schema-registry.kafka.svc.cluster.local:8081/subjects/${TOPIC}-value/versions/latest
```

### 2.3 Producer-Pod (Avro Console Producer)

```
kubectl -n ${NS} run producer -it --rm --restart=Never \
  --image=confluentinc/cp-schema-registry:7.7.1 -- bash
```

Im Container:

```
kafka-avro-console-producer \
  --bootstrap-server kafka.kafka.svc.cluster.local:9092 \
  --topic tln${TLN_NR}-orders \
  --property schema.registry.url=http://schema-registry.kafka.svc.cluster.local:8081 \
  --property value.schema='{"type":"record","name":"Order","namespace":"de.tln'${TLN_NR}'","fields":[{"name":"id","type":"string"},{"name":"product","type":"string"},{"name":"quantity","type":"int"}]}'
```

Eingabe (jede Zeile = eine Nachricht):

```
{"id":"o-1","product":"Schraubenzieher","quantity":2}
{"id":"o-2","product":"Hammer","quantity":1}
```

Strg+D zum Beenden.

### 2.4 Consumer-Pod

In **neuem Terminal**:

```
export TLN_NR=1
export NS=tln${TLN_NR}

kubectl -n ${NS} run consumer -it --rm --restart=Never \
  --image=confluentinc/cp-schema-registry:7.7.1 -- bash
```

Im Container:

```
kafka-avro-console-consumer \
  --bootstrap-server kafka.kafka.svc.cluster.local:9092 \
  --topic tln${TLN_NR}-orders \
  --from-beginning \
  --property schema.registry.url=http://schema-registry.kafka.svc.cluster.local:8081
```

**Erwartete Ausgabe:**

```
{"id":"o-1","product":"Schraubenzieher","quantity":2}
{"id":"o-2","product":"Hammer","quantity":1}
```

---

## Teil 3 — Schema-Evolution (Bonus)

Fuege ein optionales Feld hinzu (rueckwaertskompatibel):

```
kubectl -n ${NS} run schemareg2 --image=curlimages/curl --restart=Never -i --rm -- \
  curl -s -X POST \
    -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    --data '{"schema": "{\"type\":\"record\",\"name\":\"Order\",\"namespace\":\"de.tln'${TLN_NR}'\",\"fields\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"product\",\"type\":\"string\"},{\"name\":\"quantity\",\"type\":\"int\"},{\"name\":\"customer\",\"type\":[\"null\",\"string\"],\"default\":null}]}"}' \
    http://schema-registry.kafka.svc.cluster.local:8081/subjects/${TOPIC}-value/versions
```

Erwartete Ausgabe: `{"id":2}` — Versionsnummer steigt auf 2.

Alle Versionen listen:

```
kubectl -n ${NS} run verify --image=curlimages/curl --restart=Never -i --rm -- \
  curl -s http://schema-registry.kafka.svc.cluster.local:8081/subjects/${TOPIC}-value/versions
```

Erwartete Ausgabe: `[1,2]`

### Inkompatibles Schema testen

```
kubectl -n ${NS} run schemareg-bad --image=curlimages/curl --restart=Never -i --rm -- \
  curl -s -X POST \
    -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    --data '{"schema": "{\"type\":\"record\",\"name\":\"Order\",\"fields\":[{\"name\":\"id\",\"type\":\"int\"}]}"}' \
    http://schema-registry.kafka.svc.cluster.local:8081/subjects/${TOPIC}-value/versions
```

**Erwartete Ausgabe:** HTTP 409 — `Schema being registered is incompatible with an earlier schema`

---

## Teil 4 — Cleanup (pro Teilnehmer)

```
kubectl -n kafka exec kafka-controller-0 -- \
  kafka-topics.sh --bootstrap-server localhost:9092 \
    --delete --topic tln${TLN_NR}-orders

kubectl -n ${NS} run cleanup --image=curlimages/curl --restart=Never -i --rm -- \
  curl -s -X DELETE \
    http://schema-registry.kafka.svc.cluster.local:8081/subjects/tln${TLN_NR}-orders-value
```

---

## Diskussionsfragen

1. Warum ist Topic-Naming-Prefix (`tln${TLN_NR}-`) bei geteiltem Cluster sinnvoll? Welche Alternative gäbe es mit ACLs?
2. Welche Kompatibilitaetsmodi gibt es in der Schema Registry (`BACKWARD`, `FORWARD`, `FULL`, `NONE`) und wann nutzt man welchen?
3. Warum wird `${TOPIC}-value` als Subject-Name verwendet? (TopicNameStrategy vs. RecordNameStrategy)
4. Wie wuerdest du Producer-Isolation per Kafka-ACL pro `tln${TLN_NR}` umsetzen?

---

## Troubleshooting

| Symptom | Ursache | Fix |
|---|---|---|
| Producer haengt bei „Schema not found" | Schema-Registry-URL falsch | Service-DNS pruefen: `schema-registry.kafka.svc.cluster.local:8081` |
| `LEADER_NOT_AVAILABLE` | Broker noch nicht ready | `kubectl -n kafka get pods` — warten bis alle 3 Controller `1/1` |
| `409 Conflict` beim Schema-POST | Inkompatibel zu Vorversion | Kompatibilitaetsmodus pruefen: `GET /config/${SUBJECT}` |
| Topic-Erstellung „replication factor: 2 > 1" | Nur 1 Broker laeuft | Replication-Factor auf 1 reduzieren oder Controller-Replicas pruefen |
| Schema Registry CrashLoop | Kubernetes injiziert `SCHEMA_REGISTRY_PORT` | `enableServiceLinks: false` im Pod-Spec setzen |
