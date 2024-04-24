package org.acme.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.model.EtatUtilisateur;
import org.acme.model.Utilisateurs;
import org.acme.repository.RolesRepository;
import org.acme.repository.UtilisateursRepository;
import org.acme.request.ChangeStatu;
import org.acme.request.UpdateUserRequest;
import org.acme.response.UtilisateurResponce;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            PasswordEncodersService passwordEncodersService = new PasswordEncodersService();
            utilisateur.setMot_de_passe(passwordEncodersService.encodePassword(utilisateur.getMot_de_passe()));
            utilisateur.setRole(RolesRepository.findById(1));
            utilisateur.setEtat_utilisateur(EtatUtilisateur.normal);
            utilisateurRepository.persist(utilisateur);
            return mapUtilisateurToUtilisateurResponse(utilisateur);
        }
        return null;
    }
    @Transactional
    public UtilisateurResponce updateUtilisateur(int id, UpdateUserRequest request) {
        Utilisateurs utilisateur = utilisateurRepository.findById(id);
        if (utilisateur != null) {
            utilisateur.setNom(request.getNom());
            utilisateur.setPrenom(request.getPrenom());
            utilisateur.setEmail(request.getEmail());
            utilisateur.setMot_de_passe(utilisateur.getMot_de_passe());
            utilisateur.setEtat_utilisateur(utilisateur.getEtat_utilisateur());
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

    public Utilisateurs updateUtilisateurStatu(int id, ChangeStatu utilisateur) {
        Utilisateurs user = utilisateurRepository.findById(id);
        if (user != null) {

            user.setEtat_utilisateur(user.setEtat_utilisateur(EtatUtilisateur.valueOf(utilisateur.getStatu())));
            utilisateurRepository.persist(user);
            return user;
        }
        return null;
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
