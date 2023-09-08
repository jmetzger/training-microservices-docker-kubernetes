# Show content 

## Edit .gitlab-ci.yml with pipeline editor 

```
# with this content
stages:          # List of stages for jobs, and their order of execution
  - build
  - test
  - deploy

build-job:       # This job runs in the build stage, which runs first.
  stage: build
  script:
    - ls -la
    - pwd
```


