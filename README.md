# Event & Ticket Reservation System

A full-stack event and ticket reservation application built with **Spring Boot**, **PostgreSQL**, **Spring Security**, **JWT**, and **Angular**.

The project implements event management, seat reservation, payment simulation, ticket generation, role-based authorization, reservation expiration, and concurrency control.

---

## Features

### Authentication & Authorization

- User registration and login
- JWT-based authentication
- BCrypt password hashing
- Role-based authorization
- `USER` and `ADMIN` roles
- Resource ownership checks

### Event Management

- Create and list venues
- Create and manage seats
- Create and list events
- Assign seats to events
- Event-specific seat pricing
- Event-specific seat availability

### Reservation System

- Reserve multiple seats
- Automatic total price calculation
- 10-minute reservation period
- Reservation statuses:
    - `PENDING`
    - `CONFIRMED`
    - `CANCELLED`
    - `EXPIRED`
- Automatic release of expired reservations
- Manual reservation cancellation

### Seat Management

Event seats support the following states:

```text
AVAILABLE
    ↓
RESERVED
    ↓
SOLD
```

If a reservation is cancelled or expires:

```text
RESERVED
    ↓
AVAILABLE
```

### Payment

The project currently uses a simulated payment flow.

Successful payment:

```text
Payment → PAID
Reservation → CONFIRMED
EventSeat → SOLD
Ticket → VALID
```

Failed payment:

```text
Payment → FAILED
Reservation → PENDING
EventSeat → RESERVED
```

### Ticket Management

- Automatic ticket generation after successful payment
- Unique ticket codes using UUID
- Ticket ownership validation
- Ticket statuses:
    - `VALID`
    - `CANCELLED`

### Reservation Expiration

Reservations are valid for **10 minutes**.

A scheduled background task periodically checks expired reservations.

When a reservation expires:

```text
Reservation
PENDING → EXPIRED

EventSeat
RESERVED → AVAILABLE
```

### Concurrency Control

The project uses JPA optimistic locking with:

```java
@Version
```

This helps prevent multiple users from reserving the same seat concurrently.

Concurrent update conflicts are returned as:

```text
409 Conflict
```

---

## Tech Stack

### Backend

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Spring Security
- JWT
- Jakarta Validation
- PostgreSQL
- Maven
- Lombok

### Frontend

- Angular
- TypeScript
- SCSS
- Angular Router
- HTTP Interceptors
- Route Guards
- AI-assisted implementation

---

## Frontend Note

The Angular frontend was generated with AI assistance.

The primary development focus of this project was the backend, including:

- REST API design
- Spring Boot layered architecture
- Spring Data JPA and Hibernate
- DTO / Entity separation
- Validation and exception handling
- Spring Security and JWT authentication
- Role-based authorization
- Resource ownership checks
- Reservation and payment workflows
- Scheduled reservation expiration
- Transaction management
- Optimistic locking and concurrency control

The AI-generated frontend was used to provide a functional user interface and to integrate with the backend APIs.

---

## Architecture

The backend follows a layered architecture:

```text
Client
  │
  ▼
Security Filter
  │
  ▼
Controller
  │
  ▼
Service
  │
  ├── Mapper
  │
  ▼
Repository
  │
  ▼
Entity
  │
  ▼
PostgreSQL
```

Main backend packages:

```text
config
controller
dto
entity
enums
exception
mapper
repository
scheduler
security
service
```

---

## Domain Model

Main entities:

```text
User
Venue
Seat
Event
EventSeat
Reservation
ReservationItem
Payment
Ticket
```

Main relationships:

```text
Venue
 ├── Seat
 └── Event

Event
 └── EventSeat
      └── Seat

User
 └── Reservation
      ├── ReservationItem
      │    ├── EventSeat
      │    └── Ticket
      └── Payment
```

`EventSeat` is used instead of a direct many-to-many relationship between `Event` and `Seat` because event-specific information such as:

```text
price
status
version
```

must also be stored.

---

## Security Flow

```text
Login
  │
  ▼
AuthenticationManager
  │
  ▼
UserDetailsService
  │
  ▼
Password Verification
  │
  ▼
JWT Generation
```

Authenticated requests:

```text
HTTP Request
     │
     ▼
JWT Authentication Filter
     │
     ▼
JWT Validation
     │
     ▼
SecurityContext
     │
     ▼
@PreAuthorize
     │
     ▼
Controller
```

The project uses three levels of security:

```text
Authentication
    ↓
Role Authorization
    ↓
Resource Ownership
```

---

## API Overview

### Authentication

```http
POST /api/auth/register
POST /api/auth/login
```

### Events

```http
POST   /api/events
GET    /api/events
GET    /api/events/{id}
DELETE /api/events/{id}
```

### Event Seats

```http
POST /api/event-seats
GET  /api/event-seats/event/{eventId}/status/{status}
```

### Reservations

```http
POST  /api/reservations
GET   /api/reservations/{id}
PATCH /api/reservations/{id}/cancel
```

### Payments

```http
POST /api/payments
```

### Tickets

```http
GET   /api/tickets/{id}
GET   /api/tickets/ticket-code/{ticketCode}
PATCH /api/tickets/{id}/status
```

---

## Configuration

The backend uses environment variables for sensitive configuration.

`application.properties`:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION:86400000}
```

Required environment variables:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
JWT_SECRET
```

Optional:

```text
JWT_EXPIRATION
```

---

## Running the Backend

Clone the repository:

```bash
git clone https://github.com/acercagri/ticket-reservation-system.git
```

Enter the project:

```bash
cd ticket-reservation-system
```

Set the required environment variables and run:

### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

The backend runs by default on:

```text
http://localhost:8080
```

---

## Running the Frontend

Go to the frontend directory:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Run Angular:

```bash
npm start
```

or:

```bash
ng serve
```

---

## Project Goals

This project was developed to practice and apply:

- Layered backend architecture
- REST API design
- DTO / Entity separation
- Spring Data JPA
- Entity relationships
- Business rule implementation
- Transaction management
- Spring Security
- JWT authentication
- Role-based authorization
- Resource ownership
- Scheduled background jobs
- Optimistic locking
- Concurrency handling
- Angular and Spring Boot integration

The backend was the main development focus of the project. The Angular frontend was generated with AI assistance and integrated with the backend APIs.

---

## Future Improvements

Possible improvements include:

- Refresh token support
- Real payment provider integration
- Swagger / OpenAPI documentation
- Email notifications
- QR-code tickets
- Automated integration tests
- Docker support
- Deployment pipeline

---

## Author

**Muhammed Çağrı Acer**
