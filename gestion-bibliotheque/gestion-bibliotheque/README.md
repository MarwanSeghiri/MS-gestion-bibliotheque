# API de bibliothèque (TP Spring Boot MVC)

Application de gestion d'une bibliothèque (auteurs et livres) conforme au PDF.

## Lancer l'application
- Prérequis : Java 17, Maven (wrapper inclus), MySQL (base `stock`)
- Commande : `.\mvnw.cmd spring-boot:run` (Windows) ou `./mvnw spring-boot:run` (macOS/Linux)
- Base URL : `http://localhost:8080`

## Clé API
- Les requêtes `POST/PUT/PATCH` exigent l'en-tête `X-API-KEY` (lecture `GET` publique)
- Valeur par défaut : `dev-key` (configurable via `src/main/resources/application.properties`)

## Points d'extrémité
### Auteurs
- `GET /authors` — liste des auteurs
- `GET /authors/{id}` — auteur par id
- `POST /authors` — créer un auteur (`X-API-KEY` requis)
- `PUT /authors/{id}` — modifier un auteur (`X-API-KEY` requis)
- `DELETE /authors/{id}` — supprimer un auteur

### Livres
- `GET /books` — liste paginée avec filtres : `title`, `authorId`, `category`, `yearFrom`, `yearTo`, `page`, `size`, `sort` (ex : `sort=year,desc`)
- `GET /books/{id}` — détail d'un livre
- `POST /books` — créer un livre (validation obligatoire, `X-API-KEY` requis)
- `PUT /books/{id}` — modifier un livre (`X-API-KEY` requis)
- `DELETE /books/{id}` — supprimer un livre

### Statistiques
- `GET /stats/books-per-category` — nombre de livres par catégorie
- `GET /stats/top-authors?limit=3` — auteurs ayant le plus de livres

## Règles et validations
- `isbn` unique (ISBN-10/13 accepté)
- `title` non vide
- `year` entre 1450 et l'année courante
- 404 si l'auteur n'existe pas
- Erreurs retournées en JSON structuré

## Exemples de requêtes
Créer un auteur
```
curl -X POST http://localhost:8080/authors \
  -H "Content-Type: application/json" \
  -H "X-API-KEY: dev-key" \
  -d '{"firstName":"Victor","lastName":"Hugo","birthYear":1802}'
```

Lister les livres triés par année décroissante
```
curl "http://localhost:8080/books?sort=year,desc&size=5"
```

Créer un livre
```
curl -X POST http://localhost:8080/books \
  -H "Content-Type: application/json" \
  -H "X-API-KEY: dev-key" \
  -d '{
    "title":"Les Misérables",
    "isbn":"9782070409188",
    "year":1862,
    "category":"NOVEL",
    "author": { "id": 1 }
  }'
```

## Postman
- Importez `postman/gestion-bibliotheque.postman_collection.json`
- La variable d'environnement `apiKey` est utilisée pour `X-API-KEY`
- `baseUrl` : `http://localhost:8080`

## Structure du projet
- `controller/` — classes REST (API)
- `service/` — logique métier
- `repository/` — accès BD (Spring Data)
- `domain/` — entités JPA
- `exception/` — gestion des erreurs
- `config/` — sécurité (clé API) et configuration

## Lancer l’application
- Prérequis : MySQL en local avec une base `stock` (créée par `src/main/resources/db/create_database.sql` si besoin).
- Configuration : `src/main/resources/application.properties` définit la connexion MySQL et `api.key=dev-key`.
- Démarrage : `.\mvnw.cmd spring-boot:run` (Windows) ou `./mvnw spring-boot:run` (macOS/Linux).
- URL de base : `http://localhost:8080`.

## Sécurité
- Lecture (GET) : publique.
- Écriture (POST/PUT/PATCH) : exiger le header `X-API-KEY: dev-key`.

## Endpoints
### Auteurs
- `GET /authors` : liste des auteurs
- `GET /authors/{id}` : détail d’un auteur (404 si absent)
- `POST /authors` : créer un auteur (validation)
- `PUT /authors/{id}` : modifier un auteur (404 si absent)
- `DELETE /authors/{id}` : supprimer un auteur (404 si absent)

### Livres
- `GET /books` : liste paginée/filtrée/triée
- `GET /books/{id}` : détail d’un livre (404 si absent)
- `POST /books` : créer un livre (validation et ISBN unique)
- `PUT /books/{id}` : modifier un livre (404 si absent)
- `DELETE /books/{id}` : supprimer un livre (404 si absent)

### Statistiques
- `GET /stats/books-per-category` : nombre de livres par catégorie
- `GET /stats/top-authors?limit=3` : auteurs ayant le plus de livres (paramètre `limit` optionnel)

## Validation
- Livre : `title` non vide, `isbn` (10 ou 13 chiffres), `year` ∈ [1450, année courante].
- Auteur : `firstName/lastName` non vides, `birthYear` ≥ 1450 et ≤ année courante.
- Erreurs retournées en JSON structuré (400) et 404 pour ressources absentes.

  
### Créer un livre
```
POST /books
Headers: Content-Type: application/json, X-API-KEY: dev-key
Body:
{ "title":"Germinal","isbn":"9781234567890","year":1885,"category":"NOVEL","author":{"id":3} }
```

## Tests avec Postman
- Importer la collection : `postman/gestion-bibliotheque.postman_collection.json`.
- Variables d’environnement : `baseUrl=http://localhost:8080`, `apiKey=dev-key`.
- Pour POST/PUT/PATCH ajouter `X-API-KEY: {{apiKey}}`.

## Structure du projet (MVC)
- `controller/` API REST
- `service/` logique métier
- `repository/` accès BD (Spring Data JPA)
66→- `domain/` entités JPA
67→- `exception/` gestion des erreurs
68→- `config/` configuration (filtre API key)
