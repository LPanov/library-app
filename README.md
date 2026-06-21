# Library Hub 📚

<img width="1427" height="846" alt="image" src="https://github.com/user-attachments/assets/89b2a7e6-c194-4048-abbf-145bedd9f61f" />

A distributed, full-stack microservices application designed for comprehensive library management and user reading metrics. Built with a decoupled **Spring Boot** backend ecosystem and a modern **React** frontend, the system utilizes centralized API routing, event-driven communication, and docker containerization.

---

## 🏗️ Architecture Overview

The application is built using a **Database-per-Service** pattern to ensure loose coupling and independent scalability.

- **Frontend**: Modern multi page app built with React.
- **API Gateway**: Central entry point using Spring Cloud Gateway to handle routing and enforce centralized security.
- **Identity & Access Management (IAM)**: Token-based authentication and authorization handled via **Keycloak**.
- **Event-Driven Messaging**: Asynchronous event dispatching utilizing **Apache Kafka**.
- **Notification Engine**: Decoupled email alert dispatching integrated with **Mailtrap**.
- **Polyglot Persistence**: Microservices leverage dedicated instances of **MySQL** and **PostgreSQL**.

---

## 🛠️ Tech Stack

### Frontend
- **Framework**: React.js
- **Styling**: Modern CSS UI with reactive dashboards
<img width="1895" height="821" alt="image" src="https://github.com/user-attachments/assets/2980c6c3-a5f1-40be-b4be-5beb31c83020" />


### Backend Microservices (Spring Boot)
- **API Gateway**: Spring Cloud Gateway
- **Security**: Spring Security
- **Authorization**: Keycloak (OAuth2 / OpenID Connect)
- **Message Broker**: Apache Kafka
- **Mail Server**: Mailtrap SMTP integration

### Databases & DevOps
- **Databases**: MySQL, PostgreSQL
- **Containerization**: Docker & Docker Compose

---

## 📂 Repository Structure & Services

- **`api-gateway`**: Intercepts all client requests, manages routing to downstream services, and integrates with Keycloak to validate access tokens.
- **`library-frontend`**: The user interface. Houses the landing page, authorization flows, member dashboard, and book discovery grids.
- **`book-service`**: Handles the core library catalog operations, inventory tracking, and book availability.
- **`genre-service`**: Manages categories and genres independently to streamline catalog sorting.
- **`user-service`**: Tracks user profiles, current loans, reservations, history, and reading streaks. Connects to Kafka to broadcast account events.
- **`notification-service`**: Listens asynchronously to Kafka topics and triggers transaction/notification emails through Mailtrap.

---

## 🚀 Getting Started

### Prerequisites
- [Docker & Docker Compose](https://www.docker.com/) installed.
- [Node.js](https://nodejs.org/) (if running frontend outside Docker).
- [Java 17+](https://adoptium.net/) (if running backend services locally).

### Quick Local Deployment (Docker Compose)

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/LPanov/library-app.git](https://github.com/LPanov/library-app.git)
   cd library-app
