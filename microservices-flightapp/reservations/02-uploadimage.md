# 02 Upload image microservice reservations to hub.docker. 

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
```

### Das image wird getagged 

  * Damit klar ist, wo es hingeschickt werden soll und zwar welches images 

```
# Bitte <namenskuerzel> ersetzen, z.B. jm  
docker tag msupandrunning-ms-reservations dockertrainereu/reservations-<namenskuerzel>:v14
docker login
```

```
# now enter gittrainereu + password-you-will-get-from-your-trainer ;O)
# push the image to the server
# <namenskuerzel> ersetzen durch z.B. jm 
docker push dockertrainereu/reservations-<namenskuerzel>:v14
```
