# Spring Boot 4.0.4 CQRS Example

A practical CQRS order application with:

- Separate command and query REST APIs
- Separate `orders_write` and `orders_read` tables
- Domain events
- Asynchronous after-commit projection updates
- H2 in-memory database
- Validation and centralized error handling
- Java 21 and Maven

## Requirements

- JDK 21
- Maven 3.9+

## Run

```bash
mvn clean spring-boot:run
```

Application: `http://localhost:8080`

H2 console: `http://localhost:8080/h2-console`

JDBC URL: `jdbc:h2:mem:cqrsdb`  
Username: `sa`  
Password: leave blank

## Create an order

```bash
curl -X POST http://localhost:8080/api/commands/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "Parameswari",
    "productName": "Laptop",
    "quantity": 2,
    "unitPrice": 65000
  }'
```

Copy the returned `orderId`.

## Query the order

Because projection processing is asynchronous, retry after a few milliseconds if the first query returns 404.

```bash
curl http://localhost:8080/api/queries/orders/{orderId}
```

## Confirm an order

```bash
curl -X PUT http://localhost:8080/api/commands/orders/{orderId}/confirm
```

## CQRS flow

```text
Command API
    -> write model transaction
    -> domain event
    -> AFTER_COMMIT asynchronous projection handler
    -> read model
    -> Query API
```

This project demonstrates CQRS with an event-driven read projection. It is not full event sourcing. For production, use a transactional outbox and Kafka/RabbitMQ instead of relying only on in-process events.
