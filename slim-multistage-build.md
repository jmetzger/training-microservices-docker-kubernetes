# Example multistage build 

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
docker run --name multibuildv1 -p 8080:8080 multi-stage-example:v1 
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
docker run --name multibuildv2 -p 8080:8080 multi-stage-example:v2 
```
