# Static Tests

## Was sind Static Tests?

Static Tests pruefen den Code **ohne ihn auszufuehren**.
Das Analyse-Tool liest den Quellcode direkt — kein Server, keine Datenbank, keine Abhaengigkeiten.

```
Entwickler schreibt Code
       |
       v
   Static Analysis
  (kein Start noetig)
       |
       v
  Ergebnis: Fehler oder OK
```

**Wann werden sie ausgefuehrt?**
- Beim Speichern direkt im Editor (IDE-Plugin)
- Im Pre-Commit Hook (vor jedem `git commit`)
- In der CI/CD Pipeline (bei jedem Push)

---

## Die vier Arten von Static Tests

### 1. Linting — Syntaxfehler und schlechter Stil

```python
# FEHLER: Komma fehlt in Funktionsdefinition
def calculate_sum(a b):
    return a + b
```

```javascript
// FEHLER: Variable deklariert aber nie genutzt
const unusedVariable = 42;
eval(userInput);  // GEFAHR: Sicherheitsrisiko
```

**Tools:** ESLint (JS/TS), Pylint/Flake8 (Python), Checkstyle (Java)

---

### 2. Typ-Pruefung — falsche Datentypen

```typescript
// TypeScript erkennt den Fehler sofort
let age: number = "dreissig";  // Typkonflikt: string statt number

function greet(name: string): string {
    return 42;  // Fehler: number statt string zurueckgegeben
}
```

**Tools:** TypeScript, mypy (Python)

---

### 3. Security Scan — bekannte Sicherheitsluecken

```bash
# Abhaengigkeit mit bekannter CVE
pip install requests==2.6.0  # CVE-2014-1829: SSRF-Luecke

# Container Image mit veralteten Paketen
FROM ubuntu:18.04  # veraltet, viele ungepatchte CVEs
```

```bash
# Trivy: Container Image scannen
trivy image myapp:latest

# Ergebnis:
# CRITICAL: CVE-2024-1234 in libssl1.1
# HIGH:     CVE-2024-5678 in openssl
```

**Tools:** Trivy (Container), Snyk, OWASP Dependency Check, Semgrep

---

### 4. Code-Metriken — Komplexitaet und Qualitaet

```
Messung                      Schwellwert    Problem
-------------------------------------------------------
Code-Duplikation             > 5%           Copy-Paste-Code, schwer zu warten
Funktionslaenge              > 50 Zeilen    zu gross, schwer zu testen
Zyklomatische Komplexitaet   > 10           zu viele Verzweigungen
```

**Tools:** SonarQube, CodeClimate

---

## Integration: Pre-Commit Hook

Static Tests laufen automatisch vor jedem `git commit`:

```yaml
# .pre-commit-config.yaml
repos:
  - repo: https://github.com/pycqa/flake8
    rev: 7.0.0
    hooks:
      - id: flake8

  - repo: https://github.com/pre-commit/mirrors-eslint
    rev: v8.56.0
    hooks:
      - id: eslint
```

```bash
# Einmalig aktivieren
pre-commit install

# Ergebnis bei git commit:
# flake8...........PASSED
# eslint...........FAILED
# Commit wird blockiert bis Fehler behoben
```

---

## Integration: GitLab CI/CD

```yaml
stages:
  - static

lint:
  stage: static
  image: node:20-alpine
  script:
    - npm ci
    - npm run lint
  only:
    - merge_requests
    - main

security-scan:
  stage: static
  image: aquasec/trivy:latest
  script:
    - trivy image --exit-code 1 --severity HIGH,CRITICAL myapp:$CI_COMMIT_SHA
  only:
    - merge_requests
    - main
```

---

## Zusammenfassung

| Art | Findet | Typisches Tool |
|---|---|---|
| Linting | Syntaxfehler, schlechter Stil | ESLint, Pylint |
| Typ-Pruefung | Typkonflikte | TypeScript, mypy |
| Security Scan | Bekannte CVEs in Paketen/Images | Trivy, Snyk |
| Metriken | Komplexitaet, Duplikation | SonarQube |

**Vorteil:** Static Tests sind die schnellsten Tests — keine Ausfuehrung noetig,
kein Server muss laufen. Fehler werden in Sekunden gefunden.
