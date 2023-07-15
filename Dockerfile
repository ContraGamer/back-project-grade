FROM openjdk:11-jdk-slim
ADD target/testapp.jar /usr/share/test.jar
ENTRYPOINT ["java", "-jar", "/test.jar"]