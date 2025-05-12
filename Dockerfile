FROM maven:3.8.8-eclipse-temurin-17
WORKDIR /app

LABEL authors="lopezs.dev"

COPY pom.xml .
RUN mvn dependency:resolve

CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.fork=false"]
