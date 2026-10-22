# Statistics Service

## 1. Overview

The Statistics Service is a RESTful microservice developed with Java and Spring Boot. It retrieves expense data from the Expense Service and calculates summary and category statistics.

It does not connect directly to the expense database. Instead, it communicates with the Expense Service through its REST API.

## 2. Technologies

- Java 17
- Spring Boot 3.5.0
- Maven
- Spring Web
- JUnit 5 and Spring Boot Test
- Mockito

## 3. Main Features

- Retrieve the total number of expenses.
- Calculate the total amount of expenses.
- Calculate spending totals grouped by category.
- Expose statistics through REST endpoints.
- Test calculations and controller behavior using automated tests.

## 4. REST API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/health` | Check service health |
| GET | `/api/statistics/summary` | Retrieve expense count and total amount |
| GET | `/api/statistics/categories` | Retrieve spending totals grouped by category |

## 5. Configuration

The application uses port `8082` for local development.

The Expense Service address is currently configured as:

`http://localhost:8081`

Both applications must be running locally for the statistics endpoints to retrieve real expense data.

When deployed to Kubernetes, the Expense Service address will be changed to its Kubernetes Service address.

## 6. Run the Application

From the project directory, run:

```bash
./mvnw spring-boot:run
```

Check the health endpoint:

```bash
curl http://localhost:8082/api/health
```

Retrieve the summary:

```bash
curl http://localhost:8082/api/statistics/summary
```

Retrieve category totals:

```bash
curl http://localhost:8082/api/statistics/categories
```

## 7. Run the Tests

Run the test suite with:

```bash
./mvnw clean test
```

The project includes automated tests for the statistics calculations and the summary REST endpoint.

## 8. Related Service

The Expense Service manages expense records and provides the data used by this service.
