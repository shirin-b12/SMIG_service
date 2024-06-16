package org.acme.service;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CategoriesServiceTest {

    @Test
    public void testGetCategories() {
        given()
                .contentType(ContentType.JSON)
                .when().get("http://localhost:8081/categories")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreateCategory() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"nom_cat\":\"technologie\"}")
                .when().post("http://localhost:8081/categories")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreateCategoryFailed() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"nom\":\"technologie\"}")
                .when().post("http://localhost:8081/categories")
                .then()
                .statusCode(400);
    }

}
