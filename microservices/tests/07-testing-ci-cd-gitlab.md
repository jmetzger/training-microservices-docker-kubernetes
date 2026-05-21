# Gitlab CI/CD - Integration Testing 

```
stages:
  - test
  - integration

integration_test:
  stage: integration
  image: node:18  # oder dein gewünschtes Image
  services:
    - postgres:15
    - redis:7
  variables:
    POSTGRES_DB: testdb
    POSTGRES_USER: testuser
    POSTGRES_PASSWORD: testpass
    DATABASE_URL: "postgresql://testuser:testpass@postgres:5432/testdb"
    REDIS_URL: "redis://redis:6379"
  before_script:
    - npm ci
    - npm run migrate  # DB-Migrationen ausführen
  script:
    - npm run test:integration
  artifacts:
    reports:
      junit: test-results/junit.xml
    paths:
      - coverage/
    expire_in: 1 week
  only:
    - merge_requests
    - main
```
