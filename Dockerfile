FROM openjdk:21-jdk-slim-buster
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 80
CMD ["java", "-jar", "app.jar"]