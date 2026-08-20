# Dockerfile for NeSy-IOWF++_T
# Based on ARCHITECTURE.md Module 5 specifications

# Build stage
FROM maven:3.9-eclipse-temurin-17-alpine AS build
# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy the packaged jar from build stage
COPY --from=build /app/target/NeSy-IOWF_T-1.0.0-SNAPSHOT.jar ./app.jar

# Expose ports (assuming HTTP service on 8080, adjust as needed)
EXPOSE 8080

# Set environment variables
ENV JAVA_OPTS=""

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]