package org.acme.controller;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(CategoriesController.class)
public class CategoriesControllerIT {
    private String token;

    @BeforeEach
    public void setUp() {
        //RestAssured.baseURI = "http://localhost:8081"; // or the actual base URL if different
    }


    @Test
    public void testGetCategories() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"id_cat\":\"2\"}")
                .when().get("http://localhost:8081/categories")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreateCategory() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"test\", \"mot_de_passe\":\"test\"}")
                .body("{\"nom_cat\":\"chapeaux\"}")
                .when().post("http://localhost:8081/categories")
                .then()
                .statusCode(200);
    }

}