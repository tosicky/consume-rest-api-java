FROM openjdk:11-jdk-slim

WORKDIR /demo
COPY ./target/*.jar /demo/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/demo/app.jar"]