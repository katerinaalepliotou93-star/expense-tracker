# Expense Tracker Microservices

## 1. Project Overview

Expense Tracker is a microservice-based application for managing expenses and generating spending statistics. It consists of two independently deployable Java Spring Boot microservices and a PostgreSQL database.

The application is containerized with Docker and deployed to Kubernetes. Each microservice exposes a REST API, and the services communicate over HTTP.

## 2. Architecture

The application contains three main components.

### Expense Service

The Expense Service manages expense records through a REST API. It supports creating, retrieving, updating, and deleting expenses. It also provides currency conversion using an external exchange-rate API.

It stores expense records in PostgreSQL using Spring Data JPA.

**Main endpoints:**
- `GET /api/health` — health check
- `GET /api/expenses` — retrieve expenses
- `GET /api/expenses/{id}` — retrieve an expense
- `POST /api/expenses` — create an expense
- `PUT /api/expenses/{id}` — update an expense
- `DELETE /api/expenses/{id}` — delete an expense
- `GET /api/exchange-rate` — retrieve a currency conversion result

### Statistics Service

The Statistics Service retrieves expense data from the Expense Service REST API and calculates summary information.

**Main endpoints:**
- `GET /api/health` — health check
- `GET /api/statistics/summary` — expense count and total amount
- `GET /api/statistics/categories` — totals grouped by category

The Statistics Service does not access the database directly. It obtains expense data through the Expense Service API.

### PostgreSQL Database

PostgreSQL stores expense records and runs separately from the application microservices. In Kubernetes, it is deployed using its own Deployment and ClusterIP Service.

A PersistentVolumeClaim requests 1 GiB of storage for database files. This allows the data to survive PostgreSQL pod replacement while the persistent volume is retained.

## 3. Architecture Diagram

```text
Client (curl or browser)
       |
       | REST / HTTP
       v
Expense Service <------ Statistics Service (REST Request to Expense Service)
       |                  
       |
       |
       |    SQL via Spring Data JPA              
       v
   PostgreSQL

Statistics Service requests expense data from
Expense Service through its REST API.

Kubernetes Services provide internal service discovery.
A PersistentVolumeClaim provides database storage.
```

## 4. Technologies

- Java 17
- Spring Boot 3.5.0
- Maven
- Spring Web and REST APIs
- Spring Data JPA
- PostgreSQL 16
- JUnit 5 and Mockito
- Docker
- Docker Hub
- Kubernetes

## 5. Design Patterns and Architectural Principles

### Microservices Architecture

The application is divided into two independently deployable services with distinct responsibilities: expense management and statistics.

### Database per Service — a design consideration

The Statistics Service accesses expense data through the Expense Service API rather than connecting directly to PostgreSQL. The Expense Service owns database access in the current implementation.

### API Communication

The services communicate through REST over HTTP. The Statistics Service uses a REST client to retrieve expenses from the Expense Service.

### Service Discovery

In Kubernetes, the Statistics Service uses the Kubernetes Service DNS name `expense-service` to reach the Expense Service.

### Containerization and Declarative Deployment

Docker images package the applications, while Kubernetes YAML manifests describe the desired state of Deployments, Services, database credentials, and persistent storage.

## 6. Running Locally

The Expense Service uses port `8081`, and the Statistics Service uses port `8082`.

PostgreSQL must be running before the Expense Service starts. Database connection settings can be supplied through the `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` environment variables.

The Statistics Service accepts an `EXPENSE_SERVICE_URL` environment variable. Its local default is `http://localhost:8081`.

See the README files inside each microservice directory for additional instructions.

## 7. Docker Images

The application images are published on Docker Hub:

- Expense Service: `kaalepou/expense-service:1.2`
- Statistics Service: `kaalepou/statistics-service:1.1`

The Expense Service image tag `1.2` contains the updated database configuration that supports environment variables.

## 8. Kubernetes Deployment

The `kubernetes/` directory contains the deployment manifests:

- `postgres.yaml` — PostgreSQL Deployment, Service, Secret, and PersistentVolumeClaim
- `expense-service.yaml` — Expense Service Deployment and NodePort Service
- `statistics-service.yaml` — Statistics Service Deployment and NodePort Service

Apply the manifests from the repository root:

```bash
kubectl apply -f kubernetes/postgres.yaml
kubectl apply -f kubernetes/expense-service.yaml
kubectl apply -f kubernetes/statistics-service.yaml
```

Inspect the resources:

```bash
kubectl get pods
kubectl get deployments
kubectl get services
kubectl get pvc
```

The application APIs can be accessed locally using `kubectl port-forward`. For example:

```bash
kubectl port-forward service/expense-service 8081:8081
```

In another terminal:

```bash
curl http://localhost:8081/api/health
curl http://localhost:8081/api/expenses
```

For the Statistics Service:

```bash
kubectl port-forward service/statistics-service 8082:8082
```

Then:

```bash
curl http://localhost:8082/api/health
curl http://localhost:8082/api/statistics/summary
curl http://localhost:8082/api/statistics/categories
```

Keep each port-forward process running while using its corresponding local port.

### Accessing the Web Interface

The browser interface is served by the Expense Service. After starting the port-forward process, open the following address in a browser:

`http://localhost:8081/`

The interface allows users to add and view expenses and retrieve spending statistics from the Statistics Service.

Keep the Expense Service and Statistics Service port-forward processes running while using the interface.

## 9. Testing

Each microservice has automated tests. Run them from the relevant project directory:

```bash
./mvnw clean test
```

The Kubernetes deployment was also exercised manually. The Expense Service and Statistics Service health and data endpoints returned successful HTTP responses, and the Statistics Service successfully calculated totals for an expense created through the Expense Service.

The application Deployments were scaled independently to two replicas each while PostgreSQL remained at one replica.

**Note:** Database persistence is configured through a PersistentVolumeClaim. A separate restart-and-data-retention test should be completed before claiming that persistence has been verified experimentally.

## 10. Benefits and Challenges

### Benefits

- Independent deployment of the two microservices
- Independent horizontal scaling of application services
- Clear separation between expense management and statistics
- REST APIs make service responsibilities explicit
- Persistent database storage is managed separately from application pods
- Container images support repeatable deployment

### Challenges

- Services must be configured to communicate using the correct addresses in different environments.
- A service may fail to start if its database is unavailable or connection settings are incorrect.
- Distributed services require separate logs and troubleshooting.
- Statistics depend on the availability and correctness of the Expense Service API.
- Persistent storage needs careful lifecycle management to avoid data loss.

## 11. Security Considerations and Mitigations

- **Database credentials:** Kubernetes Secrets are used to provide credentials to the PostgreSQL and Expense Service deployments. The example credentials in this project are for development only; production deployments should use stronger credentials and appropriate secret-management controls.
- **Network exposure:** PostgreSQL is exposed only through an internal ClusterIP Service. Only the application APIs need external access.
- **Input validation:** Expense API inputs should be validated to reject invalid amounts and malformed requests.
- **Dependency maintenance:** Java, Spring Boot, PostgreSQL, and container images should be kept up to date.
- **Transport security:** Production deployments should use HTTPS/TLS for external API traffic and appropriate protections for internal communication.
- **Access control:** A production version should add authentication and authorization where appropriate.

## 12. Repository Structure

```text
expense-tracker/
├── expense-service-project/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── statistics-service-project/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── kubernetes/
│   ├── postgres.yaml
│   ├── expense-service.yaml
│   └── statistics-service.yaml
├── .gitignore
└── README.md
```

## 13. Future Improvements

- Add authentication and authorization.
- Add readiness and liveness probes to Kubernetes Deployments.
- Add automated container and Kubernetes deployment tests.
- Configure resource requests and limits.
- Add monitoring, metrics, and centralized logging.
- Test database recovery and persistent-data retention after pod replacement.
- Improve currency handling in statistics so totals are not combined across different currencies.

## 14. Academic Use

This project demonstrates microservice separation, REST communication, Docker containerization, Kubernetes deployment, independent application scaling, and persistent database storage. The implementation and its limitations should be described accurately in the final report and demonstration.
