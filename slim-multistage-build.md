# Example multistage build 

## Overview

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/c6ee24f7-3669-4410-bfe9-4e2d08cf8ac7)

## Step 1:

```
# Clone repo 
cd 
git clone https://github.com/jmetzger/multi-stage-example
cd multi-stage-example 
```

```
# Bauen
docker build . -t multi-stage-example:v1
```

## Step 2

```
docker build . -t multi-stage-example:v1 --target=builder # - Build image using a specific stage
# run 
docker run multi-stage-example:v1 -p 8080:8080
```
