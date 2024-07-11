# 02 Upload image microservice flights to hub.docker. 

## Step 1: Upload image to docker hub 

```
cd
cd ms-flights
```

```
# from the last step 01 Create microservce you should already have an image
docker images | grep flights
# ms-flights-ms-flights                 latest  
# to upload it to docker hub, we would need to tag it
# one image can have multiple tags
docker tag ms-flights-ms-flights dockertrainereu/flights-jm:v11
docker login
# now enter gittrainereu + password-you-will-get-from-your-trainer ;O)
# push the image to the server 
docker push dockertrainereu/flights-jm:v11
```
