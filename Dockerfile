# Stage 1: Build stage
FROM eclipse-temurin:21-jdk AS build
COPY . /app
WORKDIR /app
RUN ./mvnw --no-transfer-progress clean package -DskipTests
RUN mv -f target/*.jar app.jar

# Stage 2: Runtime stage
FROM eclipse-temurin:21-jre
ARG PORT=8080
ENV PORT=${PORT}
COPY --from=build /app/app.jar .
RUN useradd -ms /bin/bash runtime
USER runtime
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]
