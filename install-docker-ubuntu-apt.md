# Install docker ubuntu apt 

## Walkthrough 

```
sudo apt-get update
sudo apt-get install -y \
    ca-certificates \
    curl \
    gnupg \
    lsb-release

sudo mkdir -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg

echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

sudo apt-get update
sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin
```

## Läuft der Dienst (dockerd) 

```
systemctl status docker 
```

## Docker als normaler Benutzer 

```
# Wenn dein unpriviligierter Benutzer kurs heisst
sudo su -
usermod -aG docker kurs
exit
```

```
# ich wechsele nochmal in den Benutzer kurs
su - kurs
# jetzt darf kein Fehler kommen 
docker images 
```

## docker-compose ? 

```
# herausfinden, ob docker compose installieren 
docker compose version 
```
