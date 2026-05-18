# Architektur und Schichten in Microservices 

Bei Microservices meint man mit "Schichten" die **interne Strukturierung** eines einzelnen Microservices:

**Typische Schichten:**
- **Presentation Layer** - API-Endpunkte (REST, gRPC)
- **Business Logic Layer** - Geschäftslogik und Domänenregeln
- **Data Access Layer** - Datenbankzugriffe und Persistierung

**Wichtig:** Jeder Microservice hat seine eigenen Schichten - im Gegensatz zu monolithischen Architekturen, wo Schichten über die gesamte Anwendung gehen.

