# Rest-Api 

## Grundlagen 

  * synchrone Kommunikation -> bspw. REST-API (zu 95%)

## Idee dahinter (microservice) 

 *  Nie direkt auf Daten zugreifen 

## Damit auch die Möglichkeit haben, Informationen zu verstecken

  * Prinzip von Hide Information, nur das wirklich gebraucht wird, kann abgefragt werden und der Rest ist nur im Hintergrund innerhalb des MicroServices zugänglich
  * API (klarer Vertrag, wo festgelegt, welche Parameter an die API übergeben werden können/müssen) und welche Information zurückkommt 

## Beispiel

```
# GET - Abfrage 
https://api.bitpanda.com/v1/trades
# i.d.R. kriegen wir die Information als json zurück 

```

```
PUT /shop/products/11 HTTP/1.1
Host: api.predic8.de
Content-Type: application/json

{
  "name": "Red Grapes",
  "price": 1.79,
  "category_url": "/shop/categories/Fruits",
  "vendor_url": "/shop/vendors/501"
}

curl -X PUT --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{
      "name": "Red Grapes",
      "price": 1.79,
      "category_url": "/shop/categories/Fruits",
      "vendor_url": "/shop/vendors/501"
}' 'https://api.predic8.de/shop/products/140'
```
