FROM openjdk:11-jdk-slim
ADD target/testapp.jar /usr/share/test.jar
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/test.jar"]
