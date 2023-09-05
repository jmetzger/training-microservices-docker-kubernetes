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
# Bau - Variante 1:
docker build -t multi-stage-example:v1 .
# Größe ?
docker images
docker run -d --name multibuildv1 -p 8081:8081 multi-stage-example:v1 
docker exec -it multibuildv1 sh
```

```
# in der Shell
ls -la
cd /app
# source ist da ! 
ls -la
```

## Step 2:

```
# modifizieren Dockerfile und zwar stage 2 das image
# vorher:
# FROM builder <- hier wird das komplette fertige image verwendet 
# jetzt: (das scheint das kleinstmögliche Image zu sein)  
FROM openjdk:8-jdk-alpine
```

```
docker build -t multi-stage-example:v2  .
# Größe ?
docker images 
docker run -d --name multibuildv2 -p 8080:8080 multi-stage-example:v2 
docker exec -it multibuildv2 sh
```

```
# in der Shell
ls -la
cd /app
# kein source
ls -la
```

## Step 3:

```
docker rm -f multibuildv2 multibuildv1
docker rmi multi-stage-example:v1 multi-stage-example:v2
```
