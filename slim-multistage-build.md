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
# Bauen und vor Target stoppen 
docker build . -t multi-stage-example:v1 --target=builder # - Build image using a specific stage

# Bauen
docker build . -t multi-stage-binary:v1
```

## Step 2: Run only binary-version 

```
# run 
docker run --name app -d -t multi-stage-binary:v1 sh  
docker exec -it app sh 
```
