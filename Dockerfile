FROM openjdk:11-jdk-slim
ADD target/testapp.jar test.jar
ENTRYPOINT ["java", "-jar", "/test.jar"]