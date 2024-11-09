
# Use an official OpenJDK 21 image as the base image
FROM openjdk:21-jdk-slim

# Copy the application jar to the container
COPY target/illness-service-0.0.1-SNAPSHOT.jar  /app/illness-service.jar

EXPOSE 8082

CMD ["java", "-jar", "/app/illness-service.jar"]
