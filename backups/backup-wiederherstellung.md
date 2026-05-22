# Backup- und Wiederherstellungsstrategien

## Grundkonzepte

Bevor ein Backup-Konzept entworfen wird, müssen zwei zentrale Kennzahlen definiert werden:

| Kennzahl | Bedeutung | Beispiel |
|----------|-----------|---------|
| **RPO** (Recovery Point Objective) | Wie viel Datenverlust ist akzeptabel? | "Maximal 1 Stunde" → Backups stündlich |
| **RTO** (Recovery Time Objective) | Wie lange darf die Wiederherstellung dauern? | "Maximal 4 Stunden" → Restore-Prozess muss in 4h abgeschlossen sein |

**Faustregel:** RPO bestimmt, wie oft gesichert wird. RTO bestimmt, wie schnell und wie automatisiert die Wiederherstellung sein muss.

### Die 3-2-1-Regel

```
3 Kopien der Daten
2 verschiedene Speichermedien / -technologien
1 Kopie außerhalb des Rechenzentrums (off-site)
```

In Kubernetes-Umgebungen bedeutet das typischerweise:
- Primärdaten im Cluster (PVC)
- Backup im Object Storage desselben Providers (z.B. S3, GCS)
- Zweites Backup bei einem anderen Provider oder On-Premises

---

## Was muss gesichert werden?

In einer Kubernetes-Umgebung gibt es vier unabhängige Schichten:

| Schicht | Was | Tools |
|---------|-----|-------|
| **Cluster-Zustand** | etcd: alle Kubernetes-Objekte (Deployments, Services, ConfigMaps, Secrets) | `etcdctl snapshot`, Velero |
| **Persistent Volumes** | Anwendungsdaten (Datenbanken, Upload-Ordner, etc.) | CSI-Snapshots, Velero, Restic |
| **Anwendungsdaten** | Datenbankinhalt auf Anwendungsebene | pg_dump, mysqldump, mongodump |
| **Container Images** | Docker-Images in der Registry | Registry-Replikation, Image-Export |

> **Wichtig:** etcd-Backup und Volume-Backup sind unabhängig. Ein etcd-Restore ohne passende Volumes bringt leere PVCs zurück — und umgekehrt stehen Volumes ohne Kubernetes-Objekte nutzlos herum.

---

## etcd Backup

etcd speichert den kompletten Cluster-Zustand. Fällt etcd aus oder werden Daten korrumpiert,
verliert der Cluster alle Konfigurationen.

### Snapshot erstellen

```
ETCDCTL_API=3 etcdctl snapshot save /backup/etcd-$(date +%Y%m%d-%H%M%S).db \
  --endpoints=https://127.0.0.1:2379 \
  --cacert=/etc/kubernetes/pki/etcd/ca.crt \
  --cert=/etc/kubernetes/pki/etcd/healthcheck-client.crt \
  --key=/etc/kubernetes/pki/etcd/healthcheck-client.key
```

### Snapshot verifizieren

```
ETCDCTL_API=3 etcdctl snapshot status /backup/etcd-20240522-1200.db --write-out=table
```

Erwartete Ausgabe:
```
+----------+----------+------------+------------+
|   HASH   | REVISION | TOTAL KEYS | TOTAL SIZE |
+----------+----------+------------+------------+
| a1b2c3d4 |    12345 |       1234 |    5.2 MB  |
+----------+----------+------------+------------+
```

### Snapshot wiederherstellen

```
# Kubernetes-Komponenten stoppen (Control Plane)
systemctl stop kube-apiserver kube-controller-manager kube-scheduler

# etcd aus Snapshot wiederherstellen
ETCDCTL_API=3 etcdctl snapshot restore /backup/etcd-20240522-1200.db \
  --data-dir=/var/lib/etcd-restore \
  --name=master-1 \
  --initial-cluster=master-1=https://MASTER_IP:2380 \
  --initial-advertise-peer-urls=https://MASTER_IP:2380

# etcd-Datenverzeichnis tauschen
mv /var/lib/etcd /var/lib/etcd-old
mv /var/lib/etcd-restore /var/lib/etcd

# Dienste neu starten
systemctl start kube-apiserver kube-controller-manager kube-scheduler etcd
```

> **Warnung:** Ein etcd-Restore setzt den Cluster auf den Stand des Snapshots zurück.
> Alle Änderungen nach dem Snapshot-Zeitpunkt gehen verloren.

---

## Velero — Kubernetes-natives Backup

Velero ist das meistgenutzte Open-Source-Tool für Kubernetes-Backups.
Es sichert Kubernetes-Objekte und — über CSI-Snapshots oder Restic — auch Volumes.

### Architektur

```
kubectl apply -f ...
       │
       ▼
   Velero Server (läuft im Cluster)
       │
       ├──► Kubernetes API → sichert Objekte als JSON in S3/GCS/Azure Blob
       │
       └──► CSI Snapshots / Restic → sichert Volume-Inhalte
```

### Installation (mit MinIO als S3-Speicher)

```
# Velero CLI installieren
wget https://github.com/vmware-tanzu/velero/releases/latest/download/velero-linux-amd64.tar.gz
tar -xf velero-linux-amd64.tar.gz
mv velero-linux-amd64/velero /usr/local/bin/

# Velero im Cluster installieren (Beispiel mit S3)
velero install \
  --provider aws \
  --plugins velero/velero-plugin-for-aws:v1.9.0 \
  --bucket velero-backups \
  --backup-location-config region=eu-central-1 \
  --use-node-agent \
  --default-volumes-to-fs-backup
```

### Backup erstellen

```
# Gesamten Cluster sichern
velero backup create cluster-backup-$(date +%Y%m%d)

# Einzelnen Namespace sichern
velero backup create shop-backup --include-namespaces production-shop

# Backup mit Zeitplan (täglich um 2:00 Uhr)
velero schedule create daily-backup --schedule="0 2 * * *" --include-namespaces production
```

### Backup-Status prüfen

```
velero backup get
velero backup describe cluster-backup-20240522
velero backup logs cluster-backup-20240522
```

Erwartete Ausgabe (backup describe):
```
Name:         cluster-backup-20240522
Namespace:    velero
...
Phase:        Completed
...
Included Namespaces:  *
Total items to be backed up:  312
Items backed up:              312
```

### Wiederherstellen

```
# Vollständige Wiederherstellung
velero restore create --from-backup cluster-backup-20240522

# Nur einen Namespace wiederherstellen
velero restore create --from-backup cluster-backup-20240522 \
  --include-namespaces production-shop

# Restore-Status prüfen
velero restore get
velero restore describe <restore-name>
```

---

## CSI Volume Snapshots

CSI Snapshots sind der standardisierte Weg, PersistentVolumes direkt auf Storage-Ebene
zu sichern — schnell, konsistent, ohne Datenkopie über das Netzwerk.

### VolumeSnapshot erstellen

```
# vi snapshot.yml
apiVersion: snapshot.storage.k8s.io/v1
kind: VolumeSnapshot
metadata:
  name: postgres-snapshot-20240522
spec:
  volumeSnapshotClassName: csi-aws-vsc
  source:
    persistentVolumeClaimName: postgres-data
```

```
kubectl apply -f snapshot.yml -n production
kubectl get volumesnapshot -n production
```

### PVC aus Snapshot wiederherstellen

```
# vi pvc-from-snapshot.yml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: postgres-data-restored
spec:
  dataSource:
    name: postgres-snapshot-20240522
    kind: VolumeSnapshot
    apiGroup: snapshot.storage.k8s.io
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 20Gi
```

---

## Datenbankbackups auf Anwendungsebene

CSI-Snapshots und Velero sichern Dateien — aber eine laufende Datenbank kann in einem
inkonsistenten Zustand sein. Für konsistente Backups braucht es datenbankspezifische Tools.

### PostgreSQL (pg_dump)

```
# Backup als Job im Cluster ausführen
kubectl run pg-backup --restart=Never --rm -i \
  --image=postgres:16 \
  --env="PGPASSWORD=geheim" \
  -- pg_dump -h postgres-service -U myuser mydatabase \
  > backup-$(date +%Y%m%d).sql
```

Oder als CronJob für automatisches Backup:

```
# vi 01-pg-backup-cronjob.yml
apiVersion: batch/v1
kind: CronJob
metadata:
  name: postgres-backup
spec:
  schedule: "0 3 * * *"
  jobTemplate:
    spec:
      template:
        spec:
          restartPolicy: OnFailure
          containers:
          - name: pg-backup
            image: postgres:16
            env:
            - name: PGPASSWORD
              valueFrom:
                secretKeyRef:
                  name: postgres-secret
                  key: password
            command:
            - /bin/sh
            - -c
            - |
              pg_dump -h postgres-service -U myuser mydatabase \
              | gzip > /backup/dump-$(date +%Y%m%d-%H%M).sql.gz
            volumeMounts:
            - name: backup-storage
              mountPath: /backup
          volumes:
          - name: backup-storage
            persistentVolumeClaim:
              claimName: backup-pvc
```

### MySQL / MariaDB

```
kubectl run mysql-backup --restart=Never --rm -i \
  --image=mysql:8 \
  -- mysqldump -h mysql-service -u root -pgeheim --all-databases \
  > backup-$(date +%Y%m%d).sql
```

### Restore PostgreSQL

```
kubectl run pg-restore --restart=Never --rm -i \
  --image=postgres:16 \
  --env="PGPASSWORD=geheim" \
  -- psql -h postgres-service -U myuser mydatabase \
  < backup-20240522.sql
```

---

## Wiederherstellungsszenarien

### Szenario 1: Einzelne Ressource versehentlich gelöscht

```
# Velero Backup vorhanden → gezielter Restore
velero restore create --from-backup daily-backup-20240522 \
  --include-resources deployments \
  --selector app=payment-service

# Alternativ: aus Git (wenn GitOps genutzt wird)
git checkout HEAD~1 -- kubernetes/payment/deployment.yml
kubectl apply -f kubernetes/payment/deployment.yml
```

### Szenario 2: Namespace komplett gelöscht

```
velero restore create --from-backup daily-backup-20240522 \
  --include-namespaces production-shop
```

### Szenario 3: Cluster-Totalausfall (neuer Cluster)

```
# 1. Neuen Cluster aufsetzen (Kubernetes installieren)
# 2. Velero im neuen Cluster installieren
# 3. Auf das gleiche Backup-Ziel (S3) zeigen
velero install \
  --provider aws \
  --bucket velero-backups \
  --backup-location-config region=eu-central-1

# 4. Backups werden automatisch erkannt
velero backup get

# 5. Vollständig wiederherstellen
velero restore create --from-backup cluster-backup-20240522
```

### Szenario 4: Datenkorruption (Anwendungsebene)

```
# Datenbank stoppen / in Wartungsmodus
kubectl scale deployment postgres --replicas=0 -n production

# Altes Volume löschen und aus Snapshot wiederherstellen
kubectl delete pvc postgres-data -n production
kubectl apply -f pvc-from-snapshot.yml -n production

# Datenbank starten
kubectl scale deployment postgres --replicas=1 -n production
```

---

## Backup-Strategie im Vergleich

| Methode | Was wird gesichert | Konsistenz | Geschwindigkeit | Granularität |
|---------|-------------------|------------|-----------------|--------------|
| **etcd Snapshot** | Kubernetes-Objekte | Hoch | Schnell | Cluster-weit |
| **Velero (Objekte)** | Kubernetes-Objekte | Hoch | Schnell | Namespace/Label |
| **Velero + Restic** | Objekte + Volumes | Datei-konsistent | Langsam (Datei-Kopie) | Namespace/Label |
| **CSI Snapshot** | Volume-Inhalt | Storage-konsistent | Sehr schnell | Pro PVC |
| **pg_dump / mysqldump** | Datenbankinhalt | Transaktions-konsistent | Mittel | Pro DB/Tabelle |

**Empfehlung für Produktivumgebungen:**
- Velero für Kubernetes-Objekte (täglich, stündlich für kritische Namespaces)
- CSI Snapshots für Volumes (stündlich)
- pg_dump/mysqldump für Datenbanken (täglich, vor Migrationen)
- etcd Snapshot vor jedem Cluster-Upgrade

---

## Disaster Recovery Checkliste

```
Vor einem Ausfall (Vorbereitung):
□ Backup-Strategie dokumentiert (RPO/RTO definiert)
□ Backups laufen automatisch (CronJob / Velero Schedule)
□ Backup-Ziel außerhalb des Clusters (S3, off-site)
□ Restore-Prozess dokumentiert und getestet
□ Zugangsdaten für Recovery sicher verwahrt (kein Single Point of Failure)
□ Monitoring und Alerting für fehlgeschlagene Backups

Nach einem Ausfall (Recovery):
□ Schadensausmaß einschätzen (was ist betroffen?)
□ Passendes Backup-Datum auswählen (letztes konsistentes Backup)
□ Recovery-Kommunikation starten (Stakeholder informieren)
□ Restore in Test-Umgebung zuerst (wenn möglich)
□ Restore in Produktion
□ Datenintegrität prüfen (Anwendung starten, Testtransaktionen)
□ Ursache analysieren und Maßnahmen ableiten (Post-Mortem)
```

---

## Zusammenfassung

Kubernetes-Backups bestehen aus mehreren unabhängigen Schichten — wer nur etcd sichert,
verliert im Ernstfall alle Anwendungsdaten. Wer nur Volumes sichert, kann den Cluster
nicht wiederherstellen.

Eine vollständige Backup-Strategie kombiniert:
1. **Velero** für Kubernetes-Objekte (alle Namespaces, täglich)
2. **CSI Snapshots** für Persistent Volumes (stündlich)
3. **Datenbankdumps** für transaktionskonsistente Backups (täglich)
4. **etcd Snapshots** vor Cluster-Upgrades und Änderungen an der Control Plane

Entscheidend ist nicht nur, dass Backups laufen — sondern dass Restores regelmäßig
getestet werden. Ein nicht getestetes Backup ist kein Backup.
