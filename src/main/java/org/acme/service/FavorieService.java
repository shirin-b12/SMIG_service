package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Favoris;
import org.acme.model.Ressources;
import org.acme.model.Utilisateurs;
import org.acme.repository.RessourcesRepository;
import org.acme.repository.UtilisateursRepository;
import org.acme.request.FavorieReponce;
import org.acme.request.FavorieRequest;
import org.acme.request.RessourcesResponce;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FavorieService {
    @Inject
    UtilisateursRepository utilisateursRepository;

    @Inject
    RessourcesRepository ressourcesRepository;
    public void addFavorie(FavorieRequest favorieRequest) {
        Utilisateurs utilisateur = utilisateursRepository.findById(favorieRequest.getId_utilisateur());
        Ressources ressource = ressourcesRepository.findById(favorieRequest.getId_ressource());
        RessourcesResponce ressourcesResponce = new RessourcesResponce();
        ressourcesResponce.setId(ressource.getId_ressource());
        ressourcesResponce.setTitre(ressource.getTitre());
        ressourcesResponce.setDescription(ressource.getDescription());
        ressourcesResponce.setVisibilite(ressource.getVisibilite());
        ressourcesResponce.setDateDeCreation(ressource.getDate_de_creation().toString());
        ressourcesResponce.setNomCreateur(ressource.getCreateur().getNom());
        ressourcesResponce.setPrenomCreateur(ressource.getCreateur().getPrenom());
        ressourcesResponce.setNomCategorie(ressource.getCategorie().getNom_cat());
        ressourcesResponce.setNomType(ressource.getType().getNom_type());
        ressourcesResponce.setNomTag(ressource.getTag().getNom_tag());

        if ((utilisateur != null)&& (ressource != null)) {
            Favoris favoris = new Favoris();
            favoris.setId_utilisateur(utilisateur);
            favoris.setId_ressource(ressource);
            favoris.setDate_de_creation(new java.util.Date());
            Favoris.persist(favoris);
        }
    }
    public void removeFavorie(FavorieRequest favorieRequest) {
        Utilisateurs utilisateur = utilisateursRepository.findById(favorieRequest.getId_utilisateur());
        Ressources ressource = ressourcesRepository.findById(favorieRequest.getId_ressource());
        if ((utilisateur != null)&& (ressource != null)) {
            Favoris favoris = Favoris.find("id_utilisateur = ?1 and id_ressource = ?2", utilisateur, ressource).firstResult();
            if (favoris != null) {
                Favoris.deleteById(favoris.getId_favori());
            }
        }
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
}
