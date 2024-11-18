# Docker Compose example Wordpress / MySQL 


## Schritt 1:
```bash
clear
cd
mkdir wp
cd wp
nano docker-compose.yml
```

## Schritt 2:

```yaml
services:
  database:
    image: mysql:5.7
    volumes:
      - database_data:/var/lib/mysql
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: mypassword
      MYSQL_DATABASE: wordpress
      MYSQL_USER: wordpress
      MYSQL_PASSWORD: wordpress

  wordpress:
    image: wordpress:latest
    depends_on:
      - database
    ports:
      - 8080:80
    restart: always
    environment:
      WORDPRESS_DB_HOST: database:3306
      WORDPRESS_DB_USER: wordpress
      WORDPRESS_DB_PASSWORD: wordpress
    volumes:
      - wordpress_plugins:/var/www/html/wp-content/plugins
      - wordpress_themes:/var/www/html/wp-content/themes
      - wordpress_uploads:/var/www/html/wp-content/uploads

volumes:
  database_data:
  wordpress_plugins:
  wordpress_themes:
  wordpress_uploads:


```

## Schritt 3:

```
docker compose up -d
# show all containers
docker ps
# show only containers from docker compose project
docker compose ps
```

## Schritt 3.5:

```
docker compose exec -it wordpress bash
```

```
apt update
apt install -y iputils-ping
ping -c4 database
exit
```

## Schritt 4: Alles wieder beenden 

```
docker compose down
```
