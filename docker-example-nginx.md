# Beispiel nginx 

```
docker run --name test-nginx -d -p 8080:80 nginx
```

```
# auf host machine ip des hosts ausfindig machen
ip a  show eth0 

docker container ls
lsof -i
cat /etc/services | grep 8080
curl http://localhost:8080
docker container ls
# wenn der container gestoppt wird, keine ausgabe mehr, weil kein webserver
docker stop test-nginx 
curl http://localhost:8080


```
