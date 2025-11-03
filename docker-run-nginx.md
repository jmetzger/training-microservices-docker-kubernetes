# Docker run  (nginx) 

## Beispiel (binden an ein terminal), detached

```
docker run -d --name my_nginx nginx:1.23
docker container ls
# oder 
docker ps

# Ip-Adresse rausfinden 
docker inspect my_nginx | grep -i -A 20 networksettings
# ip ist: 172.17.0.2
# Webseite von nginx anzeigen 
curl http://172.17.0.2
```

## Erkundung 

```
docker images
```

```
# falls nicht root
sudo su -
# wo sind die overlays
cd /var/lib/docker
cd overlay2
# now find out
ls -la
exit
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
# auf dem Host-System
ps aux | grep nginx
```

```
# oder wir führen nur ein Kommando aus
docker exec my_nginx cat /etc/os-release
```
