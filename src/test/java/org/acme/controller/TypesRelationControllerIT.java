package org.acme.controller;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.transaction.Transactional;
import org.acme.model.TypesRelation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;

import java.io.Console;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@TestHTTPEndpoint(TypesRelationController.class)
public class TypesRelationControllerIT {

    private int idTest = 9999;

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
    @Transactional
    public void testAddTypesRelation(){
        TypesRelation typesRelation = new TypesRelation();
        typesRelation.setIntitule("Test Intitule");

        System.out.println("\n " + RestAssured.baseURI + " \n");

        given()
                .contentType(ContentType.JSON)
                .body(typesRelation)
                .when().post()
                .then()
                .statusCode(201)
                .body("intitule", is("Test Intitule"));
    }

    @Test
    public void testGetAllTypesRelations() {
        given()
                .when().get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);

        System.out.println("\n " + RestAssured.baseURI + " \n");
    }

    @Test
    public void testGetTypesRelation() {
        given()
                .pathParam("id", idTest)
                .when().get("/{id}")
                .then()
                .statusCode(200);


        System.out.println("\n " + RestAssured.baseURI + " \n");
    }

    @Test
    public void testUpdateTypesRelation() {
        TypesRelation tr = new TypesRelation();
        tr.setIntitule("Updated Relation");
        System.out.println("\n " + RestAssured.baseURI + " \n");
        given()
                .contentType(ContentType.JSON)
                .body(tr)
                .when().put("/999999")
                .then()
                .statusCode(200);  // Vérifiez que le code de statut est 200

    }

    @Test
    @Transactional
    public void testDeleteTypesRelation(){
        given()
                .pathParam("id", idTest)
                .when().delete("/{id}")
                .then()
                .statusCode(200)
                .body(is("TypesRelation deleted successfully"));


        System.out.println("\n " + RestAssured.baseURI + " \n");
    }
}

