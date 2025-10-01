# Build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17
WORKDIR /app
COPY --from=build ./build/target/*.jar gerenciamento.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","gerenciamento.jar"]