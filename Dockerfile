# Use a base image that has Java installed
FROM openjdk:17-jdk-slim AS build

# Set working directory
WORKDIR /app

# Copy Gradle wrapper and Gradle configuration files
COPY gradle /app/gradle
COPY gradlew /app/

# Copy application code
COPY src /app/src

# Copy build.gradle and settings.gradle
COPY build.gradle /app/
COPY settings.gradle /app/

# Grant execute permission to gradlew
RUN chmod +x gradlew

# Build the application
RUN ./gradlew build -x test

# Use a minimal base image for the final application
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port on which the application will run
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
