package org.acme.service;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.*;
import org.acme.repository.*;
import org.acme.request.RessourcesRequest;
import org.acme.request.RessourcesResponce;

import java.util.List;

@ApplicationScoped
public class RessourcesService {

    private final UnicastProcessor<RessourcesResponce> ressourceStream = UnicastProcessor.create();
    @Inject
    RessourcesRepository ressourcesRepository;
    @Inject
    CategoriesRepository categoriesRepository;
    @Inject
    TypeRepository typeRepository;
    @Inject
    TagRepository tagRepository;
    @Inject
    UtilisateursRepository utilisateursRepository;

    public Multi<RessourcesResponce> getRessourceStream() {
        return ressourceStream;
    }

    public List<Ressources> listAll() {
        return ressourcesRepository.listAll();
    }

    public Ressources createRessource(RessourcesRequest request) {

        Categories categorie = categoriesRepository.findById(request.getIdCat());
        Type type = typeRepository.findById(request.getIdType());
        Tag tag = tagRepository.findById(request.getIdTag());
        Utilisateurs createur = utilisateursRepository.findById(request.getIdCreateur());

        if (categorie == null || type == null || tag == null || createur == null) {
            return null;
        }

        Ressources ressource = new Ressources();
        ressource.setCategorie(categorie);
        ressource.setType(type);
        ressource.setTag(tag);
        ressource.setCreateur(createur);
        ressource.setTitre(request.getTitre());
        ressource.setDescription(request.getDescription());
        ressource.setVisibilite(request.getVisibilite());
        ressource.setDate_de_creation(request.getDateDeCreation());


        ressourcesRepository.persist(ressource);

        RessourcesResponce response = new RessourcesResponce();
        response.setId(ressource.getId_ressource());
        response.setTitre(ressource.getTitre());
        response.setDescription(ressource.getDescription());
        response.setVisibilite(ressource.getVisibilite());
        response.setDateDeCreation(ressource.getDate_de_creation().toString());

        ressourceStream.onNext(response);
        return ressource;
    }

}
