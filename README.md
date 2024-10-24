# user_accreditation
User Accreditation (Take-Home Challenge)

A Spring Boot application that manages user accreditation statuses. Built as part of a take-home challenge, this service provides endpoints for creating and managing user accreditations with automatic expiration functionality.

Author: Francesco Borg Bonello

## Table of Contents
1. [Running the Application](#running-the-application)

2. [Architecture Overview (Question A)](#architecture-overview-question-a)

3. [Audit Logging Implementation (Question B)](#audit-logging-implementation-overview-question-b)

4. [Scaling Solutions (Question C)](#scaling-solutions-overview-question-c)

## Running the Application

On Linux/MacOS:
```bash
chmod +x run.sh  
./run.sh      
```

On Windows:
```bash
run.bat      
```

## Architecture Overview (Question A)

### Layered Architecture
The application follows a traditional layered architecture pattern:
- **Controller Layer**: REST endpoints for user and admin operations
- **Service Layer**: Business logic and validation rules
- **Repository Layer**: Data access using JPA repositories
- **Domain Layer**: Entities and value objects

### Design Patterns
- **Repository Pattern**: Spring Data JPA repositories for data access abstraction
- **Builder Pattern**: Using Lombok's @Builder for DTOs
- **DTO Pattern**: Separate request/response objects for API contracts
- **Scheduler Pattern**: Background job for automatic accreditation expiration

### Key Components

#### REST Controllers
- Admin endpoints for accreditation management
- User endpoints for viewing accreditation status
- Input validation using Jakarta Bean Validation

#### Service Layer
- Business logic implementation
- Status transition rules
- Validation rules (e.g., preventing duplicate PENDING requests)

#### Scheduled Jobs
- Automatic expiration of CONFIRMED accreditations
- Scheduling using Spring's @Scheduled

#### Exception Handling
- Global exception handler
- Custom exceptions for business rules
- Consistent error responses

### Technologies
- Spring Boot
- Spring Data JPA
- H2 Database (in-memory)
- Lombok
- Spring Scheduler

### Data Storage
- In-memory H2 database
- JPA entities with auditing (creation/modification timestamps)
- No persistence between restarts (as per requirements)

## Audit Logging Implementation Overview (Question B)

Currently we do not keep track of historical changes. To do so, we need to keep a log for each `AccreditationStatus` change. 

Firstly we would need a new `Entity`: `AccreditationStatusAudit` in which an object would be created each time the current service layer detects a status update. This entity would hold data such as:
- the time the status was updated,
- the admin id updating the status
- the reason for the status update
- the status prior, and the status after
- a foreign key pointing to the accreditation entity identifier

That being said, an entity-specific repository would need to be added to the existing project.

Implementation considerations:
- Maintaining audit logs can be cumbersome in the long run. For that reason, we would need to
    - Add appropriate indexes for efficient querying of audit history
    - Consider implementing a retention policy for managing audit log growth
- Use database transactions to ensure both the status update and audit log creation happen atomically

## Scaling Solutions Overview (Question C)

To handle increased traffic to the GET endpoint we can:

1. Caching:

Use Redis or Spring's built-in caching to store frequently accessed accreditation statuses in order to reduce database load for repeated requests. Depending on the load we can configure the cache to expire based on system resources.

2. Load balancing:

Deploy multiple instances of the service to distribute traffic across multiple servers. This horizontal scaling approach ensures that no single instance becomes overwhelmed.
