package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CarteVitalService {
    public boolean verificationCarteVital(String carteVital) {
        return carteVital.matches("\\d+");
    }
}
