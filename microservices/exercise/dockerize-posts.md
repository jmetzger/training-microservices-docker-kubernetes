# Dockerisierung von posts aus blog-projekt 

## Walkthrough 

```
sudo -i 
cd 
git clone https://github.com/jmetzger/training-microservices-docker-kubernetes-uebungen blog
```

```
# Create Dockerfile in posts 
cd posts 
nano Dockerfile
```

```
FROM node:16-alpine

WORKDIR /app
# COPY package.json ./
COPY ./ ./
RUN npm install

CMD ["npm", "start"]
```

```
nano .dockerignore 
```

```
package-lock.json 
```

```
docker build -t dockertrainereu/<namenskuerzel>-posts:0.0.1 .
docker run -d --name posts dockertrainereu/<namenkuerzel>-post:0.0.1 
docker logs posts 
```
