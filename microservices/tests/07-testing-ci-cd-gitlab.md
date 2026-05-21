# Tests in der GitLab CI/CD Pipeline

## Warum Tests in CI/CD integrieren?

Lokal laufen Tests vielleicht durch — aber im Team muss sichergestellt sein, dass kein
defekter Code in den `main`-Branch kommt. CI/CD laeuft bei jedem Push automatisch.

**Prinzip:** Schnelle Tests frueher, langsame Tests spaeter.

```
git push
   |
   v
Stage 1: static   (< 2 Min.)  -- ESLint, Trivy
   |
   v
Stage 2: unit     (< 3 Min.)  -- Jest, pytest
   |
   v
Stage 3: integration (< 15 Min.) -- Testcontainers, echte DB
   |
   v
Stage 4: contract  (< 5 Min.)  -- Pact, Dredd
   |
   (nightly only)
   v
Stage 5: e2e      (30-60 Min.) -- Playwright
```

---

## Komplette Pipeline (Node.js Beispiel)

```yaml
stages:
  - static
  - unit
  - integration
  - contract

# Stage 1: Static Analysis
lint:
  stage: static
  image: node:20-alpine
  script:
    - npm ci
    - npm run lint
  only:
    - merge_requests
    - main

# Stage 2: Unit Tests
unit-tests:
  stage: unit
  image: node:20-alpine
  script:
    - npm ci
    - npm run test:unit -- --coverage
  artifacts:
    reports:
      junit: test-results/junit.xml
      coverage_report:
        coverage_format: cobertura
        path: coverage/cobertura-coverage.xml
  only:
    - merge_requests
    - main

# Stage 3: Integration Tests (mit echter DB und Redis)
integration-tests:
  stage: integration
  image: node:20-alpine
  services:
    - postgres:16-alpine
    - redis:7-alpine
  variables:
    POSTGRES_DB: testdb
    POSTGRES_USER: testuser
    POSTGRES_PASSWORD: testpass
    DATABASE_URL: "postgresql://testuser:testpass@postgres:5432/testdb"
    REDIS_URL: "redis://redis:6379"
  before_script:
    - npm ci
    - npm run migrate
  script:
    - npm run test:integration
  artifacts:
    paths:
      - coverage/
    expire_in: 1 week
  only:
    - merge_requests
    - main

# Stage 4: Contract Tests
contract-tests:
  stage: contract
  image: node:20-alpine
  script:
    - npm ci
    - npm run test:pact
    - npx pact-broker publish ./pacts
        --broker-base-url $PACT_BROKER_URL
        --consumer-app-version $CI_COMMIT_SHA
  only:
    - merge_requests
    - main
```

---

## GitLab Services — wie funktioniert das?

GitLab `services:` startet Docker-Container parallel zum CI-Job.
Erreichbar ueber den Image-Namen als Hostname:

```yaml
services:
  - postgres:16-alpine   # erreichbar als Host "postgres"
  - redis:7-alpine       # erreichbar als Host "redis"

variables:
  DATABASE_URL: "postgresql://user:pass@postgres:5432/db"
  #                                      ^^^^^^^^
  #                                      Hostname = Image-Name
```

Das entspricht Testcontainers — nur dass GitLab die Container startet,
nicht die Test-Library. Beide Ansaetze sind valide.

---

## Tipp: Coverage-Badge in README

```yaml
# coverage: regex in GitLab CI konfigurieren
unit-tests:
  coverage: '/Statements\s+:\s+(\d+\.?\d+)%/'
```

GitLab zeigt dann den Coverage-Prozentsatz direkt im Merge Request an.

---

## Zusammenfassung

| Stage | Dauer | Wann |
|---|---|---|
| Static | < 2 Min. | Jeder Push |
| Unit | < 5 Min. | Jeder Push |
| Integration | 5-15 Min. | Jeder MR + main |
| Contract | < 5 Min. | Jeder MR + main |
| E2E | 30-60 Min. | Nightly / Vor Release |
