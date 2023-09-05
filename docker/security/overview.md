# Security Overview 

## Generic 

  * Kann ich dem Image vertrauen (nur Images verwenden, denen ich vertrauen kann)
    * Im Zweifel selber oder nur images von Docker Official Image / Verified Publisher (Suche auf Docker Hub)
  * Container möglichst nicht als Root laufen lassen (bzw. solche Images vewrenden)
  * Das nur das drinnen ist, was wirklich gebraucht wird (Produktion)
    * Im Idealfall sogar nur das Executable (siehe auch hashicorp/http-ech -> kein sh, keine bash
  * Alle container einer applikation in einem eigenen Netzwerk  
  * Images to scannen / security scans. 

## Images die nicht als root lauen 

  * bitnami
  * nginx unprivileged

```
# Variante 1:
Erkennbar durch USER - Eintrag in Dockerfile
# oder
docker compose exec database id
docker exec <container> id 
```

```
# https://hub.docker.com/r/bitnami/mariadb
# https://github.com/bitnami/containers/blob/main/bitnami/mariadb/11.0/debian-11/Dockerfile
USER 1001 
```


## Run container under specific user: 

```
# user with id 40000 does not need to exist in container 
docker run -it -u 40000 alpine 

# user kurs needs to exist in container (/etc/passwd) 
docker run -it -u kurs alpine 

```

## Default capabilities 

  * Set everytime a new container is started as default 
  * https://github.com/moby/moby/blob/master/profiles/seccomp/default.json


## Run container with less capabilities 

```
cd
mkdir captest
cd captest 
```

```
nano docker-compose.yml 
```

```
services: 
  nginx:
    image: nginx 
    cap_drop:
      - CHOWN
```

```
docker compose up -d
# start and exits 
docker compose ps 
# 
docker exec -it captest_nginx_1 bash 
#/ touch /tmp/foo; chown 10000 /tmp/foo  

# what happened -> wants to do chown, but it is not allowed 
docker logs captest_nginx_1 

```

```
docker compose down 
```


## Reference:

  * https://cheatsheetseries.owasp.org/cheatsheets/Docker_Security_Cheat_Sheet.html
  * https://www.redhat.com/en/blog/secure-your-containers-one-weird-trick
  * man capabilities
