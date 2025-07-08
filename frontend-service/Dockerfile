# Étape 1 : Builder l'application avec Maven
FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /app

# Copie des fichiers Maven
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw

# Téléchargement des dépendances
RUN ./mvnw dependency:go-offline

# Copie des sources
COPY src src

# Build de l'application
RUN ./mvnw clean package -DskipTests

# Étape 2 : Image légère pour exécution
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copie le jar généré depuis l'étape précédente
COPY --from=build /app/target/*.jar app.jar

# Exposition du port standard Spring Boot
EXPOSE 8084

# Commande de démarrage
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
