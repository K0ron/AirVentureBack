# Utiliser l'image de base avec OpenJDK 17
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier tout le code source du projet dans le conteneur
COPY . .

# Construire le .jar à partir du code source
RUN ./mvnw clean install -DskipTests

# Spécifier le fichier .jar à exécuter dans l'image (mettre à jour avec ton nom de fichier .jar)
CMD ["java", "-jar", "/app/target/AirVentureBack-0.0.1-SNAPSHOT.jar"]




