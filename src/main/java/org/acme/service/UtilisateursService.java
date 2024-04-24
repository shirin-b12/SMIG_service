package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.model.Utilisateurs;
import org.acme.repository.UtilisateursRepository;
import org.acme.response.UtilisateurResponce;

import java.util.ArrayList;
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

    public List<UtilisateurResponce> listAll() {
        List<Utilisateurs> utilisateursList = utilisateurRepository.listAll();
        List<UtilisateurResponce> utilisateurResponceList = new ArrayList<>();
        for (Utilisateurs utilisateur : utilisateursList) {
            utilisateurResponceList.add(mapUtilisateurToUtilisateurResponse(utilisateur));
        }
        return utilisateurResponceList;
    }

    public UtilisateurResponce findById(int id) {

        return mapUtilisateurToUtilisateurResponse(utilisateurRepository.findById(id));
    }

    public UtilisateurResponce addUtilisateur(Utilisateurs utilisateur) {
        if (utilisateur != null) {
            utilisateurRepository.persist(utilisateur);
            return mapUtilisateurToUtilisateurResponse(utilisateur);
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

    public UtilisateurResponce updateUtilisateur(int id, Utilisateurs utilisateurUpdates) {
        Utilisateurs utilisateur = utilisateurRepository.findById(id);
        if (utilisateur != null) {
            utilisateur.setNom(utilisateurUpdates.getNom());
            utilisateur.setEmail(utilisateurUpdates.getEmail());
            utilisateur.setMot_de_passe(utilisateur.getMot_de_passe());
            utilisateur.setEtat_utilisateur(utilisateurUpdates.getEtat_utilisateur());
            utilisateurRepository.persist(utilisateur);
            return mapUtilisateurToUtilisateurResponse(utilisateur);
        }
        return null;
    }


    public void deleteUtilisateur(int id) {
        Utilisateurs utilisateur = utilisateurRepository.findById(id);

        if (utilisateur != null) {
            ressourceService.deleteRessourcebyCreateur(utilisateur.getId_utilisateur());
            favorieService.deleteFavoriebyUtilisateur(utilisateur.getId_utilisateur());
            utilisateurRepository.delete(utilisateur);
        }
    }
    public UtilisateurResponce mapUtilisateurToUtilisateurResponse(Utilisateurs utilisateur) {
        UtilisateurResponce utilisateurResponce = new UtilisateurResponce();
        utilisateurResponce.setId_utilisateur(utilisateur.getId_utilisateur());
        utilisateurResponce.setNom(utilisateur.getNom());
        utilisateurResponce.setPrenom(utilisateur.getPrenom());
        utilisateurResponce.setRole(utilisateur.getRole());
        utilisateurResponce.setEtat_utilisateur(utilisateur.getEtat_utilisateur());
        utilisateurResponce.setImage(utilisateur.getImageProfil());
        return utilisateurResponce;
    }

}
