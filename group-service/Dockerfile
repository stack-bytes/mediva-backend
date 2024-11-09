# Use an official OpenJDK 21 image as the base image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the application jar to the container
COPY target/group-service-0.0.1-SNAPSHOT.jar  /app/group-service.jar

# Expose the port that the service will run on
EXPOSE 8087

# Run the application
CMD ["java", "-jar", "/app/group-service.jar"]
