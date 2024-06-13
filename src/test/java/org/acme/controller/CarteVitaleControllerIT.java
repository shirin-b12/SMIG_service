package org.acme.controller;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


@QuarkusTest
@TestHTTPEndpoint(CarteVitaleController.class)

// Test unitaire d'API CarteVitaleControllerIT

public class CarteVitaleControllerIT {

    private String token;

    @BeforeEach
    public void setUp() {
        //RestAssured.baseURI = "http://localhost:8081"; // or the actual base URL if different
    }


    @Test
    public void testVerifierEndpointValide() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"carteVitale\":\"1234\"}")
                .when().post("http://localhost:8081/carteVitale")
                .then()
                .statusCode(200);
    }

    @Test
    public void testVerifierEndpointInvalide() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"carteVitale\":\"test\"}")
                .when().post("http://localhost:8081/carteVitale")
                .then()
                .statusCode(400);
    }
}