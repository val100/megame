# syntax=docker/dockerfile:1.7

# ----------------------
# Build stage
# ----------------------
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

# Use Maven cache to speed up builds
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -ntp clean package

# ----------------------
# Runtime stage
# ----------------------
FROM eclipse-temurin:17-jre-alpine
ENV JAVA_OPTS=""
ENV SPRING_PROFILES_ACTIVE=prod
WORKDIR /app

# Copy fat jar built by Spring Boot plugin
COPY --from=build /app/target/room-reservation-0.1.0-SNAPSHOT.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
