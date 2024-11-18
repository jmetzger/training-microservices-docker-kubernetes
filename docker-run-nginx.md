# Docker run  (nginx) 

## Beispiel (binden an ein terminal), detached

```
docker run -d --name my_nginx nginx:1.23
docker container ls 
```

```
# falls nicht root
sudo su -
# wo sind die overlays
cd /var/lib/docker
# now find out 
```


```
# in den Container reinwechsel
# interactive 
docker exec -it my_nginx bash
```

```
# Falls wir Prozesse anschauen wollen mit tool ps
# im container
apt update
apt install -y procps
ps aux  | grep nginx
exit
```

```
# oder wir führen nur ein Kommando aus
docker exec my_nginx cat /etc/os-release
```
