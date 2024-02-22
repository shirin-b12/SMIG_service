package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.*;
import org.acme.repository.*;

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

    public Optional<Ressources> createRessource(Ressources ressource, int c, int t, int tg, int u) {
        Optional<Categories> categorie = Optional.ofNullable(categoriesRepository.findById((int) c));
        Optional<Type> type = Optional.ofNullable(typeRepository.findById((int) t));
        Optional<Tag> tag = Optional.ofNullable(tagRepository.findById((int) tg));
        Optional<Utilisateurs> createur = Optional.ofNullable(utilisateursRepository.findById((int) u));

        if (!categorie.isPresent() || !type.isPresent() || !tag.isPresent() || !createur.isPresent()) {
            return Optional.empty();
        }

        ressource.categorie = categorie.get();
        ressource.type = type.get();
        ressource.tag = tag.get();
        ressource.createur = createur.get();

        ressourcesRepository.persist(ressource);

        return Optional.of(ressource);
    }
}
