# Assessment Service

## Description

Le microservice **assessment-service** fait partie d’une architecture microservices médicales.  
Il a pour objectif d’évaluer le risque de diabète d’un patient en utilisant les données fournies par deux autres microservices :

- `patient-service` : fournit les informations du patient.
- `note-service` : fournit les notes médicales associées au patient.

Ce service interroge ces deux microservices via des clients Feign, puis réalise l’évaluation du risque.

---

## Fonctionnalités principales

- Récupération des données patient via **Feign Client** depuis `patient-service`.
- Récupération des notes médicales via **Feign Client** depuis `note-service`.
- Calcul du risque de diabète à partir des données collectées.
- Exposition d’une API REST pour accéder à l’évaluation.
- Configuration spécifique de Feign pour la gestion des appels inter-services.

---


## Technologies utilisées

- Java 17+
- Spring Boot (Web, OpenFeign)
- Feign Client pour communication inter-service
- Maven pour gestion de projet et dépendances
- JUnit / Mockito (à prévoir pour tests unitaires)

---

## Points d'explications

### Les modèles 

Chaque microservice doit être autonome. Même si les modèles `Patient` et `Note` existent ailleurs, 
ils sont redéfinis localement pour découpler les services.

### Utilisation du proxy Feign

Les proxies sont des interfaces Java servant de "clients REST" pour communiquer avec les autres microservices.
Ils simplifient énormément l’appel à d'autres microservices.
Ils permettent de récupérer un patient via patient-service et une note via note-service.

Pour éviter d'écrire des appels HTTP manuels, nous utilisons `NoteProxy` et `PatientProxy`


### Rôle de FeignConfig

FeignConfig permet de personnaliser le comportement des requêtes Feign.
Nous y avons paramétré l'authentification basique.

---
| Composant         | Rôle principal                                 |
| ----------------- | ---------------------------------------------- |
| **Model**         | Structurer les données utilisées               |
| **Proxy (Feign)** | Simplifier les appels inter-services           |
| **FeignConfig**   | Personnaliser le comportement des appels Feign |
| **Service**       | Implémenter la logique métier                  |
| **Controller**    | Exposer l’API REST du microservice             |


## Lancement du projet

1. Cloner le dépôt :

   ```bash
   git clone <URL_DU_REPO>
   
