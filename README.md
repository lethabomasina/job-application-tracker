# Job Application Tracker API

A RESTful API built with Spring Boot that allows users to manage and track their job applications.

## Overview

The Job Application Tracker API provides a way to create, retrieve, update, and delete job application records.

Each application contains information such as the company name, job position, application status, location, and notes.

This project is being developed as a portfolio project to demonstrate skills in Java, Spring Boot, REST API development, and database integration.

## Technologies

* **Java** — Programming language
* **Spring Boot** — Application framework
* **Spring Web** — REST API development
* **Spring Data JPA** — Database access and persistence
* **Hibernate** — ORM and database mapping
* **PostgreSQL** — Relational database
* **Maven** — Dependency management and build tool
* **Jakarta Bean Validation** — Request validation

## Features

* Create new job applications.
* Retrieve all job applications.
* Retrieve a specific application by ID.
* Update existing applications.
* Delete applications.
* Validate incoming requests.
* Return appropriate HTTP status codes for successful operations and errors.
* Handle exceptions centrally.

## Application Statuses

Each job application can have one of the following statuses:

| Status      | Description           |
| ----------- | --------------------- |
| `APPLIED`   | Application submitted |
| `INTERVIEW` | Interview stage       |
| `OFFER`     | Offer received        |
| `REJECTED`  | Application rejected  |
| `WITHDRAWN` | Application withdrawn |

## API Endpoints

Base URL: `http://localhost:8080`

| Method   | Endpoint             | Description                   |
| -------- | -------------------- | ----------------------------- |
| `POST`   | `/applications`      | Create an application         |
| `GET`    | `/applications`      | Retrieve all applications     |
| `GET`    | `/applications/{id}` | Retrieve an application by ID |
| `PUT`    | `/applications/{id}` | Update an application         |
| `DELETE` | `/applications/{id}` | Delete an application         |

### HTTP Status Codes

| Status            | Meaning                   |
| ----------------- | ------------------------- |
| `200 OK`          | Request successful        |
| `201 Created`     | Application created       |
| `204 No Content`  | Application deleted       |
| `400 Bad Request` | Request validation failed |
| `404 Not Found`   | Application not found     |

## Installation and Setup

### Prerequisites

Make sure you have the following installed:

* Java 21
* PostgreSQL
* Git

### 1. Clone the Repository

```bash
git clone https://github.com/lethabomasina/job-application-tracker.git
cd job-application-tracker
```

### 2. Create the Database

Open PostgreSQL and create a database for the project:

```sql
CREATE DATABASE job_tracker;
```

### 3. Configure the Database Connection

Configure the database connection in `src/main/resources/application.properties`.

Set the following properties using your local PostgreSQL credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/job_tracker
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

Set the `DB_USERNAME` and `DB_PASSWORD` environment variables on your machine before starting the application.

**Never commit your actual database password to the repository.**

### 4. Run the Application

On macOS/Linux:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The API will be available at:

`http://localhost:8080`

## Validation

The API enforces the following validation rules:

| Field      | Requirement               |
| ---------- | ------------------------- |
| `company`  | Required; cannot be blank |
| `position` | Required; cannot be blank |
| `status`   | Required                  |
| `location` | Optional                  |
| `notes`    | Optional                  |

Invalid requests return a `400 Bad Request` response with validation error messages.

## Project Status

The project is under active development. Additional features and documentation will be added as development progresses.
