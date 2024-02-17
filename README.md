# code-with-quarkus

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- JDBC Driver - MySQL ([guide](https://quarkus.io/guides/datasource)): Connect to the MySQL database via JDBC

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

### RESTEasy Reactive Qute

Create your web page using Quarkus RESTEasy Reactive & Qute

[Related guide section...](https://quarkus.io/guides/qute#type-safe-templates)

### Le Token
voici une explication de ce que j'ai fait :  

- J'ai ajouté les dépendances nécessaires dans le fichier pom.xml. 
- Ces dépendances sont nécessaires pour utiliser JWT (JSON Web Tokens) et 
les fonctionnalités de sécurité de Quarkus.  
- J'ai créé une classe TokenService qui génère un token JWT. Cette classe utilise 
l'API SmallRye JWT pour créer un token avec un émetteur, un nom d'utilisateur (upn) et des rôles.  
- J'ai configuré la sécurité de Quarkus dans le fichier application.properties.
Ces configurations activent JWT, définissent le mécanisme d'authentification comme MP-JWT, 
définissent le nom du royaume comme Quarkus et configurent le rafraîchissement des clés JWT.  
- J'ai sécurisé les points de terminaison dans la classe UtilisateurResource. 
- J'ai utilisé l'annotation @RolesAllowed pour spécifier quels rôles peuvent accéder à chaque point de 
terminaison. Dans cet exemple, seuls les utilisateurs avec le rôle "User" peuvent accéder au point de terminaison
getUtilisateurs, et seuls les utilisateurs avec le rôle "Admin" peuvent accéder au point de terminaison addUtilisateur.  
C'est une implémentation simplifiée d'un système d'authentification basé sur des tokens en
utilisant Quarkus et JWT. Selon vos besoins spécifiques, vous devrez peut-être ajuster cette implémentation.