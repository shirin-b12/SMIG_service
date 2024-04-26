package org.acme.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Utilisateurs;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;


@ApplicationScoped
public class TokenService {


    public String generateToken(Utilisateurs user) {
        String role = user.getRole().getNom_role();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + TimeUnit.DAYS.toMillis(1));
        if (user != null) {
            return Jwt.issuer("https://localhost:8081/")
                    .upn(String.valueOf(user.getId_utilisateur()))
                    .groups(new HashSet<>(List.of(role)))
                    .expiresAt(expiryDate.toInstant())
                    .sign();
        } else {
           return null;
        }
    }
    public String generateRefreshToken(Utilisateurs user) {
        return Jwt.issuer("https://localhost:8081/")
                .upn(String.valueOf(user.getId_utilisateur()))
                .claim("refresh", true)
                .sign();
    }


}
