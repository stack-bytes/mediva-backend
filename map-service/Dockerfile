# Use an official OpenJDK 21 image as the base image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the application jar to the container
COPY target/map-service-0.0.1-SNAPSHOT.jar  /app/map-service.jar

# Expose the port that the service will run on
EXPOSE 8086

# Run the application
CMD ["java", "-jar", "/app/map-service.jar"]
