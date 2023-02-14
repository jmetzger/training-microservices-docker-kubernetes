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
