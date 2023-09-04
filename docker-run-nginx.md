# Docker run  (nginx) 

## Beispiel (binden an ein terminal), detached

```
docker run -d --name my_nginx nginx
docker container ls 
```

```
# wo sind die overlays
cd /var/lib/docker
# now find out 
```


```
# in den Container reinwechsel
# interactive 
docker exec -it my_nginx bash 
# oder wir führen nur ein Kommando aus
docker exec my_nginx cat /etc/os-release

```
