package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CarteVitaleService {
    public boolean verificationCarteVitale(String carteVitale) {
        return carteVitale.matches("\\d+");
    }
}
