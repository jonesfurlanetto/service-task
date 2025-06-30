# Service Task – Full Stack Application

This repository contains a full stack service management system, composed of:

- [`service-manager`](./service-manager): Angular frontend application
- [`services`](./services): Spring Boot backend API with MongoDB

Both services are fully containerized with Docker and can be run locally using Docker Compose.

## Getting Started

### Prerequisites

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Java 21](https://openjdk.org/projects/jdk/21/) (for local backend builds)
- [Node.js and npm](https://nodejs.org/) (for local frontend dev)

---

## Running the Full Stack (Docker)

To run both backend and frontend services using Docker:

### On **Linux/macOS**:

```bash
./start-all.sh
```

### On **Windows**:

```bash
./start-all.bat
```

#### Once started:

Frontend: http://localhost:4200

Backend API: http://localhost:8080

Swagger UI: http://localhost:8080/swagger-ui/index.html

### To stop all services:

```bash
./stop-all.sh     # Linux/macOS
```

```bash
./stop-all.bat    # Windows
```