package org.acme.service;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

import org.acme.model.*;
import org.acme.repository.*;
import org.acme.request.RessourcesRequest;
import org.acme.request.RessourcesResponce;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RessourcesService {

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

    private final UnicastProcessor<RessourcesResponce> ressourceStream = UnicastProcessor.create();

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
        ressource.categorie = categorie;
        ressource.type = type;
        ressource.tag = tag;
        ressource.createur = createur;
        ressource.titre = request.getTitre();
        ressource.description = request.getDescription();
        ressource.visibilite = request.getVisibilite();
        ressource.date_de_creation = request.getDateDeCreation();

        ressourcesRepository.persist(ressource);

        RessourcesResponce response = new RessourcesResponce();
        response.setId(ressource.id_ressource);
        response.setTitre(ressource.titre);
        response.setDescription(ressource.description);
        response.setVisibilite(ressource.visibilite);
        response.setDateDeCreation(ressource.date_de_creation.toString());

        ressourceStream.onNext(response);
        return ressource;
    }

}
