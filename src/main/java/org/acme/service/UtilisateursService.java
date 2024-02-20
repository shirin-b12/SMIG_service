package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Utilisateurs;
import org.acme.repository.UtilisateursRepository;

import java.util.List;

@ApplicationScoped
public class UtilisateursService {

    @Inject
    UtilisateursRepository utilisateurRepository;

    @Inject
    TokenService tokenService;

    public List<Utilisateurs> listAll() {
        return utilisateurRepository.listAll();
    }

    public Utilisateurs findById(Long id) {
        return utilisateurRepository.findById(id);
    }

    public Utilisateurs addUtilisateur(Utilisateurs utilisateur) {
        if (utilisateur != null) {
            utilisateurRepository.persist(utilisateur);
            return utilisateur;
        }
        return null;
    }
    public String login(String email, String motDePasse) {
        Utilisateurs user = utilisateurRepository.findByUsernameAndPassword(email, motDePasse);
        if (user != null) {
            return tokenService.generateToken(user);
        }
        return null;
    }
}
