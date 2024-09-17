# User Management System - MVP

This project is a simple backend application developed using Kotlin and Spring Boot. It includes functionalities for user management with asynchronous communication and basic monitoring.

## Features

- User registration and querying
- Asynchronous notifications (using in-memory queue for MVP, Kafka setup demonstrated in code)
- Basic monitoring using Spring Boot Actuator
- In-memory H2 database for quick setup and demo
- PostgreSQL configuration (commented out, for demonstration purposes)

## Technologies Used

- Spring Boot 2.7.0
- Kotlin
- Spring Data JPA
- H2 Database (in-memory)
- Spring Kafka (configuration demonstrated, not active in MVP)
- Spring Boot Actuator for monitoring
- PostgreSQL (configuration provided, not active in MVP)

## MVP vs Production Setup

For simplicity and ease of running the MVP, this project uses an in-memory H2 database and an in-memory queue for asynchronous messaging. However, the code includes commented-out configurations for using PostgreSQL as the database and Kafka for messaging in a production environment. This demonstrates the knowledge and setup required for a more robust, scalable solution.

To switch to a production-ready setup with PostgreSQL and Kafka:

1. PostgreSQL Setup:
   - Install PostgreSQL or set up a PostgreSQL Docker container
   - Create a new database: `CREATE DATABASE usermanagement;`
   - Create a user and grant privileges:
     ```sql
     CREATE USER youruser WITH ENCRYPTED PASSWORD 'yourpassword';
     GRANT ALL PRIVILEGES ON DATABASE usermanagement TO youruser;
     ```
   - Update `application.properties` with PostgreSQL connection details
   - Uncomment the PostgreSQL dependency in `pom.xml`
   - Remove or comment out the H2 database dependency

2. Kafka Setup:
   - Install and start a Kafka broker or set up a Kafka Docker container
   - Create necessary Kafka topics:
     ```
     kafka-topics.sh --create --topic user-creation-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
     kafka-topics.sh --create --topic user-deletion-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
     ```
   - Update `application.properties` with Kafka configuration
   - Uncomment Kafka-related code in `KafkaConfig.kt`, `UserServiceImpl.kt`, and other relevant files
   - Remove the `InMemoryQueueService` and its usages

3. Code Changes:
   - Update `UserServiceImpl.kt` to use Kafka instead of the in-memory queue
   - Create a `KafkaConsumerService` to handle incoming Kafka messages
   - Update `KafkaConfig.kt` with necessary beans for Kafka integration

4. Testing:
   - Update `application-test.properties` with test-specific PostgreSQL and Kafka configurations
   - Create integration tests for Kafka message production and consumption
   - Update existing tests affected by the change from H2 to PostgreSQL

5. Docker Setup:
   - Update `docker-compose.yml` to include PostgreSQL and Kafka services

6. Documentation:
   - Update README.md and other documentation to reflect the changes

Please refer to the commented-out sections in the code for specific implementation details.

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
- It uses an in-memory H2 database by default for simplicity.
- PostgreSQL configuration is provided but commented out, demonstrating how to set up a production-ready database.
- Kafka integration is demonstrated in the code but not active in the MVP for ease of setup and running.

For any questions or issues, please open an issue in the GitHub repository.