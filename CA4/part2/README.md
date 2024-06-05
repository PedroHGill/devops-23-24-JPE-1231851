# CA4, Part 2 - Containers with Docker

## Introduction

This assignment focuses on leveraging Docker to create a containerized environment for a Spring application. 
Throughout this tutorial, you will be guided through the process of setting up Docker containers to run both a 
Tomcat server with the Spring application and an H2 database server.

## Table of Contents

1. [Overview](#overview)
2. [Prerequisites](#prerequisites)
3. [Getting Started](#getting-started)
4. [Dockerfiles](#dockerfiles)
5. [Docker Compose](#docker-compose)
6. [Conclusion](#conclusion)
7. [Alternative](#alternative)
8. [Docker and Kubernetes](#docker-and-kubernetes)

## Overview

In this part of the assignment, you are required to:

-Create and configure Docker containers for a web application and a database.
-Utilize docker-compose to manage multi-container Docker applications.
-Publish Docker images to Docker Hub.
-Use Docker volumes to manage persistent data.

## Prerequisites

Before proceeding with the assignment, ensure that you have the following prerequisites:

1. Docker: Docker must be installed and running on your machine.
    - Installation: Follow the instructions for your operating system from the official Docker documentation: [Docker](https://docs.docker.com/get-docker/)
2. Git: Git must be installed on your machine to clone the repository.
    - Installation: Follow the instructions for your operating system from the official Git documentation: [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
3. Java 17: Java 17 must be installed on your machine to build the chat server application.
    - Installation: Follow the instructions for your operating system from the official OpenJDK documentation: [OpenJDK](https://openjdk.java.net/install/)
4. Gradle: Gradle must be installed on your machine to build the chat server application.
    - Installation: Follow the instructions for your operating system from the official Gradle documentation: [Gradle](https://gradle.org/install/)
5. Docker Hub Account: You must have a Docker Hub account to publish Docker images.
    - Sign up: Create an account on Docker Hub: [Docker Hub](https://hub.docker.com/)

## Getting Started

Begin by copying the contents of the CA2 Part2 project to the same location of your dockerfiles, 
in order for them to copy the contents of the project and correctly run the server.

## Dockerfiles

### Dockerfile-web

```bash
# Use the official Gradle image to build the application
FROM tomcat
RUN apt-get update && apt-get install -y dos2unix
WORKDIR /app

# Copy the project files to the container
COPY . .

# Expose the application port
EXPOSE 8080

RUN dos2unix ./gradlew

# Run the Spring Boot application
ENTRYPOINT ["./gradlew"]
CMD ["bootRun"]
```
To publish the image to Docker Hub, use the following commands:

```bash
docker tag web <your_dockerhub_username>/web
docker push <your_dockerhub_username>/web
```


### Dockerfile-db

```bash
FROM ubuntu

RUN apt-get update && \
  apt-get install -y openjdk-17-jdk-headless && \
  apt-get install unzip -y && \
  apt-get install wget -y

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app/

# Download H2 Database and run it
RUN wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar -O /opt/h2.jar

EXPOSE 8082
EXPOSE 9092

# Start H2 Server
CMD java -cp /opt/h2.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists
```

## Docker Compose
The docker-compose file is responsible for orchestrating the two images and running the containers. It defines the 
services for both the web application and the H2 database, sets up volume configurations.
Below is the Docker-compose file:

```bash
version: '3.8'

services:
  db:
    build:
      context: .
      dockerfile: Dockerfile-db
    container_name: db
    ports:
      - "8082:8082"
      - "9092:9092"
    volumes:
      - h2-data:/app/db-backup

  web:
    build:
      context: .
      dockerfile: Dockerfile-web
    container_name: web
    ports:
      - "8080:8080"
    depends_on:
      - db

volumes:
  h2-data:
    driver: local
```

To run the docker compose file, execute the following command:
```bash
docker-compose up --build
```
The web application will be accessible at `http://localhost:8080`.
The H2 database will be accessible at `http://localhost:8082`.


## Conclusion

The provided Docker Compose configuration orchestrates two services: a web application and an H2 database.
The web application service (web) is built from a Dockerfile named Dockerfile-web, exposing port 8080. It depends on the db service.
The H2 database service (db) is built from a Dockerfile named Dockerfile-db, exposing ports 8082 and 9092. It also mounts 
a volume named h2-data for storing database backups.
When you run docker-compose up --build, Docker Compose will build and start the containers for both services. 
The web application will be accessible at http://localhost:8080, while the H2 database management console will be 
accessible at http://localhost:8082.
This setup allows you to easily manage and deploy both your web application and database in a containerized environment, 
providing isolation, reproducibility, and scalability for your application stack.

## Alternative   

An alternative solution for this assignment is to deploy the app and the database in a Kubernetes cluster. This setup 
enhances scalability and availability. Each component (app and database) is deployed in separate pods, with the app 
connecting to the database via its service name.

After pushing the Docker images to Docker Hub, create Kubernetes deployment and service files for the app and the database. 
Below are examples of these files:

### Web App Deployment File
```bash
apiVersion: apps/v1
kind: Deployment
metadata:
  name: CA4-Part2-web
spec:
  replicas: 1
  selector:
    matchLabels:
      app: CA4-Part2-web
  template:
    metadata:
      labels:
        app: CA4-Part2-web
    spec:
      containers:
        - name: CA4-Part2-web
          image: <dockerhub_username>/devops_23_24:CA4_Part2_web
          ports:
            - containerPort: 8080
```

### Web App Service File
```bash
apiVersion: v1
kind: Service
metadata:
  name: CA4-Part2-web
spec:
  type: NodePort
  ports:
    - port: 8080
      targetPort: 8080
      nodePort: 30080
  selector:
    app: ca4-part2-web
```

### Database Deployment File
```bash
apiVersion: apps/v1
kind: Deployment
metadata:
  name: CA4-Part2-db
spec:
  replicas: 1
  selector:
    matchLabels:
      app: CA4-Part2-db
  template:
    metadata:
      labels:
        app: CA4-Part2-db
    spec:
      containers:
        - name: CA4-Part2-db
          image: <dockerhub_username>/devops-23-24-JPE-1231851:CA4_Part2_db
          ports:
            - containerPort: 8082
            - containerPort: 9092
```

### Database Service File
```bash
apiVersion: v1
kind: Service
metadata:
  name: CA4-Part2-db
spec:
  type: NodePort
  ports:
    - port: 8082
      targetPort: 8082
      nodePort: 30082
    - port: 9092
      targetPort: 9092
      nodePort: 30092
  selector:
    app: CA4-Part2-db
```

The use of Kubernetes provides a declarative approach to manage the desired state of the cluster via YAML files, 
ensuring better management, scalability, and availability. Docker and Kubernetes complement each other; Docker builds 
the images, and Kubernetes deploys and manages the containers, improving separation of concerns and management efficiency.

## Docker and Kubernetes

In summary, Docker is used for building and running containers, while Kubernetes is used for orchestrating and managing 
clusters of containers. They complement each other, with Docker providing the containerization technology and Kubernetes 
providing the orchestration and management capabilities. Many organizations use both Docker and Kubernetes together as 
part of their containerization and microservices strategies.
