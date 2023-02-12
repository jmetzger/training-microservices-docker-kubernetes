# Dockerize posts service 

## Prequisites 

  * Install docker from docker repo (See within this training) 

## Files we need

```
cd 
cd blogs 
cd appv10-dockerize-posts 
# We do not want to be node_modules part of the game 
nano .dockerignore 
```

```
node_modules 
```

```
nano Dockerfile 
```

```
FROM node:18.14-alpine

WORKDIR /app
COPY package.json ./
RUN npm install
COPY ./ ./

CMD ["npm", "start"]
```

## Now build the file and tag it alreay 

  * When working with kubernetes, we will upload it 

```
# Format 
# docker build -t dockertrainereu/<your-name>-posts:0.0.1
docker build -t dockertrainereu/jm-posts:0.0.1
```

## Now run it and analyze to logs  

```
docker build -t dockertrainereu/jm-posts:0.0.1 .
docker run -d dockertrainereu/jm-posts:0.0.1   
docker container ls
# get the logs of the container 
docker logs fca
```
