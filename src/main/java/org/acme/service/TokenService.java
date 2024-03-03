package org.acme.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Utilisateurs;

import java.util.HashSet;
import java.util.List;


@ApplicationScoped
public class TokenService {


    public String generateToken(Utilisateurs user) {
        String role = user.getRole().nom_role;
        return Jwt.issuer("https://localhost:8081/")
                .upn(String.valueOf(user.getId_utilisateur()))
                .groups(new HashSet<>(List.of(role)))
                .sign();
    }
}