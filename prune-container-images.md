# Aufräumen 

## Alle nicht verwendeten container und images löschen 

```
# Alle container, die nicht laufen löschen 
docker container prune 

# Alle images, die nicht an eine container gebunden sind, löschen 
docker image prune 

# Alle nicht benötigten Daten löschen
docker system prune 
```
