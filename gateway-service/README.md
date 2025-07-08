# Gateway Service

Ce projet représente le **microservice de passerelle (Gateway)** de l'application de gestion de patients. Il agit comme un **point d'entrée unique** pour les autres microservices.

---

## Technologies utilisées

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Cloud Gateway**
- **Spring Security (authentification HTTP Basic)**
- **Maven**

---

## Objectifs

- Fournir une **passerelle centralisée** pour accéder aux différents microservices.
- **Rediriger** les requêtes entrantes vers les bons services (`patient-service`, `note-service`, etc.).
- Appliquer une **authentification basique** pour sécuriser les accès.

---

## Configuration

Le fichier `application.yml` contient la configuration des routes :

```yaml
server:
  port: 8080

spring:
  cloud:
    gateway:
      routes:
        - id: patient-service
          uri: http://localhost:8081
          predicates:
            - Path=/patients/**
        - id: note-service
          uri: http://localhost:8082
          predicates:
            - Path=/notes/**, /notes/patient/**
        - id: assessment-service
          uri: http://localhost:8083
          predicates:
            - Path=/assessments, /assessments/**

# Sécurité basique pour test
spring.security.user.name: admin
spring.security.user.password: admin123

```

La sécurité est assurée par **Spring Security** avec des utilisateurs en mémoire (exemple dans `SecurityConfig.java`).

---

## Exemple d'accès sécurisé

Toutes les routes sont protégées par une **authentification Basic**.

**Exemple avec requests.http :**

```bash
POST http://localhost:8080/patients
Authorization: Basic YWRtaW46YWRtaW4xMjM=
Content-Type: application/json
```

---

## Lancer le projet

1. Assure-toi que tous les microservices en backend sont démarrés (`patient-service`, `note-service`, etc.).
2. Démarre `gateway-service` :

```bash
mvn spring-boot:run
```

3. Accède aux endpoints via `http://localhost:8080/...`

---

## Structure du projet

```
gateway-service/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.gateway_service/
│   │   │       ├── GatewayServiceApplication.java
│   │   │       └── config/
│   │   │           └── SecurityConfig.java
│   │   └── resources/
│   │       └── application.yml
├── pom.xml
└── README.md
```

---

## 📦 Dépendances principales

```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway-server-webmvc</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
  </dependency>
</dependencies>
```

---

## Tests

Des tests unitaires peuvent être ajoutés pour tester la configuration de sécurité et le routage.

---

## Auteur

Projet développé par **ZAGOUE** dans le cadre de la formation CDA 2024 – OpenClassrooms.
