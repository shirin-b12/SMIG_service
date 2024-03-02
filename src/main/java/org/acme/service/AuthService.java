package org.acme.service;

import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Utilisateurs;
import org.acme.repository.AuthRepository;
import org.acme.repository.UtilisateursRepository;

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
        Utilisateurs user = utilisateurRepository.findByUsernameAndPassword(email, motDePasse);
        return CreateTokens(user);
    }
    public Map<String, String> login(String token) {
        Utilisateurs user = utilisateurRepository.findByToken(token);
        return CreateTokens(user);
    }
    private Map<String,String> CreateTokens(Utilisateurs user){
        String accessToken = tokenService.generateToken(user);
        String refreshToken = tokenService.generateRefreshToken(user);

        authRepository.updateRefreshToken(user.id_utilisateur, refreshToken);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

}