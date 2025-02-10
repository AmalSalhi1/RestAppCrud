# CRUD REST API - Documentation

## Description

Ce projet est une API REST simple permettant de gérer des produits. Il propose des opérations CRUD (Create, Read, Update, Delete) via des endpoints exposés.

## Architecture du Projet

Le projet suit l'architecture MVC (Modèle-Vue-Contrôleur) :

- **Controller** : Gère les requêtes HTTP et appelle le service approprié.
- **Service** : Contient la logique métier et interagit avec le repository.
- **Repository** : Interface pour l'accès à la base de données.
- **DTO (Data Transfer Object)** : Utilisé pour transférer des données entre la couche API et la couche Service.

### Technologies utilisées

- **Spring Boot** (Framework Backend)
- **Spring Data JPA** (Gestion de la base de données)
- **H2 / MySQL** (Base de données en mémoire ou persistante)
- **Spring Cache** (Optimisation des performances)
- **Spring Boot Test, JUnit, Mockito** (Tests unitaires et d'intégration)

## Installation et Exécution

### 1. Cloner le projet

```bash
git clone <URL_DU_REPO_GITHUB>
cd CrudRestApi
```

### 2. Configuration de la base de données

- Modifier `application.properties` pour utiliser MySQL ou H2.

```properties
# H2 Database
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

- Pour MySQL, remplace par :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nom_de_la_base
spring.datasource.username=root
spring.datasource.password=mot_de_passe
spring.jpa.hibernate.ddl-auto=update
```

### 3. Démarrer l'API

```bash
mvn spring-boot:run
```

L'API sera disponible sur `http://localhost:8080/api/products`

## Endpoints de l'API

| Méthode    | URL                  | Description                 |
| ---------- | -------------------- | --------------------------- |
| **GET**    | `/api/products`      | Récupérer tous les produits |
| **GET**    | `/api/products/{id}` | Récupérer un produit par ID |
| **POST**   | `/api/products`      | Créer un nouveau produit    |
| **PUT**    | `/api/products/{id}` | Mettre à jour un produit    |
| **DELETE** | `/api/products/{id}` | Supprimer un produit        |

## Exécution des Tests

Pour exécuter les tests unitaires et d'intégration :

```bash
mvn test
```

## Déploiement sur GitHub

### 1. Initialiser Git et ajouter les fichiers

```bash
git init
git add .
git commit -m "Initial commit"
```

### 2. Lier le projet à un dépôt GitHub

```bash
git remote add origin <URL_DU_REPO_GITHUB>
git branch -M main
git push -u origin main
```

## Auteur

Amal Salhi - Développeuse Fullstack
