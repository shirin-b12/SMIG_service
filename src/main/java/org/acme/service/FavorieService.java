package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.acme.model.Favoris;
import org.acme.model.Ressources;
import org.acme.model.Utilisateurs;
import org.acme.repository.RessourcesRepository;
import org.acme.repository.UtilisateursRepository;
import org.acme.request.FavorieRequest;

import java.util.List;

@ApplicationScoped
public class FavorieService {
    @Inject
    UtilisateursRepository utilisateursRepository;

    @Inject
    RessourcesRepository ressourcesRepository;
    public void addFavorie(FavorieRequest favorieRequest) {
        Utilisateurs utilisateur = utilisateursRepository.findById(favorieRequest.getId_utilisateur());
        Ressources ressource = ressourcesRepository.findById(favorieRequest.getId_ressource());
        if ((utilisateur != null)&& (ressource != null)) {
            Favoris favorie = new Favoris();
            favorie.setId_utilisateur(utilisateur);
            favorie.setId_ressource(ressource);
            favorie.setDate_de_creation(new java.util.Date());
            Favoris.persist(favorie);
        }
    }
    public void removeFavorie(FavorieRequest favorieRequest) {
        Utilisateurs utilisateur = utilisateursRepository.findById(favorieRequest.getId_utilisateur());
        Ressources ressource = ressourcesRepository.findById(favorieRequest.getId_ressource());
        if ((utilisateur != null)&& (ressource != null)) {
            Favoris favorie = Favoris.find("id_utilisateur = ?1 and id_ressource = ?2", utilisateur, ressource).firstResult();
            if (favorie != null) {
                Favoris.deleteById(favorie.getId_favori());
            }
        }
    }
    @Path("/{id}")
    public List<Favoris> listFavorie(@PathParam("id") int id_utilisateur) {
        Utilisateurs utilisateur = utilisateursRepository.findById(id_utilisateur);
        if (utilisateur != null) {
            return Favoris.list("id_utilisateur = ?1", utilisateur);
        }
        return null;
    }
}
