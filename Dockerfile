FROM openjdk:11-jdk-slim

WORKDIR /demo
COPY ./target/*.jar /demo/app.jar
ENV _JAVA_OPTIONS="-Djava.net.preferIPv4Stack=true"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/demo/app.jar"]