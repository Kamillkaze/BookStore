# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the host machine to the container
COPY app.jar /app/app.jar

# Tried to do "COPY .env /app/.env" - the 1st dot caused the command to fail with "file not found"
COPY env /app/.env

# Specify the command to run the Java application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
