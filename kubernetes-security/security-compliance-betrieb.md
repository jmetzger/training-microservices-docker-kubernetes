# Security und Compliance im Betrieb von Kubernetes-Clustern

## Ueberblick: Sicherheitsschichten

Kubernetes-Security ist kein einzelnes Feature, sondern ein Schichtenmodell.
Jede Schicht schliesst Angriffsvektoren auf ihrer Ebene.

| Schicht | Thema | Schluessel-Tool |
|---------|-------|----------------|
| **Cluster-Infrastruktur** | etcd-Verschluesselung, TLS, Node-Haertung | Encryption at Rest, kubeadm |
| **Zugriffssteuerung** | Wer darf was im Cluster tun? | RBAC |
| **Netzwerk** | Welche Pods duerfen miteinander reden? | Network Policies, mTLS |
| **Workload** | Wie laufen Container ab? | PSA, SecurityContext, seccomp |
| **Secrets** | Wie werden Passwörter verwaltet? | Vault, ESO, CSI |
| **Images** | Sind Images vertrauenswuerdig? | Trivy, Cosign, Admission |
| **Audit** | Was ist im Cluster passiert? | Audit Logging |
| **Policy** | Werden Regeln automatisch durchgesetzt? | Kyverno, OPA/Gatekeeper |
| **Compliance** | Entspricht der Cluster Standards? | kube-bench, CIS Benchmark |

---

## 1. RBAC — Zugriffssteuerung

### Das Konzept

RBAC (Role-Based Access Control) regelt, welche **Subjects** (Nutzer, Gruppen, ServiceAccounts)
welche **Aktionen** auf welchen **Ressourcen** ausfuehren duerfen.

```
Subject --> RoleBinding --> Role --> Regeln (Verben auf Ressourcen)
```

**Verben:** `get`, `list`, `watch`, `create`, `update`, `patch`, `delete`

**Scope:**
- `Role` + `RoleBinding` → gilt in einem Namespace
- `ClusterRole` + `ClusterRoleBinding` → gilt cluster-weit

### Beispiel: Lese-Only-Rolle fuer Entwickler

```
apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
  name: dev-readonly
  namespace: production
rules:
- apiGroups: [""]
  resources: ["pods", "services", "configmaps"]
  verbs: ["get", "list", "watch"]
```

```
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
  name: dev-readonly-binding
  namespace: production
subjects:
- kind: User
  name: alice
  apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: Role
  name: dev-readonly
  apiGroup: rbac.authorization.k8s.io
```

### ServiceAccounts absichern

Jeder Pod erhaelt automatisch den `default`-ServiceAccount seines Namespace.
Dieser hat in vielen Clustern mehr Rechte als noetig.

```
# Token automatisches Mounten deaktivieren
apiVersion: v1
kind: ServiceAccount
metadata:
  name: my-app
automountServiceAccountToken: false
```

```
# Nur wenn noetig: explizit im Pod aktivieren
spec:
  serviceAccountName: my-app
  automountServiceAccountToken: true
```

### Best Practices RBAC

| Regel | Begruendung |
|-------|-------------|
| Keine `cluster-admin` fuer normale Nutzer | Kompromittiertes Konto = voller Clusterzugriff |
| Eigene ServiceAccounts pro App | Isolierte Rechte, kein shared `default` |
| `ClusterRoleBinding` nur wenn wirklich cluster-weit noetig | Scope auf Namespace begrenzen |
| Regelmaessige Auditierung mit `kubectl auth can-i` | Rechtedrift erkennen |

```
# Pruefen: Darf alice Pods in production loeschen?
kubectl auth can-i delete pods --namespace=production --as=alice
```

---

## 2. Netzwerk-Sicherheit

### Network Policies — die Firewall in Kubernetes

Standardmaessig darf jeder Pod mit jedem Pod im Cluster kommunizieren.
Network Policies aendern das: Sie definieren explizit erlaubten Traffic.

**Wichtig:** Network Policies werden vom CNI-Plugin durchgesetzt (Calico, Cilium, Weave).
Mit `kubenet` (Default in vielen Setups) werden Policies ignoriert.

#### Default-Deny fuer einen Namespace

```
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: default-deny-all
  namespace: production
spec:
  podSelector: {}      # alle Pods im Namespace
  policyTypes:
  - Ingress
  - Egress
```

Danach muss jede erlaubte Verbindung explizit aufgemacht werden.

#### Nur Frontend darf Backend erreichen

```
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: allow-frontend-to-backend
  namespace: production
spec:
  podSelector:
    matchLabels:
      app: backend
  ingress:
  - from:
    - podSelector:
        matchLabels:
          app: frontend
    ports:
    - protocol: TCP
      port: 8080
```

### mTLS — Verschlüsselung zwischen Diensten

Network Policies kontrollieren den Zugriff, verschluesseln aber nicht.
Fuer Verschlüsselung zwischen Pods wird **mTLS** (mutual TLS) eingesetzt.

**Optionen:**
- **Istio / Linkerd (Service Mesh):** mTLS automatisch zwischen allen Diensten
- **cert-manager:** Zertifikate fuer einzelne Dienste ausstellen

```
# Istio: PeerAuthentication fuer strict mTLS im Namespace
apiVersion: security.istio.io/v1beta1
kind: PeerAuthentication
metadata:
  name: default
  namespace: production
spec:
  mtls:
    mode: STRICT
```

---

## 3. Pod- und Container-Sicherheit

### SecurityContext

Auf Pod- und Container-Ebene definiert der `securityContext`, mit welchen
Linux-Rechten der Prozess laeuft.

```
apiVersion: v1
kind: Pod
spec:
  securityContext:
    runAsNonRoot: true          # kein Root-User
    runAsUser: 1000
    fsGroup: 2000
    seccompProfile:
      type: RuntimeDefault      # Standard-Syscall-Filter
  containers:
  - name: app
    securityContext:
      allowPrivilegeEscalation: false   # kein sudo/setuid
      readOnlyRootFilesystem: true      # Filesystem read-only
      capabilities:
        drop: ["ALL"]                   # alle Linux Capabilities entfernen
        add: ["NET_BIND_SERVICE"]       # nur was wirklich gebraucht wird
```

### Pod Security Admission (PSA)

PSA erzwingt Sicherheitsprofile auf Namespace-Ebene per Label.
Details und Uebungen: [Pod Security Admission](pod-security-admission.md)

| Profil | Beschreibung |
|--------|-------------|
| `privileged` | Keine Einschraenkungen |
| `baseline` | Verhindert bekannte Privilegieneskalationen |
| `restricted` | Strenge Haertung, empfohlen fuer Produktion |

```
# Namespace mit restricted-Profil
kubectl label namespace production \
  pod-security.kubernetes.io/enforce=restricted \
  pod-security.kubernetes.io/warn=restricted
```

---

## 4. Secrets-Management

Native Kubernetes Secrets haben grundlegende Schwaechen:
- Nur Base64-kodiert, nicht verschluesselt
- Liegen standardmaessig im Klartext in etcd
- Kein Audit-Trail, keine automatische Rotation

Loesungsansaetze und Details: [Secrets aus HashiCorp Vault — 3 Wege](vault-secrets-integration.md)

**Kurzuebersicht der Optionen:**

| Ansatz | Secret in etcd? | Rotation | Aufwand |
|--------|----------------|----------|---------|
| Vault Agent Injector | Nein | Automatisch (live) | Mittel |
| Vault CSI Provider | Nein | Nein | Mittel |
| External Secrets Operator | Ja (kurz) | Per Intervall | Niedrig |

---

## 5. Image Security und Supply Chain

### Das Problem

Ein Kubernetes-Cluster ist nur so sicher wie die Images, die darin laufen.
Angreifer nutzen Images mit bekannten CVEs oder schleusen boesartige Images ein.

### Image Scanning mit Trivy

```
# Lokales Image scannen
trivy image nginx:latest

# Nur kritische CVEs anzeigen
trivy image --severity CRITICAL,HIGH nginx:latest
```

**In CI/CD einbauen:** Pipeline schlaegt fehl, wenn Critical CVEs gefunden werden.

### Signed Images mit Cosign (Sigstore)

Cosign ermoeglicht das Signieren von Container-Images — der Cluster prueft
vor dem Start, ob die Signatur gueltig ist.

```
# Image signieren
cosign sign --key cosign.key registry.example.com/my-app:v1.0

# Signatur pruefen
cosign verify --key cosign.pub registry.example.com/my-app:v1.0
```

**Admission Controller** (z.B. Kyverno oder Connaisseur) kann das automatisch erzwingen:
Unsigned Images werden abgelehnt, bevor der Pod startet.

### Best Practices Image Security

| Regel | Massnahme |
|-------|-----------|
| Keine `latest`-Tags in Produktion | Immer konkrete Version pinnen |
| Minimale Base-Images | `distroless`, `alpine` statt `ubuntu` |
| Regelmässiges Scannen | Trivy in CI/CD und als Scheduled Job im Cluster |
| Eigene Registry | Nur gepruefe Images aus interner Registry erlauben |
| Image Signing | Cosign + Admission Controller |

---

## 6. Audit Logging

### Was wird protokolliert?

Das Kubernetes Audit Log erfasst alle API-Aufrufe: Wer hat wann was getan?

```
Jeder API-Call durchlaeuft 4 Stufen:
RequestReceived → ResponseStarted → ResponseComplete → Panic
```

### Audit Policy konfigurieren

Die Policy legt fest, welche Events auf welchem Level aufgezeichnet werden:

| Level | Was wird gespeichert |
|-------|---------------------|
| `None` | Nichts |
| `Metadata` | Nur Metadaten (Wer, Was, Wann) — kein Body |
| `Request` | Metadaten + Request-Body |
| `RequestResponse` | Metadaten + Request + Response-Body |

```
# /etc/kubernetes/audit-policy.yaml
apiVersion: audit.k8s.io/v1
kind: Policy
rules:
# Secrets: nur Metadaten (kein Klartext-Inhalt im Log)
- level: Metadata
  resources:
  - group: ""
    resources: ["secrets"]

# Lesezugriffe auf pods: ignorieren (zu viel Rauschen)
- level: None
  verbs: ["get", "list", "watch"]
  resources:
  - group: ""
    resources: ["pods"]

# Alles andere: Metadaten
- level: Metadata
```

```
# kube-apiserver starten mit:
--audit-log-path=/var/log/kubernetes/audit.log
--audit-policy-file=/etc/kubernetes/audit-policy.yaml
--audit-log-maxage=30
--audit-log-maxbackup=10
--audit-log-maxsize=100
```

### Typische Audit-Abfragen

```
# Wer hat das Secret "db-password" gelesen?
grep '"resource":"secrets"' audit.log | grep '"verb":"get"' | grep 'db-password'

# Welche Pods wurden heute geloescht?
grep '"verb":"delete"' audit.log | grep '"resource":"pods"'
```

---

## 7. etcd-Verschlüsselung (Encryption at Rest)

### Das Problem

etcd ist die Kubernetes-Datenbank. Sie speichert alle Objekte — auch Secrets.
Standardmaessig liegen diese Secrets im Klartext in etcd.

Wer Zugriff auf ein etcd-Backup hat, kann alle Secrets auslesen:

```
# Ohne Encryption at Rest:
ETCDCTL_API=3 etcdctl get /registry/secrets/default/my-secret | strings
# → Klartext-Passwort sichtbar
```

### Encryption at Rest aktivieren

```
# /etc/kubernetes/encryption-config.yaml
apiVersion: apiserver.config.k8s.io/v1
kind: EncryptionConfiguration
resources:
- resources:
  - secrets
  providers:
  - aescbc:
      keys:
      - name: key1
        secret: <base64-encoded-32-byte-key>
  - identity: {}   # Fallback fuer bereits gespeicherte, unverschluesselte Secrets
```

```
# kube-apiserver starten mit:
--encryption-provider-config=/etc/kubernetes/encryption-config.yaml
```

```
# Bestehende Secrets re-encrypten:
kubectl get secrets --all-namespaces -o json | kubectl replace -f -
```

**Empfehlung fuer Produktion:** Statt AES-CBC besser einen KMS-Provider nutzen
(AWS KMS, GCP KMS, Azure Key Vault) — der Schluessel liegt dann ausserhalb von etcd.

---

## 8. Policy Enforcement: Kyverno und OPA/Gatekeeper

### Warum Policy Enforcement?

RBAC regelt Zugriff, aber nicht die Qualitaet von Manifesten.
Policy Engines pruefen beim Erstellen/Aendern von Ressourcen:
- Hat das Deployment Ressource-Limits?
- Laeuft der Container als root?
- Ist das Image aus der erlaubten Registry?

### Kyverno

Kyverno arbeitet nativ mit Kubernetes-Manifesten — keine eigene Sprache noetig.

```
# Policy: Alle Pods muessen Ressource-Limits haben
apiVersion: kyverno.io/v1
kind: ClusterPolicy
metadata:
  name: require-resource-limits
spec:
  validationFailureAction: Enforce   # oder Audit
  rules:
  - name: check-container-resources
    match:
      any:
      - resources:
          kinds: ["Pod"]
    validate:
      message: "Ressource-Limits sind Pflicht."
      pattern:
        spec:
          containers:
          - name: "*"
            resources:
              limits:
                memory: "?*"
                cpu: "?*"
```

```
# Policy: Nur Images aus eigener Registry erlauben
apiVersion: kyverno.io/v1
kind: ClusterPolicy
metadata:
  name: allowed-registries
spec:
  validationFailureAction: Enforce
  rules:
  - name: check-registry
    match:
      any:
      - resources:
          kinds: ["Pod"]
    validate:
      message: "Nur registry.example.com ist erlaubt."
      pattern:
        spec:
          containers:
          - name: "*"
            image: "registry.example.com/*"
```

### OPA/Gatekeeper

OPA (Open Policy Agent) + Gatekeeper bietet mehr Flexibilitaet durch Rego-Sprache,
ist aber komplexer.

```
# ConstraintTemplate (Rego-Logik)
apiVersion: templates.gatekeeper.sh/v1
kind: ConstraintTemplate
metadata:
  name: k8srequiredlabels
spec:
  crd:
    spec:
      names:
        kind: K8sRequiredLabels
  targets:
  - target: admission.k8s.gatekeeper.sh
    rego: |
      package k8srequiredlabels
      violation[{"msg": msg}] {
        not input.review.object.metadata.labels["team"]
        msg := "Label 'team' fehlt."
      }
```

### Vergleich Kyverno vs. OPA/Gatekeeper

| | Kyverno | OPA/Gatekeeper |
|---|---------|----------------|
| **Sprache** | YAML/JSON (nativ K8s) | Rego (eigene DSL) |
| **Einstieg** | Einfach | Steiler |
| **Flexibilitaet** | Gut | Sehr hoch |
| **Mutation** | Ja (Manifeste anpassen) | Ja |
| **Community** | Wachsend, CNCF | Etabliert, CNCF |
| **Empfehlung** | Fuer die meisten Teams | Wenn komplexe Logik noetig |

---

## 9. Compliance-Frameworks und Tools

### CIS Kubernetes Benchmark

Der CIS (Center for Internet Security) Benchmark ist der meistgenutzte
Standard fuer Kubernetes-Haertung. Er prueft u.a.:

- API-Server-Konfiguration (TLS, Admission Plugins)
- etcd-Sicherheit
- kubelet-Einstellungen
- RBAC und Service Accounts
- Network Policies

**Automatische Pruefung mit kube-bench:**

```
# kube-bench als Job im Cluster ausfuehren
kubectl apply -f https://raw.githubusercontent.com/aquasecurity/kube-bench/main/job.yaml
kubectl logs -l app=kube-bench
```

Ausgabe zeigt PASS/FAIL/WARN pro Check mit konkreter Behebungsanweisung.

### NSA/CISA Kubernetes Hardening Guide

Die US-Behoerden NSA und CISA haben 2021 einen Leitfaden veroeffentlicht.
Schwerpunkte:

| Bereich | Empfehlungen |
|---------|-------------|
| Pod Security | Non-root, read-only FS, keine privilegierten Pods |
| Network Policies | Default-deny, explizite Freigaben |
| Authentication | MFA fuer Cluster-Zugriff, kubeconfig sichern |
| Audit Logging | Aktivieren und zentral sammeln |
| Updates | Regelmaessige Updates von Kubernetes und Nodes |

### kube-score

kube-score analysiert Kubernetes-Manifeste statisch — ideal in CI/CD:

```
# Deployment pruefe
kube-score score deployment.yml
```

```
[CRITICAL] Container Security Context
    · app -> Container has no configured security context
      Set securityContext to run the container in a more secure context.

[WARNING] Container Resources
    · app -> CPU limit is not set
```

---

## 10. Sicherheits-Checkliste fuer Produktion

### Zugriffssteuerung

- [ ] RBAC aktiviert und konfiguriert (kein Wildcard-Admin fuer normale Nutzer)
- [ ] ServiceAccounts pro App, `automountServiceAccountToken: false` als Default
- [ ] kubeconfig-Dateien nicht committet, Rotation konfiguriert
- [ ] `kubectl auth can-i` regelmaessig auditieren

### Netzwerk

- [ ] Network Policies: Default-Deny pro Namespace, explizite Freigaben
- [ ] CNI-Plugin unterstuetzt Network Policies (Calico, Cilium, Weave)
- [ ] mTLS in Produktion (Service Mesh oder cert-manager)

### Workload

- [ ] PSA: Namespace mit `baseline` oder `restricted` konfiguriert
- [ ] `runAsNonRoot: true`, `allowPrivilegeEscalation: false`
- [ ] `readOnlyRootFilesystem: true` wo moeglich
- [ ] Ressource-Limits fuer alle Container gesetzt

### Secrets

- [ ] Encryption at Rest fuer etcd aktiviert (oder KMS-Provider)
- [ ] Kein Klartext in ConfigMaps, kein Secret im Git
- [ ] Secret-Management-System (Vault, ESO) fuer Produktion evaluiert

### Images

- [ ] Kein `latest`-Tag in Produktion
- [ ] Image Scanning in CI/CD (Trivy)
- [ ] Nur Images aus vertrauenswuerdiger Registry
- [ ] Image Signing evaluieren (Cosign)

### Audit und Compliance

- [ ] Audit Logging aktiviert, Policy definiert
- [ ] Logs zentral gesammelt (SIEM, Elastic, Loki)
- [ ] kube-bench regelmaessig ausgefuehrt
- [ ] Policy Engine (Kyverno oder OPA/Gatekeeper) fuer kritische Regeln

### Updates

- [ ] Kubernetes-Version aktuell (maximal 2 Minor-Versionen hinter aktuellem Release)
- [ ] Node-OS regelmaessig gepatcht
- [ ] Automatische CVE-Alerts fuer verwendete Images

---

## Weiterführende Seiten in diesem Training

- [Kubernetes Tipps Hardening](tipps-hardening.md)
- [Pod Security Admission — Uebung](pod-security-admission.md)
- [Secrets aus HashiCorp Vault — 3 Wege](vault-secrets-integration.md)
- [Network Policies — Beispiele Ingress/Egress](/kubernetes-networks/examples-ingress-egress.md)
