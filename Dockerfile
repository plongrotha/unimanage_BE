# Use official OpenJDK image as base
FROM eclipse-temurin:21-jdk-alpine AS build

# Set working directory
WORKDIR /app

# Copy Maven/Gradle files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
# If using Gradle, copy: build.gradle settings.gradle gradlew gradle/

# Copy source code
COPY src src

# Build the application (skip tests for faster builds)
RUN ./mvnw clean package -DskipTests
# For Gradle: RUN ./gradlew build -x test

# Production stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the built jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
