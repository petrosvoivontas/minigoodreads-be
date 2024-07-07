# Use an official OpenJDK 18 image as the base image for building
FROM eclipse-temurin:18-jdk-focal AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper files (if you're using Maven)
COPY mvnw ./
COPY .mvn .mvn

# Copy the project files and pom.xml
COPY pom.xml .

# Download the dependencies
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY src src

# Build the application
RUN ./mvnw package -DskipTests

# Use a smaller base image for runtime
FROM eclipse-temurin:18-jre-focal

LABEL org.opencontainers.image.source https://github.com/petrosvoivontas/minigoodreads-be

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/minigoodreads.jar /app/minigoodreads.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "--enable-preview", "-Dspring.profiles.active=prod", "-jar", "minigoodreads.jar"]
