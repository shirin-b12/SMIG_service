package org.acme.controller;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(TagController.class)
public class TagControllerIT {

    private String token;

    @BeforeEach
    public void setUp() {
        //RestAssured.baseURI = "http://localhost:8081"; // or the actual base URL if different
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
    public void testGetTags() {
        given()
                .contentType(ContentType.JSON)
                .when().get("http://localhost:8081/tags")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreateTag() {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token) // Utiliser le jeton récupéré
                .body("{\"nom_tag\":\"tag\"}")
                .when().post("http://localhost:8081/tags")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreateTagFailed() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"NomTags\":\"tag\"}")
                .when().post("http://localhost:8081/tags")
                .then()
                .statusCode(400);
    }

}