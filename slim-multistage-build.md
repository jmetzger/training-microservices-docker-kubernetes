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

```
# modifizieren Dockerfile und zwar stage 2 das image
# vorher:
# FROM builder
# jetzt:
FROM openjdk:8-jdk-slim
```

```
docker build -t multi-stage-example:v2  .
docker run --name multibuildv2 -p 8080:8080 multi-stage-example:v2 
```
