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

    public Utilisateurs updateUtilisateur(int id, Utilisateurs utilisateurUpdates) {
        Utilisateurs utilisateur = findById(id);
        if (utilisateur != null) {
            utilisateur.setNom(utilisateurUpdates.getNom());
            utilisateur.setEmail(utilisateurUpdates.getEmail());
            utilisateur.setMot_de_passe(utilisateur.getMot_de_passe());
            utilisateur.setEtat_utilisateur(utilisateurUpdates.getEtat_utilisateur());
            utilisateurRepository.persist(utilisateur);
            return utilisateur;
        }
        return null;
    }

    public void deleteUtilisateur(int id) {
        Utilisateurs utilisateur = findById(id);
        ressourceService.deleteRessourcebyCreateur(utilisateur.getId_utilisateur());
        favorieService.deleteFavoriebyUtilisateur(utilisateur.getId_utilisateur());


        if (utilisateur != null) {
            utilisateurRepository.delete(utilisateur);
        }
    }

}
