FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests


# Run stage
FROM eclipse-temurin:21-jdk-jammy AS run
WORKDIR /app

# Copy jar từ build stage
COPY --from=build /app/target/qlnt-be-0.0.1-SNAPSHOT.jar qlnt-be.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","qlnt-be.jar"]