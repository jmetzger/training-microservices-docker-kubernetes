# Encryption Datenverzeichnis mariadb mit Thales 

## Background 

  * Thales verwendet transparente Encryption (funktioniert über den Kernel)

<img width="928" height="187" alt="image" src="https://github.com/user-attachments/assets/336d3a1e-5dd1-4c81-a24d-f8c1a566854a" />

## Was macht ein GuardPoint ? 

  * Verschlüsselung: Alle neuen Dateien werden verschlüsselt gespeichert
  * Entschlüsselung: Autorisierte Prozesse können transparent lesen
  * Zugriffskontrolle: Policy definiert wer Zugriff hat
  * Audit: Alle Zugriffe werden geloggt

## Beispiel für mariadb (wenn kein uid_mapping) 

<img width="539" height="683" alt="image" src="https://github.com/user-attachments/assets/c060df8b-42fc-4ee0-b2b4-05f9f5bf754c" />

## Wie hänge ich ein mount ein, der nicht unter volumes liegt in docker ? 

  * Achtung, Rechte müssen richtig gesetzt sein
  * chown 1001:1001 /data/mariadb # wenn der mariadb - container unter diesem User läuft  

version: '3.8'

services:
  mariadb:
    image: mariadb:11.2
    container_name: mariadb-encrypted
    user: "10001:10001"
    environment:
      MYSQL_ROOT_PASSWORD: SuperSecret123
      MYSQL_DATABASE: appdb
      MYSQL_USER: appuser
      MYSQL_PASSWORD: AppPass456
    volumes:
      - /data/mariadb:/var/lib/mysql
    ports:
      - "3306:3306"
    restart: unless-stopped

