# Expense Service

## 1. Overview

The Expense Service is a RESTful microservice developed with Java and Spring Boot. It manages expense records, stores them in PostgreSQL, and provides currency conversion using an external exchange-rate API.

It is one of the two microservices in the Expense Tracker application.

## 2. Technologies

- Java 17
- Spring Boot 3.5.0
- Maven
- Spring Web
- Spring Data JPA
- PostgreSQL
- JUnit 5 and Spring Boot Test

## 3. Main Features

- Create, retrieve, update, and delete expenses.
- Store expenses in PostgreSQL.
- Retrieve currency exchange rates through the Frankfurter API.
- Convert an amount from one currency to another.
- Provide a health endpoint.

## 4. REST API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/health` | Check service health |
| GET | `/api/expenses` | Retrieve all expenses |
| GET | `/api/expenses/{id}` | Retrieve an expense by ID |
| POST | `/api/expenses` | Create an expense |
| PUT | `/api/expenses/{id}` | Update an expense |
| DELETE | `/api/expenses/{id}` | Delete an expense |
| GET | `/api/exchange-rate?from=USD&to=EUR&amount=100` | Convert an amount using the exchange rate |

## 5. Configuration

The application uses the following local defaults:

- Port: `8081`
- Database: `expenses`
- Database username: `expenseuser`

The database URL, username, and password can be supplied using the `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` environment variables. The default credentials are intended for local development only.

PostgreSQL must be running before the application starts.

## 6. Run the Application

From the project directory, run:

```bash
./mvnw spring-boot:run
```

Check the health endpoint:

```bash
curl http://localhost:8081/api/health
```

## 7. Run the Tests

Run the test suite with:

```bash
./mvnw clean test
```

The project includes an integration test for the expense CRUD lifecycle.

## 8. Related Service

The Statistics Service calls this service's REST API to retrieve expenses and calculate summary and category statistics.
