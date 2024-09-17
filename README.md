# Backend Developer Challenge: User Management System

This project is a simple backend application developed using Kotlin and Spring Boot. It includes functionalities for user management with asynchronous communication and automated testing.

## Features

- User registration and querying
- Custom SQL queries for advanced user searches
- Asynchronous notifications using Kafka
- Automated tests using JUnit and MockK
- Monitoring using Micrometer and Prometheus, including custom metrics

... (previous content remains the same)

## Running Tests

To run the automated tests, use the following command:

```
mvn test
```

## Monitoring

The application exposes Prometheus metrics at the `/actuator/prometheus` endpoint. You can configure your Prometheus server to scrape metrics from this endpoint.

Custom metrics include:
- `user.created`: A counter that increments each time a new user is created


## Prerequisites

- JDK 11 or later
- Maven 3.6 or later
- PostgreSQL
- Kafka

## Configuration

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/user-management.git
   cd user-management
   ```

2. Configure the database connection in `src/main/resources/application.properties`:
   ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/usermanagement
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. Configure Kafka in `src/main/resources/application.properties`:
   ```
   spring.kafka.bootstrap-servers=localhost:9092
   spring.kafka.consumer.group-id=user-management-group
   kafka.topic.user-creation=user-creation-topic
   ```

## Running the Application

1. Build the project:
   ```
   mvn clean install
   ```

2. Run the application:
   ```
   mvn spring-boot:run
   ```

The application will start and be available at `http://localhost:8080`.

## API Endpoints

- Create User: POST `/api/users`
- Get User by ID: GET `/api/users/{id}`
- Get All Users: GET `/api/users`
- Get User by Email: GET `/api/users/email/{email}`

## Running Tests

To run the automated tests, use the following command:

```
mvn test
```

## Monitoring

The application exposes Prometheus metrics at the `/actuator/prometheus` endpoint. You can configure your Prometheus server to scrape metrics from this endpoint.

## Docker (Optional)

If you prefer to run the application using Docker, follow these steps:

1. Build the Docker image:
   ```
   docker build -t user-management .
   ```

2. Run the Docker container:
   ```
   docker run -p 8080:8080 user-management
   ```

Note: Make sure to adjust the database and Kafka connection settings in the `application.properties` file or provide them as environment variables when running the Docker container.

## Additional Notes

- This project uses Clean Code principles and appropriate design patterns.
- The asynchronous notification system uses Kafka to send messages when a new user is created.
- Basic monitoring is implemented using Spring Boot Actuator and Micrometer, with Prometheus export capabilities.

For any questions or issues, please open an issue in the GitHub repository.