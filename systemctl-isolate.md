# Den Grafischen Modus abschalten 

  * Besser: komplett deinstallieren

## Das geht immer 

```
# also root
sudo su -
# target ohne Grafik 
systemctl isolate multi-user
# Beim Start auch diese Target setzen
systemctl set-default multi-user
```
