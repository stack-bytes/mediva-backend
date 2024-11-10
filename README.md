# 🏢 **Mediva Backend Microservices Monorepo**

This is the monorepo for the **Mediva Backend** project, which houses all the microservices. The services are organized by feature and contain individual Dockerfiles, configurations, and service logic.

## 🚀 **Microservices**

### 📅 **Appointment Service**
- **Description:** Manages appointments and related services.
- **Folder Structure:**
    - `Dockerfile` - Defines the Docker image for the service.
    - `pom.xml` - Maven configuration for building the service.
    - `src/main/java/com/stackbytes/`
        - **AppointmentMain.java** - Main entry point for the service.
        - **controller/** - Handles the HTTP request/response.
        - **model/** - Contains domain models (e.g., `Appointment.java`).
        - **service/** - Business logic related to appointments.
    - `resources/application.properties` - Service configuration.

### 📱 **Group Service**
- **Description:** Manages groups and associated functionalities.
- **Folder Structure:**
    - `Dockerfile` - Dockerfile for building the service container.
    - `pom.xml` - Maven configuration for the service.
    - `src/main/java/com/stackbytes/`
        - **GroupServiceMain.java** - Main service class.
        - **controllers/** - API controllers.
        - **model/** - Domain models for groups.
    - `resources/application.properties` - Service configuration.

### 🤕 **Illness Service**
- **Description:** Handles illness-related data and requests.
- **Folder Structure:**
    - `Dockerfile` - Builds the Docker container for the service.
    - `pom.xml` - Maven build configuration.
    - `src/main/java/com/stackbytes/`
        - **IllnessMain.java** - Entry point for the service.
        - **controller/** - Endpoints for illness management.
        - **model/** - Models for illnesses (e.g., `Illness.java`).
    - `resources/application.properties` - Configuration for the service.

### 🗺️ **Map Service**
- **Description:** Provides map-related services.
- **Folder Structure:**
    - `Dockerfile` - For building the service Docker image.
    - `pom.xml` - Maven configuration for building the service.
    - `src/main/java/com/stackbytes/`
        - **controller/** - Map-related controllers.
        - **model/** - Map-related models.
    - `resources/application.properties` - Configuration for map services.

---

## 🛠️ **Containers**

- **Gateway:** The entry point for routing requests between services.
    - `Dockerfile` - Gateway container.
    - `nginx.conf` - NGINX configuration for routing.

- **Shared Storage:** A service used to store shared data.
    - `Dockerfile` - Shared storage container.

## ⚙️ **Common Configurations**

- `compose.yaml` - Docker Compose configuration for spinning up the entire backend stack.
- `HELP.md` - Documentation for setting up and using the services.

---
### 🐳 **Docker**
All services are containerized using Docker for easy deployment.

### 🧩 **Microservice Communication**
The services communicate with each other over RESTful APIs and/or message brokers, depending on the requirements.

### ⚡ **Build Tools**
Maven is used as the build tool for all services in this monorepo.

---

## 🌍 **Service Architecture**
This repository follows a microservices architecture. Each service is developed, deployed, and scaled independently.

- 🐋 Dockerized
- 🚀 Scalable via Kubernetes
- 🛠️ Configurable via `application.properties`
