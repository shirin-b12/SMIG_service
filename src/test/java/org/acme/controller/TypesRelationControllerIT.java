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
import org.mockito.Mock;

import java.io.Console;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyInt;

@QuarkusTest
@TestHTTPEndpoint(TypesRelationController.class)
public class TypesRelationControllerIT {

    @Mock
    private TypesRelation mockTypesRelation;
    private int idTest = 9999;

    private String token;

    @BeforeEach
    public void setUp() {
        mockTypesRelation = new TypesRelation();

        mockTypesRelation.setIntitule("Test Intitule");

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
    public void testAddTypesRelation(){

        Response response = given()
                .contentType(ContentType.JSON)
                .body(mockTypesRelation)
                .when().post()
                .then()
                .statusCode(201)
                .body("idTypeRelation", is(notNullValue()))
                .body("intitule", is("Test Intitule"))
                .extract().response();

        //System.out.println("\n " + response.getBody().print() + " \n");
    }

    @Test
    public void testGetAllTypesRelations() {
        Response response = given()
                .when().get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();

        //System.out.println("\n " + response.getBody().print() + " \n");
    }

    @Test
    public void testGetTypesRelation() {

        Response response = given()
                .pathParam("id", idTest)
                .when().get("/{id}")
                .then()
                .statusCode(200)
                .extract().response();

        //System.out.println("\n " + response.getBody().print() + " \n");
    }

    @Test
    public void testUpdateTypesRelation() {
        TypesRelation tr = new TypesRelation();
        tr.setIntitule("Updated Relation");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(tr)
                .when().put("/999999")
                .then()
                .statusCode(200)
                .extract().response();  // Vérifiez que le code de statut est 200

        //System.out.println("\n " + response.jsonPath().get() + " \n");
    }

    @Test
    public void testDeleteTypesRelation(){
        Response response = given()
                .pathParam("id", idTest)
                .when().delete("/{id}")
                .then()
                .statusCode(200)
                .body(is("TypesRelation deleted successfully"))
                .extract().response();

        //System.out.println("\n " + response.getBody().print() + " \n");
    }
}

