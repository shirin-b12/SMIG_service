package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.acme.response.UtilisateurResponce;

@Entity
public class Utilisateurs extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id_utilisateur;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String mot_de_passe;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String tel;

    @ManyToOne
    @JoinColumn(name = "id_role", nullable = true)
    private Roles role;

    @Column(nullable = true,unique = true)
    private String tokenRefrech;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatUtilisateur etat_utilisateur;



    public void setToken(String token) {
        this.tokenRefrech = token;
    }


    @OneToOne
    @JoinColumn(name = "id_image_profil", referencedColumnName = "id_image")
    private Images imageProfil;

    public Images getImageProfil() {
        return imageProfil;
    }

    public void setImageProfil(Images imageProfil) {
        this.imageProfil = imageProfil;
    }

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

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public EtatUtilisateur setEtat_utilisateur(EtatUtilisateur etat_utilisateur) {
        this.etat_utilisateur = etat_utilisateur;
        return etat_utilisateur;
    }

    public UtilisateurResponce mapUtilisateurToUtilisateurResponse() {
        UtilisateurResponce utilisateurResponce = new UtilisateurResponce();
        utilisateurResponce.setId_utilisateur(this.getId_utilisateur());
        utilisateurResponce.setNom(this.getNom());
        utilisateurResponce.setPrenom(this.getPrenom());
        utilisateurResponce.setRole(this.getRole());
        utilisateurResponce.setEtat_utilisateur(this.getEtat_utilisateur());
        utilisateurResponce.setImage(this.getImageProfil());
        utilisateurResponce.setEmail(this.getEmail());
        return utilisateurResponce;
    }
}
