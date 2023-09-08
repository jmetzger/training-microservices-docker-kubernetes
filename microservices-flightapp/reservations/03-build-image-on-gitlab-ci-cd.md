# Build image of reservations on gitlab ci-cd (and push)

## Step 1: Clone Repo from github locally

```
cd
git clone https://github.com/jmetzger/ms-reservations.git
cd ms-reservations
```

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

## Step 3a: build image and push to gitlab registry 

```
# modify gitlab-ci.yml with pipeline editor as follows
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
DOCKER_PROJECT
in Settings -> CI/CD -> Variables (in your repo)
```

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/0b245254-8c72-485b-9160-8826ebcaffde)

### 3b) Ministep 2
```
stages:          # List of stages for jobs, and their order of execution
  - build

build-image:       # This job runs in the build stage, which runs first.
  stage: build
  image: docker:20.10.10
  services:
     - docker:20.10.10-dind
  script:
    - echo "user:"$DOCKER_USER
    - echo "pass:"$DOCKER_PASS
    - echo "project:"$DOCKER_PROJECT
    - echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
    - docker build -t $DOCKER_USER/$DOCKER_PROJECT:$CI_COMMIT_TAG .
    - docker images
    - docker push $DOCKER_USER/$DOCKER_PROJECT:$CI_COMMIT_TAG
    - echo "BUILD for "$DOCKER_USER/$DOCKER_PROJECT:$CI_COMMIT_TAG" done"
  rules:
      # Man muss einen Tag setzen, damit die Pipeline triggered.
    - if: $CI_COMMIT_TAG
```

```
# Jetzt zum Testen (Triggern der Pipeline) 
# neuen Tag setzen 
CODE -> Tags -> New Tag -> (z.B.) v3
# https://gitlab.com/training.tn1/ms-jochen/-/tags/new
```
