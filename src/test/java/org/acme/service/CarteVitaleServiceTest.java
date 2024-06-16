package org.acme.service;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.acme.controller.ImagesController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.restassured.RestAssured.given;


@QuarkusTest
class CarteVitaleServiceTest {

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
