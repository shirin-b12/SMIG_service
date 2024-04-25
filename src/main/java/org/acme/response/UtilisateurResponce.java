package org.acme.response;

import org.acme.model.EtatUtilisateur;
import org.acme.model.Images;
import org.acme.model.Roles;
import org.acme.model.Utilisateurs;

public class UtilisateurResponce {
    private Images image;
    private int id_utilisateur;
    private String nom;
    private String prenom;
    private Roles role;
    private EtatUtilisateur etat_utilisateur;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public EtatUtilisateur getEtat_utilisateur() {
        return etat_utilisateur;
    }

    public void setEtat_utilisateur(EtatUtilisateur etat_utilisateur) {
        this.etat_utilisateur = etat_utilisateur;
    }

    public Images getImage() {
        return image;
    }

    public void setImage(Images image) {
        this.image = image;
    }

    public UtilisateurResponce mapUtilisateurToUtilisateurResponse(Utilisateurs utilisateur) {
        UtilisateurResponce utilisateurResponce = new UtilisateurResponce();
        utilisateurResponce.setId_utilisateur(utilisateur.getId_utilisateur());
        utilisateurResponce.setNom(utilisateur.getNom());
        utilisateurResponce.setPrenom(utilisateur.getPrenom());
        utilisateurResponce.setRole(utilisateur.getRole());
        utilisateurResponce.setEmail(utilisateur.getEmail());
        utilisateurResponce.setEtat_utilisateur(utilisateur.getEtat_utilisateur());
        utilisateurResponce.setImage(utilisateur.getImageProfil());
        return utilisateurResponce;
    }
}
