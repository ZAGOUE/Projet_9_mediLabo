## ✨ Green Code - Recommandations pour un code écoresponsable

### 📅 Contexte de l'étape 6

**Objectif** : Identifier les enjeux du Green Code et proposer des recommandations pour rendre le projet plus sobre en ressources.

**Prérequis** : Le code est finalisé et conteneurisé.

**Livrable** : Ce fichier documente les pistes d'amélioration écoresponsables identifiées.

---

## 🌿 Qu'est-ce que le Green Code ?

Le Green Code regroupe les pratiques de développement visant à réduire l'impact environnemental du logiciel. Il s'agit d'écrire un code qui :

* ⚖️ Consomme moins de ressources (CPU, RAM, bande passante, stockage)
* ⏳ S'exécute plus rapidement et efficacement
* 📝 Est plus simple, plus clair, et plus facile à maintenir

Le Green Code rejoint les principes de performance, de sécurité et d'accessibilité.

---

## 💡 Enjeux du Green Code

* 🌍 **Environnemental** : Moins d'énergie = moins d'émissions de CO2
* 🚀 **Performance** : Temps de réponse amélioré, meilleure expérience utilisateur
* 📈 **Scalabilité** : Meilleure montée en charge, réduction des coûts d'hébergement
* 🤝 **Responsabilité sociétale** : Numérique plus durable et plus sobre

---

## 🔍 Identifier les zones peu performantes

### ✅ Backend Spring Boot

* Boucles imbriquées ou appels répétés à la base de données non nécessaires
* Connexions à la BDD non fermées proprement
* Logs excessifs ou au niveau DEBUG en production
* Initialisation trop élargie des beans Spring
* Absence de cache pour des données répétitives

### ✅ Conteneurs Docker

* Images de base trop lourdes (ex : openjdk:17 au lieu de temurin:17-slim)
* Fichiers de configuration non optimisés (ressources allouées inutilement)
* Logs persistants mal configurés (taille illimitée)

---

## 📊 Recommandations d'amélioration Green

### 🛠️ Backend (Spring Boot uniquement)

* Activer le **lazy initialization** : `spring.main.lazy-initialization=true`
* Utiliser `@Cacheable` sur les services qui retournent des données inchangées
* Nettoyer les logs et limiter le niveau à `INFO` en production
* Supprimer les endpoints non utiles en prod (`/h2-console`, `/swagger-ui`, etc.)
* Préférer les algorithmes simples et efficaces (O(n) max)

### 🚧 Docker et déploiement

* Utiliser des **images slim** (ex: `eclipse-temurin:17-jdk-slim`)
* Nettoyer les fichiers inutiles dans l'image avec `.dockerignore`
* Utiliser des **multi-stage builds** pour ne garder que les fichiers nécessaires
* Définir des limites de ressources dans `docker-compose.yml` (ex: `mem_limit`)
* Utiliser des volumes uniquement si besoin de persistance

---

## 📄 Ressources consultées

* [https://www.greenit.fr](https://www.greenit.fr)
* [https://ecoresponsable.numerique.gouv.fr/publications/referentiel-ecoconception/](https://ecoresponsable.numerique.gouv.fr/publications/referentiel-ecoconception/)
* [https://www.boavizta.org](https://www.boavizta.org)
* [https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html)

---

## 📚 Conclusion

Ce projet peut améliorer son empreinte écologique en intégrant des pratiques simples mais efficaces d'éco-conception. Ces améliorations contribuent aussi à la performance, à la sécurité et à la maintenabilité du code. Une démarche écoresponsable est donc aussi un gage de qualité logicielle durable.
