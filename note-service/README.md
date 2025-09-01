# 📝 Microservice `note-service` – Projet P9

Ce microservice fait partie du projet de détection du diabète de type 2, développé pour la clinique Abernathy.

Il gère les **notes médicales** des patients, rédigées par les praticiens, et les stocke dans une base **MongoDB**.  
Ce service fonctionne indépendamment des autres microservices.

---

## ✅ Fonctionnalités

- Ajout de notes médicales associées à un patient
- Consultation des notes par ID patient
- Stockage dans une base NoSQL (MongoDB)
- Sécurité HTTP Basic avec Spring Security

---

## 📦 Stack technique

- Java 17
- Spring Boot 3.5
- Spring Web
- Spring Data MongoDB
- Spring Security
- Lombok
- MongoDB local

---

## 🔐 Sécurité

Tous les endpoints sont protégés par une authentification **HTTP Basic** dans `filterChain`.

> Identifiants par défaut :
> - Username : `admin`
> - Password : `admin123`
---
Les identifiants sont paramétrés dans `application.properties`


## 🌐 Endpoints REST

| Méthode | URI                           | Description                            |
|---------|-------------------------------|----------------------------------------|
| `GET`   | `/notes/patient/{patientId}`  | Liste toutes les notes du patient      |
| `POST`  | `/notes/patient/{patientId}`  | Ajoute une nouvelle note               |

---


## ⚙️ Configuration application.properties

```properties
spring.application.name=note-service
server.port=8082

spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=note_db

spring.security.user.name=admin
spring.security.user.password=admin123
```

---

## ▶️ Lancer le microservice

1. Assurez-vous que **MongoDB** est bien démarré (`localhost:27017`)
2. Lancez le projet depuis IntelliJ ou en ligne de commande :

```bash
mvn spring-boot:run
```

---

## 📁 Structure du projet

```
com.note_service
├── controller        → REST API
├── service           → Logique métier
├── repository        → Accès MongoDB
├── model             → Entité Note
├── security          → Spring Security config
```

---

## 💡 À venir dans les sprints suivants


- Dockerisation et orchestration des services