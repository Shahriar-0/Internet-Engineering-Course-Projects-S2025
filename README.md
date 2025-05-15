# MioBook

## Description

This repository contains the projects of the Internet Engineering course at the University of Tehran.
MioBook is an online e-bookstore where users can buy or borrow books with ease.

## Phases

### CA1: Warm-up

Just a simple hotel management system written in Java for warm-up.

### CA2: Back-end Logic

The basic back-end logic of the system is implemented in Java.
A command-line interface is provided for the user to interact with the system.
Some JUnit tests are also written to assure correctness.

### CA3: HTML & CSS

The views are redesigned using HTML and CSS.
The given Figma designs are implemented as closely as possible.
Static web pages are created using Bootstrap.
The pages are fully responsive from mobile devices (360px width) to desktop screens.

### CA4: Spring & React

The back-end is rewritten using the Spring REST framework.
The MVC pattern is replaced with RESTful architecture, having JSON endpoints for resources.
A custom response format is defined for the API.

The front-end is implemented using React.
The previous static pages have been replaced with React components.
React Router is used for navigation between pages and API calls are made using fetch.

### CA5: Database & ORM

The system is connected to a MySQL database.
The models now have JPA annotations, and Hibernate ORM is used for database operations.
Database queries are made using the Spring Data JPA repository.
Searching restaurants and paging are handled in the database.
Initial application data is read from a given endpoint.

### CA6: Redis for Caching

The system now uses Redis for caching user sessions.
The session data is stored in Redis and retrieved when needed.

