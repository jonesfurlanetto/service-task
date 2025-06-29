# Service API

This project is a Spring Boot microservice with support for **MongoDB**, **JPA**, **Swagger (OpenAPI)** and is fully
dockerized. It provides RESTful endpoints for data manipulation and observability via Actuator.

---

## Technologies

- Java 21
- Spring Boot 3.2.5
- Spring Data JPA
- Spring Data MongoDB
- Spring Validation
- Springdoc OpenAPI (Swagger UI)
- Docker & Docker Compose

---

## How to run the project with Docker

### Prerequisites

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

### Steps:

```bash
# 1. Package the application
mvn clean package -DskipTests

# 2. Build and upload the containers
docker-compose up --build
```

The application will be available at: http://localhost:8080

## API Documentation

Swagger UI:
http://localhost:8080/swagger-ui/index.html

OpenAPI JSON:
http://localhost:8080/v3/api-docs

## Monitoring

Actuator Endpoints:

http://localhost:8080/actuator/health

http://localhost:8080/actuator/info

## Tests

To run the unit tests:

```bash
mvn test
```