# === BUILD STAGE ===
# Use the official Maven image with JDK 25 for the build stage.
FROM maven:3.9-eclipse-temurin-25 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy only the pom.xml file first to leverage Docker layer caching for dependencies
COPY pom.xml .

# Download project dependencies (this layer is only rebuilt if pom.xml changes)
RUN mvn dependency:go-offline -B

# Copy the rest of the application source code
COPY src /app/src

# Package the application into a JAR file
RUN mvn clean package -DskipTests

# === RUNTIME STAGE ===
# Use a minimal JRE 25 image for the final, slimmed-down runtime environment
FROM eclipse-temurin:25-jre-jammy AS runtime

# Set the working directory
WORKDIR /app

# Copy the JAR file from the 'build' stage to the 'runtime' stage
COPY --from=build /app/target/*-jar-with-dependencies.jar app.jar

# Expose the port your application runs on (default is often 8080 for web apps)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
