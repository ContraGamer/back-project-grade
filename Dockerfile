FROM openjdk:11-jdk-slim
COPY target/testapp.jar app.jar
ENTRYPOINT ["java", "-jar", "/test.jar"]
