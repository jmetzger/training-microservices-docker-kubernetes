# Build image of reservations on gitlab ci-cd 

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
