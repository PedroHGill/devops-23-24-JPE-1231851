FROM openjdk:17-jdk-slim

WORKDIR /app

COPY *.jar /app

EXPOSE 8080

CMD ["java", "-cp", "basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "8080"]

## docker build -t ca4-part1v2 .
## docker run --name ca4-part1v2 -p 8081:59001 ca4-part1v2