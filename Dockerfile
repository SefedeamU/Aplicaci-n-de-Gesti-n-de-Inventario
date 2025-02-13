FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/ECommerceOrderSystem-0.0.1-SNAPSHOT.jar app.jar
COPY sql /app/sql

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]