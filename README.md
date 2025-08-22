# MioBook

## Project Overview

MioBook is a comprehensive project developed for the Internet Engineering course at the University of Tehran. It documents the incremental development of an online e-bookstore, showcasing the evolution of a web application from a monolithic command-line program to a modern, containerized, and scalable service-oriented architecture. Each phase introduces and integrates key technologies and architectural patterns prevalent in modern web development.

## Project Evolution

The repository is structured into distinct phases, each representing a significant milestone in the application's development lifecycle.

### Phase 1: Foundational Logic in Java (CA1)

This initial phase established the foundational domain logic of the application using core Java. A simple hotel management system was implemented as a preliminary exercise to define core models, services, and business logic, laying the groundwork for the subsequent e-bookstore application.

### Phase 2: Core Back-end and Command-Line Interface (CA2)

The core back-end logic for the MioBook e-bookstore was implemented in pure Java. This phase focused on building the service layer and data models necessary for the application's core functionality. A command-line interface (CLI) was developed to facilitate interaction and allow for direct testing of the business logic. To ensure the correctness and reliability of the core components, unit tests were written using the JUnit framework.

### Phase 3: Front-end Implementation with HTML & CSS (CA3)

This phase addressed the user interface by translating Figma design specifications into functional, static web pages. The front-end was developed using **HTML5**, **CSS3**, and the **Bootstrap** framework to ensure a responsive design across a wide range of devices, from mobile viewports (360px) to full desktop screens. The primary focus was on achieving a pixel-perfect implementation of the provided UI/UX designs.

### Phase 4: Transition to Spring REST and React (CA4)

A significant architectural shift was undertaken in this phase to modernize the stack.

* **Back-end**: The back-end was refactored from a monolithic structure to a RESTful API using the **Spring Boot** framework. This involved replacing the traditional Model-View-Controller (MVC) pattern with a REST architecture, exposing resources via JSON-based endpoints. A standardized API response format was established to ensure consistency across the application.

* **Front-end**: The static front-end was re-implemented as a Single-Page Application (SPA) using **React**. Static HTML pages were converted into modular React components, and client-side routing was managed with **React Router**. Asynchronous API communication with the back-end was handled using the browser's **Fetch API**.

### Phase 5: Database Integration with JPA/Hibernate (CA5)

Persistence was introduced by integrating a **MySQL** relational database. The in-memory data structures were replaced with a persistent data layer managed by **Hibernate ORM** and **Spring Data JPA**. Plain Old Java Object (POJO) models were annotated as JPA entities to map them to database tables. Database operations were abstracted through Spring Data JPA repositories, simplifying data access logic. Advanced functionalities such as search and pagination were implemented at the database level to optimize performance. The application was also configured to initialize its state by fetching data from an external API endpoint upon startup.

### Phase 6: Performance Caching with Redis (CA6)

To enhance performance and manage user state efficiently, **Redis** was integrated as an in-memory caching layer. The primary use case was for session management, where user session data is offloaded from the application memory to the Redis data store. This approach improves scalability and resilience, as user sessions are no longer tied to a specific application instance, facilitating horizontal scaling of the back-end service.

### Phase 7: Security with JWT and OAuth 2.0 (CA7)

The application's security posture was significantly strengthened in this phase.

* **Authentication**: The authentication mechanism was migrated to a token-based system using **JSON Web Tokens (JWT)**. Upon successful login, the back-end issues a signed JWT, which the front-end stores and includes in the `Authorization` header of subsequent requests as a Bearer token.
* **Authorization**: The system leverages JWT claims for role-based authorization.
* **Password Security**: Password management was improved by implementing one-way hashing for storing user credentials in the database.
* **Third-Party Login**: Support for federated identity was added by integrating **Google Sign-In** via the **OAuth 2.0** protocol.

### Phase 8: Containerization with Docker (CA8)

This phase focused on containerization to streamline development, deployment, and scalability using **Docker**.

* **Dockerfiles**: Individual `Dockerfiles` were created for the Spring Boot back-end and the React front-end. The front-end container utilizes a multi-stage build with an **Nginx** web server to serve the static assets and proxy API requests to the back-end service, resolving CORS issues and simplifying the network configuration.
* **Docker Compose**: A `docker-compose.yml` file was authored to orchestrate the multi-container application, defining the back-end, front-end, and MySQL database services. The Compose file manages environment variables, secrets, inter-service networking, and health checks, enabling the entire application stack to be launched with a single command.

### Phase 9: Kubernetes (CA9)

The system is deployed on a Kubernetes cluster using **Kind** for local setup. The back-end, front-end, and database services are defined as Kubernetes objects such as Deployments, Services, ConfigMaps, and Secrets. The database uses Persistent Volumes for data storage, while the back-end supports horizontal scaling and load balancing.