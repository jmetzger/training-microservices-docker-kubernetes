# Build image of reservations on gitlab ci-cd (and push)

## Step 1: Clone Repo from github locally

```
cd
git clone https://github.com/jmetzger/ms-reservations.git
cd ms-reservations
```

## Step 1.5: Set identity 

```
# Ist sie gesetzt
git config user.name
git config user.email 
git config --list 

# Wenn nein 
git config --global user.name "Max Mustermann"
git config --global user.email "tn1@t3company.de"
```


## Step 1.7 gitlab repository erstellen 

  * Darauf achten, dass keine README.md angelegt, d.h. Haken rausnehmen für README.md
  * Und public haken setzen 



## Step 2: Change origin (target where push data) and push 

```
# of your newly created repo on gitlab 
git remote -v
git remote set-url origin https://gitlab.com/training.tn1/ms-jochen.git
# find out current branch and use it in next step
# marked with a *
git branch
# enter username + password 
git push -u origin master 
```



## Step 3a: (gitlab) build image and push to gitlab registry 

```
# modify .gitlab-ci.yml with pipeline editor as follows
stages:          # List of stages for jobs, and their order of execution
  - build

build-image:       # This job runs in the build stage, which runs first.
  stage: build
  image: docker:20.10.10
  services:
     - docker:20.10.10-dind
  script:
    - echo "user:"$CI_REGISTRY_USER
    - echo "pass:"$CI_REGISTRY_PASSWORD
    - echo "registry:"$CI_REGISTRY
    - echo $CI_REGISTRY_PASSWORD | docker login -u $CI_REGISTRY_USER $CI_REGISTRY --password-stdin
    - docker build -t $CI_REGISTRY_IMAGE .
    - docker images
    - docker push $CI_REGISTRY_IMAGE
    - echo "BUILD for $CI_REGISTRY_IMAGE done"
```

```
# this will run, when you commit
```

## Step 3b: Build image, when setting a tag and upload to docker hub 

### 3a) Ministep 1 - add variables for docker in SETTINGS -> CI/CD -> Variables 

```
# add
DOCKER_USER
DOCKER_PASS
DOCKER_PROJECT # z.B, reservations-jm
in Settings -> CI/CD -> Variables (in your repo)
```

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/0b245254-8c72-485b-9160-8826ebcaffde)

### 3b) Ministep 2
```
stages:
  - build

build-image:
  stage: build
  image:
    name: gcr.io/kaniko-project/executor:debug
    entrypoint: [""]
  script:
    - echo "user:"$DOCKER_USER
    - echo "project:"$DOCKER_PROJECT
    - mkdir -p /kaniko/.docker
    - echo "{\"auths\":{\"https://index.docker.io/v1/\":{\"auth\":\"$(printf "%s:%s" "${DOCKER_USER}" "${DOCKER_PASS}" | base64 | tr -d '\n')\"}}}" > /kaniko/.docker/config.json
    - /kaniko/executor
      --context "${CI_PROJECT_DIR}"
      --dockerfile "${CI_PROJECT_DIR}/Dockerfile"
      --destination "${DOCKER_USER}/${DOCKER_PROJECT}:${CI_COMMIT_TAG}"
    - echo "BUILD for "${DOCKER_USER}/${DOCKER_PROJECT}:${CI_COMMIT_TAG}" done"
  rules:
    - if: $CI_COMMIT_TAG
```

```
# Jetzt zum Testen (Triggern der Pipeline) 
# neuen Tag setzen 
CODE -> Tags -> New Tag -> (z.B.) v3
# https://gitlab.com/training.tn1/ms-jochen/-/tags/new
```
