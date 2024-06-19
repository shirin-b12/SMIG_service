package org.acme.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CarteVitaleServiceTest {

    private CarteVitaleService carteVitaleService;

    @BeforeEach
    public void setUp() {
        carteVitaleService = new CarteVitaleService();
    }

    @Test
    public void testVerifierCarteVitaleValide() {
        String carteVitale = "1234"; // Utiliser une carte Vitale valide
        boolean resultat = carteVitaleService.verificationCarteVitale(carteVitale);
        assertTrue(resultat, "La carte Vitale devrait être valide");
    }

    @Test
    public void testVerifierCarteVitaleInvalide() {
        String carteVitale = "test"; // Utiliser une carte Vitale invalide
        boolean resultat = carteVitaleService.verificationCarteVitale(carteVitale);
        assertFalse(resultat, "La carte Vitale devrait être invalide");
    }

    @Test
    public void testVerifierCarteVitaleVide() {
        String carteVitale = ""; // Tester le cas où la carte Vitale est vide
        boolean resultat = carteVitaleService.verificationCarteVitale(carteVitale);
        assertFalse(resultat, "La carte Vitale vide devrait être invalide");
    }
}
