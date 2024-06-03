package org.acme.controller;

import io.restassured.response.Response;
import org.acme.model.Categories;
import org.acme.model.Ressources;
import org.acme.model.Type;
import org.acme.model.Tag;
import org.acme.model.Utilisateurs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestHTTPEndpoint(RessourcesController.class)
public class RessourcesControllerIT {

    private String token;

    @BeforeEach
    public void setUp() {
        loginAndRetrieveToken();
    }

    private void loginAndRetrieveToken() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"test\", \"mot_de_passe\":\"test\"}")
                .when().post("http://localhost:8081/utilisateur/login")
                .then()
                .statusCode(200)
                .extract().response();

        token = response.jsonPath().getString("accessToken");
    }

    @Test
    public void testGetRessourcesEndpoint() {
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/all")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0)); // Assure que la réponse contient au moins une ressource
    }

    @Test
    public void testCreateRessource() {
        Utilisateurs test = new Utilisateurs();
        test.setId_utilisateur(21);

        Ressources ressourceMock = new Ressources();
        ressourceMock.setCreateur(test);
        ressourceMock.setTitre("Test Ressource");
        ressourceMock.setDescription("Ceci est un test de création de ressource");
        ressourceMock.setCategorie(Categories.findById(1));
        ressourceMock.setType(Type.findById(1));
        ressourceMock.setTag(Tag.findById(1));

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(ressourceMock)
                .when()
                .post("/create")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("titre", equalTo("Test Ressource"));
    }

    @Test
    public void testGetRessourcesByCreateur() {
        given()
                .header("Authorization", "Bearer " + token)
                .pathParam("createurId", 21)
                .when().get("/byCreateur/{createurId}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
}
