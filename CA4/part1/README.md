# CA4, Part 1 - Containers with Docker

## Introduction

This assignment focuses on using Docker to containerize an application, building Docker images, and running Docker 
containers. As part of Class Assignment 4, Part 1, the main task is to work with the chat application from CA2, creating 
two different Docker images and running the chat server in a container. This process helps in understanding the 
fundamental concepts of Docker, including Dockerfiles, images, and containers, as well as practicing Docker commands 
and exploring Docker Hub for image publishing.

## Table of Contents

1. [Overview](#overview)
2. [Prerequisites](#prerequisites)
3. [Version 1: Building Chat Server Inside Docker Container](#version-1-building-chat-server-inside-docker-container)
4. [Version 2: Building Chat Server on Host Machine](#version-2-building-chat-server-on-host-machine)
5. [Conclusion](#conclusion)

## Overview

In this part of the assignment, you are required to:

- Create a Docker image for the chat server application.
- Run the chat server inside a Docker container.
- Publish the Docker image to Docker Hub.
- Connect to the chat server using a chat client running on the host machine.

Two distinct versions of the Docker images will be created:

1. **Version 1**: The chat server is built inside the Docker container using a Dockerfile.
2. **Version 2**: he chat server is built on the host machine, and the built JAR file is copied into the Docker container using a Dockerfile.

The repository for this project is: https://github.com/PedroHGill/devops-23-24-JPE-1231851.git

In the following sections, detailed instructions for both versions will be provided, including Dockerfile contents, 
commands to build and run Docker images, and steps to publish the images to Docker Hub. This documentation aims to guide 
you through the process and ensure reproducibility of the assignment by following the tutorial-style instructions.

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

## Version 1: Building Chat Server Inside Docker Container

Dockerfile
```bash
# Use openjdk:17-jdk-slim as the base image
FROM openjdk:17-jdk-slim

# Install dependencies
RUN apt-get update && \
    apt-get install -y openjdk-17-jre-headless git dos2unix && \
    apt-get clean

# Set the working directory to /app
WORKDIR /app

# Clone the repository containing the project
RUN git clone https://github.com/PedroHGill/devops-23-24-JPE-1231851.git .

# Navigate to the CA2/part2 directory
WORKDIR /app/CA2/part1

# Modify gradlew permissions and convert to Unix format
RUN dos2unix ./gradlew && chmod +x ./gradlew

# Build the application
RUN ./gradlew build

# Expose port 8080 for the chat server
EXPOSE 8080

# Run the chat server
CMD ["./gradlew", "runServer"]
 ```

## Running the Server

1. To create the image, navigate to the directory containing the Dockerfile and run:
```bash
docker build -t ca4-part1v1 . 
```
* The `-t` flag tags the image with a name. Ensure to include the dot at the end to specify the Dockerfile's location.

2. To run the container, execute the following command:
```bash
ocker run --name ca4-part1v1 -p 8080:59001 ca4-part1v1
```
3. To start the chat client, got to the directory containing the chat client (CA2/part2) and run:
```bash
./gradlew runClient
```
4. To push the image to Docker Hub, use the following commands:
```bash
docker tag ca4-part1v1 <your_dockerhub_username>/ca4-part1v1
docker push <your_dockerhub_username>/ca4-part1v1
```

## Version 2: Building Chat Server on Host Machine

```bash
# Use openjdk:17-jdk-slim as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the image to /app
WORKDIR /app

# Copy the jar file from your host machine into the Docker image
COPY basic_demo-0.1.0.jar /app

EXPOSE 8080

# Set the command to run when the Docker container starts
CMD ["java", "-cp", "basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "8080"]
```
1. To build the project and create the JAR file, navigate to the project directory (CA2/part1) and run:
```bash
./gradlew build
```
2. Copy the JAR file from the build/libs directory to the directory containing the Dockerfile:
```bash
cd ../CA2
cp ./part1/build/libs/basic_demo-0.1.0.jar ../CA4/part1/v2
```
3. To create the image, navigate to the directory containing the Dockerfile (CA4/part1/v2) and run:
```bash
docker build -t ca4-part1v2 . 
```
4. To run the container, execute the following command:
```bash
docker run --name ca4-part1v2 -p 8080:8080 ca4-part1v2
```
5. To push the image to Docker Hub, use the following commands:
```bash
docker tag ca4-part1v2 <your_dockerhub_username>/ca4-part1v2
docker push <your_dockerhub_username>/ca4-part1v2
```

## Conclusion

In conclusion, Class Assignment 4, Part 1, focuses on containerizing a chat application using Docker. The assignment 
involves creating two distinct Docker images for the chat server application, each using a different approach.

Version 1 involves building the chat server inside a Docker container using a Dockerfile. This approach includes cloning 
the project repository, setting up the environment, building the application within the container, and running the chat 
server.

Version 2 entails building the chat server on the host machine and then copying the built JAR file into the Docker 
container using a Dockerfile. This method requires building the project outside of Docker, copying the resulting JAR 
file into the Docker image, and configuring the container to run the server using the copied JAR file.







