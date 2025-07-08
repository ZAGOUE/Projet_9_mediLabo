# 🩺 Microservice `patient-service` – Projet P9

Ce microservice fait partie du projet de détection du diabète de type 2, développé pour la clinique Abernathy.

Il gère les **informations personnelles des patients** dans le cadre d’un système distribué basé sur des microservices Spring Boot.

---

## ✅ Fonctionnalités

- Récupération de la liste des patients
- Récupération d’un patient par son ID
- Création d’un nouveau patient
- Mise à jour des données d’un patient existant
- Authentification HTTP Basic via Spring Security
- Chargement automatique de données de test

---

## 📦 Stack technique

- Java 17
- Spring Boot 3.5
- Spring Web
- Spring Data JPA
- Spring Security
- MySQL
- Maven
- Lombok

---

## 🔐 Sécurité

L’accès aux endpoints REST est protégé par **Spring Security** avec une authentification **HTTP Basic**.

> Identifiants par défaut (in-memory) :
> - **Username** : `admin`
> - **Password** : `admin123`

---

## 🚀 Endpoints REST

| Méthode | URI                  | Description                         |
|---------|----------------------|-------------------------------------|
| `GET`   | `/patients`          | Récupère tous les patients          |
| `GET`   | `/patients/{id}`     | Récupère un patient par ID          |
| `POST`  | `/patients`          | Ajoute un nouveau patient           |
| `PUT`   | `/patients/{id}`     | Met à jour les infos d’un patient   |

📌 Tous les endpoints nécessitent une authentification.

---

## 🧪 Données de test

Les **4 cas de test** suivants sont chargés automatiquement via `data.sql` :

| Nom            | Date de naissance | Sexe | Adresse         | Téléphone     |
|----------------|-------------------|------|------------------|----------------|
| TestNone       | 1966-12-31        | F    | 1 Brookside St   | 100-222-3333   |
| TestBorderline | 1945-06-24        | M    | 2 High St        | 200-333-4444   |
| TestInDanger   | 2004-06-18        | M    | 3 Club Road      | 300-444-5555   |
| TestEarlyOnset | 2002-06-28        | F    | 4 Valley Dr      | 400-555-6666   |

---

## ⚙️ Configuration `application.properties`

```properties
spring.application.name=patient-service
server.port=8081

spring.datasource.url=jdbc:mysql://localhost:3306/diabetes_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## ▶️ Lancer l’application

1. Assurez-vous que MySQL est en cours d'exécution avec la base `diabetes_db`.
2. Lancez l'application depuis IntelliJ ou en ligne de commande :
```bash
mvn spring-boot:run
```

---

## 📁 Structure du projet

```
com.patient_service
├── controller        → REST endpoints
├── service           → Logique métier
├── repository        → Accès base de données
├── model             → Entité `Patient`
├── security          → Configuration Spring Security
```

---
## Récapitulatif Sprint 1

| Élément                                | Statut | Détails                             |
| -------------------------------------- | ------ | ----------------------------------- |
| Projet Spring Boot (`patient-service`) | ✅      | Créé avec les bonnes dépendances    |
| Base de données relationnelle (3NF)    | ✅      | MySQL + entité `Patient` conforme   |
| Endpoints REST fonctionnels            | ✅      | `GET`, `POST`, `PUT`, `GET by id`   |
| Données de test chargées               | ✅      | via `data.sql`                      |
| Sécurité activée                       | ✅      | Auth HTTP Basic (`admin:admin123`)  |
| Fichier `README.md` structuré          | ✅      | Généré et prêt pour le dépôt GitHub |
| Test avec `requests.http`              | ✅      | Effectué avec succès                |



## 💡 À venir dans les sprints suivants


- Dockerisation complète

---

## 🧪 Suggestions Green Code (Sprint final)

- Limiter les requêtes SQL redondantes
- Utiliser des DTOs légers côté réseau
- Éviter les allocations mémoire inutiles
- Préférer les `Optional` ou `null-safe` pour limiter les erreurs de flux