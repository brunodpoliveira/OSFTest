# User Management System - MVP

This project is a simple backend application developed using Kotlin and Spring Boot. It includes functionalities for user management with asynchronous communication and basic monitoring.

## Features

- User registration and querying
- Asynchronous notifications using in-memory Kafka
- Basic monitoring using Spring Boot Actuator
- In-memory H2 database for quick setup and demo

## Technologies Used

- Spring Boot 2.7.0
- Kotlin
- Spring Data JPA
- H2 Database (in-memory)
- Spring Kafka (with in-memory broker for MVP)
- Spring Boot Actuator for monitoring

## Prerequisites

- JDK 11 or later
- Maven 3.6 or later

## Running the Application

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/user-management.git
   cd user-management
   ```

2. Build the project:
   ```
   mvn clean install
   ```

3. Run the application:
   ```
   mvn spring-boot:run
   ```

The application will start and be available at `http://localhost:8080`.

## API Endpoints

- Create User: POST `/api/users`
- Get User by ID: GET `/api/users/{id}`
- Get All Users: GET `/api/users`
- Get User by Email: GET `/api/users/email/{email}`

## Monitoring

The application exposes various metrics and health checks through Spring Boot Actuator. You can access these at:

- Health check: GET `/actuator/health`
- Metrics: GET `/actuator/metrics`
- All actuator endpoints: GET `/actuator`

## Testing

To run the automated tests, use the following command:

   ```
   mvn test
   ```

## Running with Docker

This application can also be run using Docker, which encapsulates the entire runtime environment.

### Prerequisites

- Docker
- Docker Compose

### Steps

1. Build the Docker image:
   ```
   docker build -t user-management-mvp .
   ```

2. Run the application using Docker Compose:
   ```
   docker-compose up
   ```
The application will be available at `http://localhost:8080`.

To stop the application, use:
   ```
   docker-compose down
   ```
### Notes on Docker Setup

- The application runs in a single container, including the H2 database and simulated Kafka.
- The `SPRING_PROFILES_ACTIVE=docker` environment variable in `docker-compose.yml` activates the Docker-specific configuration.
- In a production environment, you would typically have separate containers for the application, database, and Kafka.

## Notes

- This project uses Clean Code principles and appropriate design patterns.
- This is an MVP (Minimum Viable Product) designed for demonstration purposes.
- It uses an in-memory H2 database instead of PostgreSQL for simplicity.
- Kafka is simulated using an in-memory implementation for demo purposes.
- In a production environment, you would use a proper PostgreSQL database and a real Kafka setup.

## Additional Information

- The application uses Spring Data JPA for database operations.
- Asynchronous notifications are simulated using an in-memory Kafka implementation.
- Basic monitoring is implemented using Spring Boot Actuator.

For any questions or issues, please open an issue in the GitHub repository.