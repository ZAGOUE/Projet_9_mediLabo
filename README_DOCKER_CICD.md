📘 Projet 9 – Medilabo Solutions
📌 Présentation

Medilabo Solutions est une application distribuée composée de plusieurs microservices destinés à la gestion des patients, des notes médicales et de l’évaluation des risques.
L’architecture repose sur Spring Boot, une gateway sécurisée, et une intégration continue complète avec CI/CD, Docker et une approche orientée Green Code.

🏗️ Architecture

L’application est découpée en plusieurs microservices :

frontend-service : Interface utilisateur (Spring MVC/Thymeleaf ou React).

gateway-service : Point d’entrée unique, sécurisation des flux et routage.

patient-service : Gestion des données patients (MySQL).

note-service : Gestion des notes médicales (MongoDB).

assessment-service : Service d’évaluation du risque de diabète (appel aux autres microservices via Feign).

L’ensemble est orchestré par Docker Compose et interconnecté via un réseau interne.

[Frontend] ⇄ [Gateway] ⇄ [Patient Service]
⇄ [Note Service]
⇄ [Assessment Service]
---

### ⚙️ Exemple de Dockerfile pour un microservice 

```dockerfile
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
```
🚀 Déploiement avec Docker

L’application utilise Docker et Docker Compose pour simplifier le déploiement :

# Construction et lancement des services
docker compose up --build

# Arrêt des services
docker compose down


Les services déployés incluent :

mysql-db (base relationnelle patients)

mongo-db (base documentaire notes)

patient-service, note-service, assessment-service, gateway-service, frontend-service

Healthchecks

Chaque conteneur inclut un healthcheck (MySQL, MongoDB, services Spring Boot) afin d’assurer la résilience du système.


🗂️ Fichier docker-compose.yml à la racine du projet

J’ai créé un unique fichier docker-compose.yml à la racine du projet.
Ce fichier :

✅ Référence toutes les images de microservices

✅ Définit leurs ports et variables d’environnement

✅ Configure un réseau interne pour qu’ils puissent communiquer entre eux

✅ Peut inclure des services optionnels (ex. base de données) avec leurs propres volumes et healthchecks

(voir docker-compose)

🧩 Fonctionnement de la dockerisation

🚀 À la compilation
Maven génère le JAR de chaque microservice :


`mvn clean package`

📦 Avec Docker
Le Dockerfile prend ce JAR, le "met dans une boîte" (le conteneur), et définit comment le lancer.

🤝 Avec Docker Compose
Orchestration du lancement de tous les conteneurs :


🟢 À l’exécution
Chaque microservice tourne dans son propre environnement isolé.

Ils partagent un réseau Docker interne et s'appellent par nom.

L’utilisateur accède via les ports mappés (ex: localhost:8080).

🔐 Sécurité

Spring Security avec authentification basique (Basic Auth) et configuration par microservice.

Gateway protégée (admin/admin123).

Feign interceptors pour la propagation de l’authentification entre microservices.

CORS configuré globalement pour autoriser les appels du frontend (http://localhost:8084).

⚙️ Intégration Continue (CI/CD)

L’intégration continue est assurée via GitHub Actions :

✅ Build Maven avec compilation et packaging.

✅ Tests unitaires et d’intégration exécutés automatiquement.

✅ Rapports de couverture générés avec JaCoCo et envoyés vers Codecov.

✅ Rapports Surefire pour le suivi des tests.

✅ Analyse qualité avec Qodana / SonarCloud (optionnel).

🌱 Approche Green Code

•	Réduction de l’empreinte mémoire et CPU

•	Code sobre et lisible

•	Éviter les appels réseau redondants

•	Ne pas surcharger la RAM inutilement

•	Réflexion sur l’optimisation des traitements (ex. : éviter des appels REST en boucle)
