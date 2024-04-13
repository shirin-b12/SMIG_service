package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Favoris;
import org.acme.model.Ressources;
import org.acme.model.Utilisateurs;
import org.acme.repository.FavorieRepository;
import org.acme.repository.RessourcesRepository;
import org.acme.repository.UtilisateursRepository;

import java.util.List;

@ApplicationScoped
public class FavorieService {
    @Inject
    UtilisateursRepository utilisateursRepository;

    @Inject
    RessourcesRepository ressourcesRepository;
    @Inject
    FavorieRepository favorisRepository;
    public void addFavorie( int id_utilisateur, int id_ressource) {
        Utilisateurs utilisateur =utilisateursRepository.findById(id_utilisateur);
        Ressources ressource = ressourcesRepository.findById(id_ressource);
        if ((utilisateur != null)&& (ressource != null)) {
            Favoris newfavorie = new Favoris();
            newfavorie.setUtilisateur(utilisateur);
            newfavorie.setRessource(ressource);
            newfavorie.setDate_de_creation(new java.util.Date());
            try {
                favorisRepository.persist(newfavorie);
            }catch (Exception e){
                System.out.println(e.getMessage());

        }
    }
    }
    public void removeFavorie( int id_ressource, int id_utilisateur) {
        Ressources ressource = ressourcesRepository.findById(id_ressource);
        Utilisateurs utilisateur = utilisateursRepository.findById(id_utilisateur);

        if ((utilisateur != null)&& (ressource != null)) {
            Favoris favorie = Favoris.find("id_utilisateur = ?1 and id_ressource = ?2", utilisateur, ressource).firstResult();
            if (favorie != null) {
                Favoris.deleteById(favorie.getId_favori());
            }
        }
    }
    public List<Favoris> listFavorie(int id_utilisateur) {
        Utilisateurs utilisateur = utilisateursRepository.findById(id_utilisateur);
        if (utilisateur != null) {
            return Favoris.list("id_utilisateur = ?1", utilisateur);
        }
        return null;
    }
    public void deleteFavoriebyUtilisateur(int id_utilisateur){
        try {
            Favoris.delete("utilisateur.id = ?1", id_utilisateur);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
