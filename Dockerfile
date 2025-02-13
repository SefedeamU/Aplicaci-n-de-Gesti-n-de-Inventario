FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/mi-aplicacion-1.0.0.jar app.jar
COPY sql /app/sql

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]