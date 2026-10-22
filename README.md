# Expense Tracker Microservices

## 1. Project Overview

Expense Tracker is a microservice-based application for managing expenses and generating spending statistics. It consists of two independently deployable Java Spring Boot microservices and a PostgreSQL database.

The application is being developed for deployment on Kubernetes.

## 2. Architecture

The application consists of the following components:

### Expense Service
- Manages expense records through a REST API.
- Supports creating, retrieving, updating, and deleting expenses.
- Provides currency conversion using an external exchange-rate API.
- Stores expense data in PostgreSQL.

### Statistics Service
- Retrieves expense data through the Expense Service REST API.
- Calculates the number of expenses and their total amount.
- Calculates spending totals grouped by category.

### PostgreSQL Database
- Stores expense records persistently.
- Runs separately from the application microservices.

## 3. Technologies

- Java 17
- Spring Boot 3.5.0
- Maven
- Spring Data JPA
- PostgreSQL
- JUnit 5 and Mockito
- Docker
- Kubernetes

## 4. Local Development

The Expense Service runs on port `8081`.

The Statistics Service runs on port `8082`.

PostgreSQL must be running and configured before the Expense Service starts. The Statistics Service retrieves expense data through the Expense Service API.

See the README file in each microservice directory for detailed setup and testing instructions.

## 5. Testing

Each microservice has automated tests that can be run from its project directory:

```bash
./mvnw clean test
```

## 6. Deployment

Dockerfiles, Docker Hub images, and Kubernetes deployment manifests will be added as part of the deployment work.

The Kubernetes configuration will include separate application Deployments and Services, PostgreSQL persistent storage, and configuration for communication between the microservices.

## 7. Security Considerations

- Database credentials should be supplied through environment variables or Kubernetes Secrets.
- Development credentials must not be used in production.
- Services should expose only the endpoints required by the application.
- Container images and application dependencies should be kept up to date.
