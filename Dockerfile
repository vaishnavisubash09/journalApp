# 1. Use a lightweight JDK image (best practice)
FROM eclipse-temurin:17-jdk-alpine

# 2. Set working directory inside the container
WORKDIR /app

# 3. Copy the packaged JAR file (will be created during Jenkins build)
COPY target/journalApp-0.0.1-SNAPSHOT.jar app.jar

# 4. Expose the port (Spring Boot default 8080)
EXPOSE 8080

# 5. Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
