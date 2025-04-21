    FROM maven:3.8.8-eclipse-temurin-17
    WORKDIR /app

    COPY pom.xml ./
    COPY .mvn .mvn

    RUN mvn dependency:resolve

    COPY . .

    EXPOSE 8080

    CMD ["mvn", "spring-boot:run"]