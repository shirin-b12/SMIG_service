package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Favorie;
import org.acme.model.Ressources;
import org.acme.model.Utilisateurs;
import org.acme.repository.RessourcesRepository;
import org.acme.repository.UtilisateursRepository;

import java.util.List;

@ApplicationScoped
public class FavorieService {
    @Inject
    UtilisateursRepository utilisateursRepository;

    @Inject
    RessourcesRepository ressourcesRepository;
    public void addFavorie(int id_utilisateur, int id_ressource) {
        Utilisateurs utilisateur = utilisateursRepository.findById(id_utilisateur);
        Ressources ressource = ressourcesRepository.findById(id_ressource);
        if ((utilisateur != null)&& (ressource != null)) {
            Favorie favorie = new Favorie();
            favorie.setId_utilisateur(utilisateur);
            favorie.setId_ressource(ressource);
            favorie.setDate_de_creation(new java.util.Date());
            Favorie.persist(favorie);
        }
    }
    public void removeFavorie(int id_utilisateur, int id_ressource) {
        Utilisateurs utilisateur = utilisateursRepository.findById(id_utilisateur);
        Ressources ressource = ressourcesRepository.findById(id_ressource);
        if ((utilisateur != null)&& (ressource != null)) {
            Favorie favorie = Favorie.find("id_utilisateur = ?1 and id_ressource = ?2", utilisateur, ressource).firstResult();
            if (favorie != null) {
                Favorie.deleteById(favorie.getId_favori());
            }
        }
    }
    public List<Favorie> listFavorie(int id_utilisateur) {
        Utilisateurs utilisateur = utilisateursRepository.findById(id_utilisateur);
        if (utilisateur != null) {
            return Favorie.list("id_utilisateur = ?1", utilisateur);
        }
        return null;
    }
}
