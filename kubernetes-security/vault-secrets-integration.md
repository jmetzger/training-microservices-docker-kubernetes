# Secrets in Kubernetes mit HashiCorp Vault

![Übersicht: Secrets in Kubernetes mit HashiCorp Vault](/images/vault-secrets-overview.svg)

## Das Problem: Warum reichen native Kubernetes Secrets nicht aus?

Kubernetes hat ein eingebautes `Secret`-Objekt. Auf den ersten Blick wirkt es wie eine
Lösung — aber es hat grundlegende Schwächen:

### 1. Secrets sind nur Base64-kodiert, nicht verschlüsselt

```
kubectl get secret my-secret -o yaml
```

```yaml
apiVersion: v1
kind: Secret
data:
  password: c3VwZXJzZWNyZXQ=   # das ist nur Base64 — kein echter Schutz
```

`echo "c3VwZXJzZWNyZXQ=" | base64 -d` gibt sofort `supersecret` zurück.
**Wer Lesezugriff auf die API hat, kann alle Secrets lesen.**

### 2. Secrets liegen im Klartext in etcd

Die Kubernetes-Datenbank (etcd) speichert Secrets standardmäßig unverschlüsselt.
Wer Zugriff auf das etcd-Backup hat, hat alle Secrets.

### 3. Kein Audit-Trail, keine Rotation, keine Ablaufzeiten

Native Kubernetes Secrets...
- wissen nicht, wer sie wann gelesen hat
- rotieren sich nicht automatisch
- laufen nicht ab
- haben keine Zugriffsrichtlinien pro Team oder Applikation

### 4. Secrets landen im Git — der klassische Fehler

Entwickler committen versehentlich `.env`-Dateien oder YAML-Manifeste mit Passwörtern.
Selbst nach dem Löschen sind sie in der Git-Historie.

---

## Was ist HashiCorp Vault?

HashiCorp Vault ist ein dediziertes Secret-Management-System. Es löst genau die
Probleme oben:

| Funktion | Beschreibung |
|----------|-------------|
| **Verschlüsselung** | Alle Secrets werden verschlüsselt gespeichert (AES-256) |
| **Audit-Log** | Jeder Zugriff wird protokolliert: Wer, wann, welches Secret |
| **Dynamic Secrets** | Vault kann Passwörter on-demand generieren und automatisch rotieren |
| **Lease & TTL** | Secrets laufen automatisch ab und werden erneuert |
| **Policies** | Feingranulare Zugriffsregeln: App A darf nur Secret X lesen |
| **Auth-Methoden** | Kubernetes-Pods authentifizieren sich über ihren ServiceAccount |

### Warum ist ein Vault-Cluster (HA) wichtig?

Ein einzelner Vault-Server ist ein **Single Point of Failure**. Wenn er ausfällt,
können keine Secrets mehr abgerufen werden — Pods starten nicht, Deployments schlagen
fehl. In Produktion ist Vault deshalb immer als Cluster betrieben:

![HashiCorp Vault HA Cluster](/images/vault-ha-cluster.svg)

**Raft (Integrated Storage)** ist heute der empfohlene Weg — Vault managed seinen
eigenen Cluster ohne externes Storage-Backend.

**Wichtig:** Vault muss nach einem Neustart manuell **unsealт** werden (oder über
Auto-Unseal z.B. mit AWS KMS / Azure Key Vault). Ein versiegelter Vault antwortet
auf keine Anfragen.

---

## Die 3 Wege: Secrets aus Vault in Kubernetes

### Weg 1: Vault Agent Injector (Sidecar)

**Wie es funktioniert:**

Kubernetes startet automatisch einen zweiten Container (Sidecar) in jedem Pod,
der Annotations hat. Dieser `vault-agent`-Container holt die Secrets aus Vault
und schreibt sie als Datei in ein geteiltes Volume im Pod.

![Vault Agent Injector](/images/vault-agent-injector.svg)

**Konfiguration per Annotations:**

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-app
spec:
  template:
    metadata:
      annotations:
        vault.hashicorp.com/agent-inject: "true"
        vault.hashicorp.com/role: "my-app-role"
        vault.hashicorp.com/agent-inject-secret-config: "secret/data/my-app/config"
    spec:
      serviceAccountName: my-app
      containers:
      - name: my-app
        image: my-app:1.0
        # Secret liegt unter: /vault/secrets/config
```

**Vorteile:**
- Kein Kubernetes Secret wird erstellt (Secret nie in etcd)
- Automatische Rotation (Vault Agent hält Lease aufrecht)
- Weit verbreitet, viel Dokumentation

**Nachteile:**
- Jeder Pod bekommt einen zusätzlichen Container (Ressourcenverbrauch)
- App muss Secrets als Datei lesen (nicht als Env-Variable direkt)
- Annotations im Deployment — koppelt App an Vault

---

### Weg 2: Vault CSI Provider

**Was ist CSI?**
CSI steht für **Container Storage Interface** — ein Standard, über den Kubernetes
externe Storage-Systeme anbindet. Der **Secrets Store CSI Driver** erweitert das:
Secrets aus externen Systemen (Vault, AWS Secrets Manager, Azure Key Vault) werden
wie Volumes in den Pod gemountet.

**Wie es funktioniert:**

![Vault CSI Provider](/images/vault-csi-provider.svg)

**SecretProviderClass definieren:**

```yaml
apiVersion: secrets-store.csi.x-k8s.io/v1
kind: SecretProviderClass
metadata:
  name: vault-db-creds
spec:
  provider: vault
  parameters:
    vaultAddress: "https://vault.example.com"
    roleName: "my-app-role"
    objects: |
      - objectName: "password"
        secretPath: "secret/data/my-app/db"
        secretKey: "password"
```

**Im Deployment:**

```yaml
spec:
  containers:
  - name: my-app
    volumeMounts:
    - name: secrets
      mountPath: "/mnt/secrets"
      readOnly: true
  volumes:
  - name: secrets
    csi:
      driver: secrets-store.csi.k8s.io
      readOnly: true
      volumeAttributes:
        secretProviderClass: "vault-db-creds"
```

**Vorteile:**
- Kein Sidecar-Container nötig (leichtgewichtiger als Weg 1)
- Funktioniert mit mehreren Secret-Backends (Vault, AWS, Azure) über dieselbe API
- Kann optional auch ein Kubernetes Secret anlegen (für Env-Variablen)

**Nachteile:**
- CSI Driver muss als DaemonSet auf allen Nodes laufen
- Etwas komplexere Ersteinrichtung
- Secret steht erst beim Pod-Start bereit (kein dynamisches Nachladen)

---

### Weg 3: External Secrets Operator (ESO)

**Was ist der External Secrets Operator?**

Der ESO ist ein Kubernetes-Operator (ein Controller, der im Cluster läuft und
`ExternalSecret`-Objekte beobachtet). Er holt Secrets aus Vault und erstellt daraus
ganz normale Kubernetes `Secret`-Objekte — die App merkt gar nicht, dass Vault
im Spiel ist.

**Wie es funktioniert:**

![External Secrets Operator](/images/vault-eso.svg)

**ExternalSecret definieren:**

```yaml
apiVersion: external-secrets.io/v1beta1
kind: ExternalSecret
metadata:
  name: my-app-db-secret
spec:
  refreshInterval: 1h          # wie oft ESO bei Vault nachfragt
  secretStoreRef:
    name: vault-backend
    kind: ClusterSecretStore
  target:
    name: my-app-db-creds      # Name des Kubernetes Secrets das erstellt wird
    creationPolicy: Owner
  data:
  - secretKey: password        # Key im Kubernetes Secret
    remoteRef:
      key: secret/my-app/db    # Pfad in Vault
      property: password
```

**Das erstellte Kubernetes Secret:**

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: my-app-db-creds        # automatisch vom ESO erstellt
data:
  password: <base64-encoded>   # von Vault geholt, automatisch aktualisiert
```

**Vorteile:**
- App braucht keinerlei Vault-Kenntnisse (nutzt normales K8s Secret)
- Einfache Migration bestehender Apps
- Automatische Rotation über `refreshInterval`
- Unterstützt viele Backends: Vault, AWS SM, GCP Secret Manager, Azure Key Vault

**Nachteile:**
- Secret existiert als Kubernetes Secret in etcd (kurzzeitig im Klartext)
- ESO muss Schreibrecht auf Kubernetes Secrets haben (RBAC beachten)

---

## Vergleich der 3 Wege

| | Vault Agent Injector | Vault CSI Provider | External Secrets Operator |
|---|---|---|---|
| **Secret in etcd?** | Nein | Nein (optional) | Ja |
| **Sidecar nötig?** | Ja | Nein | Nein |
| **App muss angepasst werden?** | Dateipfad lesen | Dateipfad lesen | Nein (normales K8s Secret) |
| **Automatische Rotation** | Ja (Live) | Nein | Ja (per Intervall) |
| **Mehrere Secret-Backends** | Nur Vault | Ja | Ja |
| **Einstiegshürde** | Mittel | Mittel | Niedrig |
| **Empfohlen für** | Vault-first Teams | Multi-Cloud | Migration / Einfachheit |

---

## Empfehlung

- **Neu mit Vault starten, höchste Sicherheit:** Vault Agent Injector oder CSI Provider —
  das Secret landet nie in etcd.
- **Bestehende Apps migrieren oder Multi-Cloud:** External Secrets Operator —
  Apps müssen nicht angepasst werden.
- **Produktion:** Immer Vault als HA-Cluster betreiben (mind. 3 Nodes, Raft Storage,
  Auto-Unseal konfiguriert).
