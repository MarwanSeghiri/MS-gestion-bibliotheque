# TP Spring Boot – Gestion d’une Bibliothèque

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
  - Query params : `page` (0), `size` (10), `title`, `authorId`, `category` (`NOVEL|ESSAY|POETRY|OTHER`), `yearFrom`, `yearTo`, `sort` (ex :`year,desc`)
  - Exemple : `/books?page=0&size=10&title=mis&authorId=1&category=NOVEL&yearFrom=1800&yearTo=1900&sort=year,desc`
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

## Exemples de requêtes
### Créer un auteur
```
POST /authors
Headers: Content-Type: application/json, X-API-KEY: dev-key
Body:
{ "firstName": "Jean", "lastName": "Valjean", "birthYear": 1800 }
```

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
