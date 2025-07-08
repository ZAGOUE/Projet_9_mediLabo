
# Frontend Service – Projet 9 Medilabo Solutions

Ce projet est le **frontend** Spring Boot de l’application Medilabo Solutions.  
Il fournit une interface web minimaliste (pages HTML ou Thymeleaf) permettant de visualiser les données de patients, d’ajouter des notes médicales, et de consulter l’évaluation du risque de diabète.  
Le frontend interagit avec les microservices via le **gateway** et propose une authentification HTTP Basic (en mémoire) pour la démo.

---

## Fonctionnalités

- Liste des patients
- Détail d’un patient (infos, notes, évaluation du risque)
- Ajout/Suppression de notes médicales
- Interface utilisateur sobre pour la démo  
- Sécurité : authentification HTTP Basic (utilisateurs en mémoire)


## Technologies utilisées

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Security (authentification HTTP Basic)**
- **Maven**

- Accès au réseau interne (pour contacter les autres microservices et le gateway)
- Le backend doit être déjà lancé (gateway, patient-service, note-service, assessment-service, etc.)

### Lancement

Le service démarre par défaut sur [http://localhost:8084](http://localhost:8084)
(Modifie le port dans `application.properties` si besoin.)

### Accès à l’interface

* Ouvre ton navigateur sur [http://localhost:8084](http://localhost:8084)
* Saisis les identifiants d’un utilisateur (voir section "Utilisateurs de démo").

---

## 3. Utilisateurs de démo

Les identifiants sont définis dans la classe `SecurityConfig`.
Exemple :

| Nom d'utilisateur | Mot de passe | Rôle  |
| ----------------- | ------------ | ----- |
| user              | password     | USER  |
| admin             | adminpass    | ADMIN |

---

## 4. Structure du projet

```
frontend-service/
├── src/main/java/com/frontend_service/
│   ├── controller/         # Contrôleurs Web (Thymeleaf ou REST)
│   ├── model/              # Modèles de données Patient, Note, etc.
│   ├── proxy/              # Feign clients vers les microservices
│   ├── security/           # Config Spring Security
│   └── FrontendServiceApplication.java
├── src/main/resources/
│   ├── templates/          # Fichiers Thymeleaf (.html)
│   └── application.properties
├── pom.xml
└── README.md
```

---

## 5. Configuration

Les URLs des microservices sont déclarées dans `application.properties` ou via le **gateway**.

Exemple de configuration :

```properties
server.port=8084
gateway.url=http://localhost:8080
```

---

## 6. Appels aux microservices

Les communications entre le frontend et les autres services s’effectuent via des **proxies Feign** ou via RestTemplate.

* Patient Service : CRUD des patients
* Note Service : Ajout, édition et suppression de notes médicales
* Assessment Service : Évaluation du risque de diabète

---

## 7. Sécurité

L’application utilise **Spring Security** avec des utilisateurs en mémoire et le schéma HTTP Basic.
Les endpoints principaux sont protégés et nécessitent une authentification.

---

## 8. Démo et bonnes pratiques

* Interface sobre, sans design complexe : une page pour la liste des patients, une page pour les détails d’un patient et ses notes.
* Démo rapide : adapté pour une soutenance ou une évaluation.

---

## 9. Contribution

Pour contribuer :

1. Forker le dépôt.
2. Créer une branche de fonctionnalité.
3. Soumettre une pull request.

---

## 10. Auteurs

Projet développé dans le cadre du **Projet 9 – Medilabo Solutions**


---

```


