package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.model.EtatUtilisateur;
import org.acme.model.Utilisateurs;
import org.acme.repository.UtilisateursRepository;
import org.acme.request.UpdateUserRequest;
import org.acme.request.ChangeStatu;

import java.util.List;

@ApplicationScoped
public class UtilisateursService {

    @Inject
    UtilisateursRepository utilisateurRepository;

    @Inject
    TokenService tokenService;
    @Inject
    FavorieService favorieService;
    @Inject
    RessourcesService ressourceService;

    public List<Utilisateurs> listAll() {
        return utilisateurRepository.listAll();
    }

    public Utilisateurs findById(int id) {
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

    @Transactional
    public Utilisateurs updateUtilisateur(int id, UpdateUserRequest request) {
        Utilisateurs utilisateur = findById(id);
        if (utilisateur != null) {
            utilisateur.setNom(request.getNom());
            utilisateur.setPrenom(request.getPrenom());
            utilisateur.setEmail(request.getEmail());
            utilisateur.setMot_de_passe(utilisateur.getMot_de_passe());
            utilisateur.setEtat_utilisateur(utilisateur.getEtat_utilisateur());
            utilisateurRepository.persist(utilisateur);
            return utilisateur;
        }
        return null;
    }

    public boolean deleteUtilisateur(int id) {
        Utilisateurs utilisateur = findById(id);
        if (utilisateur != null) {
            ressourceService.deleteRessourcebyCreateur(utilisateur.getId_utilisateur());
            favorieService.deleteFavoriebyUtilisateur(utilisateur.getId_utilisateur());
            utilisateurRepository.deleteUtilisateur(utilisateur.getId_utilisateur());
            return true;
        }
        return false;
    }

    public Utilisateurs updateUtilisateurStatu(int id, ChangeStatu utilisateur) {
        Utilisateurs user = findById(id);
        if (user != null) {

            user.setEtat_utilisateur(user.setEtat_utilisateur(EtatUtilisateur.valueOf(utilisateur.getStatu())));
            utilisateurRepository.persist(user);
            return user;
        }
        return null;
    }
}
