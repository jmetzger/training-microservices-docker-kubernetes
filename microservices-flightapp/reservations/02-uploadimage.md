# 02 Upload image microservice flights to hub.docker. 

## Step 1: Upload image to docker hub 

```
# eventually 
cd ms-reservations
```

```
# from the last step 01 Create microservce you should already have an image
docker images | grep reservations
# msupandrunning-ms-reservations     latest  
# to upload it to docker hub, we would need to tag it
# one image can have multiple tags
docker tag msupandrunning-ms-reservations dockertrainereu/reservations-jm:v1
docker login
# now enter gittrainereu + password-you-will-get-from-your-trainer ;O)
# push the image to the server 
docker push dockertrainereu/reservations-jm:v1
```
