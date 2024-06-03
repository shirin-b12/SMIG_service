package org.acme.controller;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(ImagesController.class)

public class FavoriControllerIT {
    private String token;

    @BeforeEach
    public void setUp() {
        loginAndRetrieveToken();
    }

    private void loginAndRetrieveToken() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"shirin@laqueen.com\", \"mot_de_passe\":\"123\"}")
                .when().post("http://localhost:8081/utilisateur/login")
                .then()
                .statusCode(200)
                .extract().response();

        token = response.jsonPath().getString("accessToken");
    }
    @Test
    public void addFavorie (){
         given()
                .contentType(ContentType.JSON)
                .body("{\"id_ressource\":\"28\", \"id_utilisateur\":\"27\"}")
                 .header("Authorization", "Bearer " + token)
                .when().post("http://localhost:8081/favori")
                .then()
                .statusCode(200);
    }
    @Test
    public void addFavorieErreur (){
        given()
                .contentType(ContentType.JSON)
                .body("{\"id_ressource\":\"1\", \"id_utilisateur\":\"1\"}")
                .header("Authorization", "Bearer " + token)
                .when().post("http://localhost:8081/favori")
                .then()
                .statusCode(400)
                .extract().response();
    }
    @Test
    public void listFavorie (){
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("http://localhost:8081/favori/30")
                .then()
                .statusCode(204);
    }
    @Test
    public void removeFavorie (){
        given()
                .contentType(ContentType.JSON)
                .body("{\"id_ressource\":\"28\", \"id_utilisateur\":\"27\"}")
                .header("Authorization", "Bearer " + token)
                .when().delete("http://localhost:8081/favori")
                .then()
                .statusCode(200);
    }




}
