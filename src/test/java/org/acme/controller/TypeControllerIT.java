package org.acme.controller;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(ImagesController.class)
public class TypeControllerIT {

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
    public void testGetType() {
        int testImageId = 1; // Assuming an image with ID 1 exists in the database
        given()
                .when().get("http://localhost:8081/types/")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testCreateType() {

        given()
                .contentType(ContentType.JSON)
                .body("{\"nom_type\": \"test\"}")
                .header("Authorization", "Bearer " + token) // Use the retrieved token
                .when().post("http://localhost:8081/types/")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
}
