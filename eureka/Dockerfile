# syntax=docker/dockerfile:1
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /workspace

COPY eureka/pom.xml eureka/pom.xml
RUN mvn -q -f eureka/pom.xml dependency:go-offline
COPY eureka/src eureka/src

RUN mvn -q -f eureka/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /workspace/eureka/target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/app/app.jar"] 