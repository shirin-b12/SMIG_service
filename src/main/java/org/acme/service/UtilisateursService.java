package org.acme.service;

import org.jboss.resteasy.reactive.multipart.FileUpload;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.acme.model.EtatUtilisateur;
import org.acme.model.Images;
import org.acme.model.Utilisateurs;
import org.acme.repository.RolesRepository;
import org.acme.repository.UtilisateursRepository;
import org.acme.request.ChangeStatu;
import org.acme.request.UpdateUserRequest;
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

    @Inject
    ImagesService imagesService;

    public List<UtilisateurResponce> listAll() {
        List<Utilisateurs> utilisateursList = utilisateurRepository.listAll();
        List<UtilisateurResponce> utilisateurResponceList = new ArrayList<>();
        for (Utilisateurs utilisateur : utilisateursList) {
            utilisateurResponceList.add(utilisateur.mapUtilisateurToUtilisateurResponse());
        }
        return utilisateurResponceList;
    }

    public UtilisateurResponce findById(int id) {

        return utilisateurRepository.findById(id).mapUtilisateurToUtilisateurResponse();
    }

    public Utilisateurs findUserById(int id) {

        return utilisateurRepository.findById(id);
    }

    public UtilisateurResponce addUtilisateur(Utilisateurs utilisateur) {
        if (utilisateur != null) {
            PasswordEncodersService passwordEncodersService = new PasswordEncodersService();
            utilisateur.setMot_de_passe(passwordEncodersService.encodePassword(utilisateur.getMot_de_passe()));
            utilisateur.setRole(RolesRepository.findById(1));
            utilisateur.setEtat_utilisateur(EtatUtilisateur.normal);
            utilisateurRepository.persist(utilisateur);
            return utilisateur.mapUtilisateurToUtilisateurResponse();
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
            return utilisateur.mapUtilisateurToUtilisateurResponse();
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
    @Transactional
    public UtilisateurResponce addProfileImageToUser(int userId, FileUpload fileUpload) {
        Utilisateurs user = utilisateurRepository.findById(userId);
        if (user == null) {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }
        Images image = imagesService.addImage("Profile image for user ID " + userId, fileUpload);
        user.setImageProfil(image);
        utilisateurRepository.persist(user);
        return user.mapUtilisateurToUtilisateurResponse();
    }


}
