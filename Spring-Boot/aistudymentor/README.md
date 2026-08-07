# AI Study Mentor

AI Study Mentor is a Spring Boot application designed to support students with AI-assisted learning. It provides endpoints for authentication, subject browsing, quiz generation, and question history management.

## Features

- User registration and login
- JWT-based authentication
- Subject listing
- AI-powered chat assistant for study questions
- Quiz generation based on subject and user education level
- Question history storage and retrieval

## Tech Stack

- Java 17
- Spring Boot 4.1.0
- Spring Security
- Spring Data JPA
- MySQL
- H2 (for tests)
- Maven
- JaCoCo for test coverage
- Spring AI OpenAI integration
- Swagger UI / OpenAPI

## Project Structure

```text
src/
  main/
    java/
      com/_anhtai/aistudymentor/
        config/
        dao/
        dto/
        entity/
        exception/
        filter/
        repositoy/
        restcontroller/
        service/
        utils/
    resources/
      application.properties
      application-test.properties
  test/
    java/
      com/_anhtai/aistudymentor/
        integration/
        service/
```

## Prerequisites

Make sure you have the following installed:

- Java 17 or newer
- Maven
- MySQL Server

## Configuration

The application reads configuration from:

- [src/main/resources/application.properties](src/main/resources/application.properties)
- [src/main/resources/application-test.properties](src/main/resources/application-test.properties)

### Main environment variables

Set these values before running the application:

- `JWT_SECRET_KEY`: secret key for JWT signing
- `OPENAI_API_KEY`: API key for the AI model integration

Example:

```bash
export JWT_SECRET_KEY=your-secret-key
export OPENAI_API_KEY=your-openai-key
```

## Running the Application

### 1. Start MySQL

Make sure a MySQL database is available and matches the values in the configuration file.

### 2. Run the app

```bash
./mvnw spring-boot:run
```

The app will start on the default Spring Boot port.

## API Overview

### Authentication

- `POST /api/auth/register` – register a new user
- `POST /api/auth/login` – authenticate and receive a JWT token

### Subjects

- `GET /api/subject/allsubjects` – get all available subjects

### Questions

- `POST /api/question/ask` – ask a study question
- `POST /api/question/quiz` – generate a quiz
- `GET /api/question/questions` – view question history

### Swagger UI

Swagger documentation is available at:

- `/my-ui.html`
- `/my-api-docs`

## Testing

Run the full test suite with:

```bash
./mvnw test
```

The project uses:

- JUnit 5
- Mockito
- Spring Boot Test
- H2 for integration testing
- JaCoCo for coverage reporting

## Coverage

Coverage reports are generated under:

- `target/site/jacoco/index.html`

## Notes

- The test profile uses H2 in memory so the application can be tested without a live MySQL instance.
- The default application properties provide fallback values for local development.

## License

This project is for educational purposes.
