# syntax=docker/dockerfile:1
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /workspace

# Pre-carico dipendenze di common-files
COPY common-files/pom.xml common-files/pom.xml
RUN mvn -q -f common-files/pom.xml dependency:go-offline
COPY common-files/src common-files/src
RUN mvn -q -f common-files/pom.xml clean install -DskipTests

# Pre-carico dipendenze del servizio
COPY activity-service/pom.xml activity-service/pom.xml
RUN mvn -q -f activity-service/pom.xml dependency:go-offline
COPY activity-service/src activity-service/src

# Build del servizio
RUN mvn -q -f activity-service/pom.xml clean package -DskipTests

# Runtime image
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /workspace/activity-service/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"] 