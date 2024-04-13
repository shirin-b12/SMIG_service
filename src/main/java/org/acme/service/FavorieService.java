package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.acme.model.Favoris;
import org.acme.model.Ressources;
import org.acme.model.Utilisateurs;
import org.acme.repository.RessourcesRepository;
import org.acme.repository.UtilisateursRepository;
import org.acme.request.FavorieReponce;
import org.acme.request.FavorieRequest;
import org.acme.request.RessourcesResponce;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FavorieService {
    @Inject
    UtilisateursRepository utilisateursRepository;

    @Inject
    RessourcesRepository ressourcesRepository;


    @Transactional
    public Response addFavori(FavorieRequest favoriRequest) {
        Utilisateurs utilisateur = utilisateursRepository.findById(favoriRequest.getId_utilisateur());
        Ressources ressource = ressourcesRepository.findById(favoriRequest.getId_ressource());

        if ((utilisateur != null) && (ressource != null)) {
            Favoris existingFavoris = Favoris.find("id_utilisateur = ?1 and id_ressource = ?2", utilisateur, ressource).firstResult();
            if (existingFavoris != null) {

               return Response.ok("existe").build();
            } else {
                // Favori with same attributes does not exist, persist data
                Favoris favoris = new Favoris();
                favoris.setId_utilisateur(utilisateur);
                favoris.setId_ressource(ressource);
                favoris.setDate_de_creation(new Date(Timestamp.valueOf(LocalDateTime.now()).getTime()));
                Favoris.persist(favoris);
                return Response.ok("added").build();
            }
        }
       return Response.status(Response.Status.BAD_REQUEST).build();
    }
    public Response removeFavorie(FavorieRequest favorieRequest) {
        Utilisateurs utilisateur = utilisateursRepository.findById(favorieRequest.getId_utilisateur());
        Ressources ressource = ressourcesRepository.findById(favorieRequest.getId_ressource());
        if ((utilisateur != null)&& (ressource != null)) {
            Favoris favoris = Favoris.find("id_utilisateur = ?1 and id_ressource = ?2", utilisateur, ressource).firstResult();
            if (favoris != null) {
                Favoris.deleteById(favoris.getId_favori());
                return Response.ok("deleted").build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    public List<FavorieReponce> listFavorie(int id_utilisateur) {
        Utilisateurs utilisateur = utilisateursRepository.findById(id_utilisateur);
        if (utilisateur != null) {
            return Favoris.list("id_utilisateur = ?1", utilisateur)
                    .stream()
                    .map(favoris -> new FavorieReponce((Favoris) favoris))
                    .collect(Collectors.toList());
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
