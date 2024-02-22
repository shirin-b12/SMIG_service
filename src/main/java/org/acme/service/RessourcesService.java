package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.*;
import org.acme.repository.*;
import org.acme.request.RessourcesRequest;

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

    public List<Ressources> listAll() {
        return ressourcesRepository.listAll();
    }

    public Optional<Ressources> createRessource(RessourcesRequest request) {
        Optional<Categories> categorie = Optional.ofNullable(categoriesRepository.findById(request.getIdCat()));
        Optional<Type> type = Optional.ofNullable(typeRepository.findById(request.getIdType()));
        Optional<Tag> tag = Optional.ofNullable(tagRepository.findById(request.getIdTag()));
        Optional<Utilisateurs> createur = Optional.ofNullable(utilisateursRepository.findById(request.getIdCreateur()));

        if (!categorie.isPresent() || !type.isPresent() || !tag.isPresent() || !createur.isPresent()) {
            return Optional.empty();
        }

        Ressources ressource = new Ressources();
        // Set properties from request to ressource
        ressource.setCategorie(categorie.get());
        ressource.setType(type.get());
        ressource.setTag(tag.get());
        ressource.setCreateur(createur.get());
        ressource.setTitre(request.getTitre());
        ressource.setDescription(request.getDescription());
        ressource.setVisibilite(request.getVisibilite());
        // Set other properties as necessary

        ressourcesRepository.persist(ressource);

        return Optional.of(ressource);
    }

}
