# Usar una imagen de Java como base
FROM openjdk:11

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR de la aplicación a la imagen
COPY test-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto en el que se ejecuta la aplicación (ajústalo según tus necesidades)
EXPOSE 8080

# Comando para ejecutar la aplicación cuando el contenedor se inicie
CMD ["java", "-jar", "app.jar"]