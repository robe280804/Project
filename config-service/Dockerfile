# syntax=docker/dockerfile:1
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /workspace

COPY config-service/pom.xml config-service/pom.xml
RUN mvn -q -f config-service/pom.xml dependency:go-offline
COPY config-service/src config-service/src

RUN mvn -q -f config-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /workspace/config-service/target/*.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","/app/app.jar"] 