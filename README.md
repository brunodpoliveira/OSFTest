# User Management System - MVP

This project is a simple backend application developed using Kotlin and Spring Boot. It includes functionalities for user management with asynchronous communication and basic monitoring.

## Features

- User registration and querying
- Asynchronous notifications (using in-memory queue for MVP, Kafka setup demonstrated in code)
- Basic monitoring using Spring Boot Actuator
- In-memory H2 database for quick setup and demo

## Technologies Used

- Spring Boot 2.7.0
- Kotlin
- Spring Data JPA
- H2 Database (in-memory)
- Spring Kafka (configuration demonstrated, not active in MVP)
- Spring Boot Actuator for monitoring

## MVP vs Production Setup

For simplicity and ease of running the MVP, this project uses an in-memory queue for asynchronous messaging. However, the code includes commented-out configurations and implementations for using Kafka in a production environment. This demonstrates the knowledge and setup required for a more robust, scalable messaging solution with Kafka.

To switch to a Kafka-based implementation:
1. Uncomment Kafka-related code in `UserServiceImpl`, `KafkaConfig`, and `application.properties`
2. Remove or comment out the `InMemoryQueueService` usage
3. Ensure a Kafka broker is running and accessible

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

## Notes

- This project uses Clean Code principles and appropriate design patterns.
- This is an MVP (Minimum Viable Product) designed for demonstration purposes.
- It uses an in-memory H2 database instead of PostgreSQL for simplicity.
- Kafka integration is demonstrated in the code but not active in the MVP for ease of setup and running.

For any questions or issues, please open an issue in the GitHub repository.