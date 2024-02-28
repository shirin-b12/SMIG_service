# README

## Contrôleur Utilisateurs

Le contrôleur `UtilisateursController` gère les interactions avec les utilisateurs de notre application. Voici comment
fonctionnent les requêtes GET et POST pour ce contrôleur.

### Requêtes GET

Une requête GET est utilisée pour récupérer des données à partir de notre serveur.

- Pour récupérer tous les utilisateurs, vous pouvez envoyer une requête GET à l'URL suivante :

```
GET /utilisateur
```

Cela renverra une liste de tous les utilisateurs dans notre base de données.

- Pour récupérer un utilisateur spécifique par son ID, vous pouvez envoyer une requête GET à l'URL suivante :

```
GET /utilisateur/{id}
```

Cela renverra l'utilisateur correspondant à l'ID fourni. Cette route nécessite une authentification et l'utilisateur
doit avoir le rôle "Utilisateur".

### Requêtes POST

Une requête POST est utilisée pour envoyer des données à notre serveur.

- Pour créer un nouvel utilisateur, vous pouvez envoyer une requête POST à l'URL suivante :

```
POST /utilisateur
```

Dans le corps de la requête, vous devez inclure les détails de l'utilisateur que vous voulez créer. Par exemple :

```json
{
  "nom": "Dupont",
  "prenom": "Jean",
  "email": "jean.dupont@example.com",
  "mot_de_passe": "motdepasse"
}
```

Cela créera un nouvel utilisateur dans notre base de données avec les détails que vous avez fournis. Cette route
nécessite une authentification et l'utilisateur doit avoir le rôle "SuperAdmin".

- Pour se connecter en tant qu'utilisateur, vous pouvez envoyer une requête POST à l'URL suivante :

```
POST /utilisateur/login
```

Dans le corps de la requête, vous devez inclure l'email et le mot de passe de l'utilisateur. Par exemple :

```json
{
  "email": "jean.dupont@example.com",
  "mot_de_passe": "motdepasse"
}
```

Cela renverra un token que vous pouvez utiliser pour vous authentifier sur les routes protégées.

### Statut des réponses

Chaque requête renvoie un code de statut qui indique si la requête a réussi ou non. Voici quelques codes de statut
courants :

- 200 OK : La requête a réussi.
- 201 Created : La requête POST a réussi et un nouvel utilisateur a été créé.
- 400 Bad Request : La requête n'a pas été comprise par le serveur, souvent à cause d'un corps de requête mal formé.
- 401 Unauthorized : La requête nécessite une authentification. Cela se produit généralement lorsqu'une requête POST est
  envoyée à `/utilisateur/login` avec un email ou un mot de passe incorrect.
- 404 Not Found : L'utilisateur demandé n'a pas été trouvé sur le serveur.

### Authentification

Certaines routes nécessitent une authentification. Pour ces routes, vous devez inclure un en-tête `Authorization` avec
un token JWT valide. Vous pouvez obtenir un token en envoyant une requête POST à l'URL `/utilisateur/login` comme décrit
ci-dessus.