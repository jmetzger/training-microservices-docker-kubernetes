# Integration Testing mit Testcontainers

## Was ist das Problem mit klassischen Integration Tests?

Klassische Integration Tests laufen entweder gegen eine gemeinsame Testdatenbank
(Zustand von anderen Tests abhaengig) oder gegen gemockte Abhaengigkeiten
(Mock weicht von echter Implementierung ab).

**Testcontainers** loest das: Jeder Test startet seine eigene, isolierte
Docker-Instanz der echten Abhaengigkeit — Datenbank, Kafka, Redis, etc.

```
Ohne Testcontainers:          Mit Testcontainers:
                              
Test  -->  Shared DB          Test  -->  [PostgreSQL Container]  (frisch, isoliert)
           (Datensalat)                   startet beim Test, stoppt danach
```

---

## Setup (Java / Spring Boot)

```xml
<!-- pom.xml -->
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>testcontainers</artifactId>
    <version>1.19.8</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>postgresql</artifactId>
    <version>1.19.8</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>kafka</artifactId>
    <version>1.19.8</version>
    <scope>test</scope>
</dependency>
```

---

## Beispiel 1: PostgreSQL

```java
@SpringBootTest
@Testcontainers
class OrderRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
        .withDatabaseName("orders")
        .withUsername("test")
        .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    OrderRepository orderRepository;

    @Test
    void shouldSaveAndFindOrder() {
        Order order = new Order("booking-001", 500.0);
        orderRepository.save(order);

        Optional<Order> found = orderRepository.findById("booking-001");
        assertThat(found).isPresent();
        assertThat(found.get().getAmount()).isEqualTo(500.0);
    }
}
```

---

## Beispiel 2: Kafka (Async Messaging testen)

```java
@SpringBootTest
@Testcontainers
class OrderEventTest {

    @Container
    static KafkaContainer kafka = new KafkaContainer(
        DockerImageName.parse("confluentinc/cp-kafka:7.6.0"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @Autowired
    OrderService orderService;

    @Test
    void shouldPublishOrderPlacedEvent() throws Exception {
        // Consumer vorbereiten
        KafkaConsumer<String, String> consumer = createTestConsumer("order-events");

        // Bestellung ausloesen
        orderService.placeOrder("booking-001", 500.0);

        // Auf Event warten (max. 10 Sekunden)
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));

        assertThat(records.count()).isEqualTo(1);
        assertThat(records.iterator().next().value()).contains("booking-001");
    }
}
```

---

## Beispiel 3: Mehrere Container zusammen (Docker Compose Stil)

```java
@Testcontainers
class BookingIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Container
    static KafkaContainer kafka = new KafkaContainer(
        DockerImageName.parse("confluentinc/cp-kafka:7.6.0"));

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine")
        .withExposedPorts(6379);

    @Test
    void shouldCompleteBookingFlow() {
        // Test laeuft gegen echte PostgreSQL, echtes Kafka, echtes Redis
        // Kein Mock, kein geteilter State
    }
}
```

---

## Warum nicht einfach mocken?

```
Mock:                              Testcontainers:

orderRepository.save(order)        orderRepository.save(order)
  --> gibt was zurueck was           --> echter SQL-INSERT in
      du konfiguriert hast               echte PostgreSQL
      (kann von echter DB               (testet auch SQL-Syntax,
       abweichen)                        Constraints, Indexes)
```

Ein Mock testet nur, ob dein Code den Mock richtig aufruft.
Testcontainers testet ob dein Code mit der echten Abhaengigkeit funktioniert.

---

## Einordnung in die Test-Pyramide

```
         /\
        /E2E\          wenige, langsam
       /------\
      /Contract\       Pact, OpenAPI
     /----------\
    / Integration \    <-- Testcontainers hier
   /--------------\
  /   Unit Tests   \   viele, schnell
 /------------------\
```

Testcontainers = Integration Tests mit echten Abhaengigkeiten,
aber ohne laufende Infrastruktur vorauszusetzen.

---

## Unterstuetzte Technologien (Auswahl)

| Technologie | Testcontainers-Modul |
|---|---|
| PostgreSQL, MySQL, MongoDB | `postgresql`, `mysql`, `mongodb` |
| Kafka, RabbitMQ | `kafka`, `rabbitmq` |
| Redis | `GenericContainer("redis:7")` |
| Elasticsearch | `elasticsearch` |
| LocalStack (AWS S3, SQS ...) | `localstack` |
| Beliebiger Docker-Container | `GenericContainer` |
