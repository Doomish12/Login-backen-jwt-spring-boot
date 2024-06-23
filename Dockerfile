
FROM amazoncorretto:17-alpine-jdk

COPY target/login-backend-jwt-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]