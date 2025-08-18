# syntax=docker/dockerfile:1
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /workspace

# Pre-carico dipendenze di common-files
COPY common-files/pom.xml common-files/pom.xml
RUN mvn -q -f common-files/pom.xml dependency:go-offline
COPY common-files/src common-files/src
RUN mvn -q -f common-files/pom.xml clean install -DskipTests

# Pre-carico dipendenze del servizio
COPY auth-service/pom.xml auth-service/pom.xml
RUN mvn -q -f auth-service/pom.xml dependency:go-offline
COPY auth-service/src auth-service/src

# Build del servizio
RUN mvn -q -f auth-service/pom.xml clean package -DskipTests

# Runtime image
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /workspace/auth-service/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"] 