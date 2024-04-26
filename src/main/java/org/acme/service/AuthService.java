package org.acme.service;

import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Utilisateurs;
import org.acme.repository.AuthRepository;
import org.acme.repository.UtilisateursRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class AuthService {
    @Inject
    private TokenService tokenService;
    @Inject
    UtilisateursRepository utilisateurRepository;
    @Inject
    AuthRepository authRepository;

    public Map<String, String> login(String email, String motDePasse) {
        Utilisateurs user = utilisateurRepository.findByEmail(email);
        if (user != null && PasswordEncodersService.checkPassword(motDePasse, user.getMot_de_passe())) {
            return CreateTokens(user);
        } else {
            // Handle login failure
            throw new RuntimeException("Invalid email or password");
        }
    }
    public Map<String, String> login(String token) {
        Utilisateurs user = utilisateurRepository.findByToken(token);
        return CreateTokens(user);
    }
    private Map<String,String> CreateTokens(Utilisateurs user){


        String accessToken = tokenService.generateToken(user);
        String refreshToken = tokenService.generateRefreshToken(user);

        authRepository.updateRefreshToken(user.getId_utilisateur(), refreshToken);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }


}
