FROM openjdk:17-jdk-slim

RUN apt-get update && \
    apt-get install -y openjdk-17-jre-headless git dos2unix && \
    apt-get clean

WORKDIR /app

RUN git clone https://github.com/PedroHGill/devops-23-24-JPE-1231851.git .

WORKDIR /app/CA2/part1

RUN dos2unix ./gradlew && chmod +x ./gradlew

RUN ./gradlew build

EXPOSE 8080

CMD ["./gradlew", "runServer"]

## docker build -t ca4-part1v1 .
## docker build --progress=plain --no-cache -t ca4-part1v1 .
## docker run --name ca4-part1v1 -p 8080:59001 ca4-part1v1