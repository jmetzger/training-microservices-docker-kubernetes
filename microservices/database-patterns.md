# Database patterns 

## Static Reference Data Service 

  * z.B. dedizierter Service für Ländercodes 

### Aufbau 

```
    Service        Service
   Warehouse       Finance 
       \              /
        \            /
            Service
         Country Code 

```
