package org.acme.service;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
    ImagesRepository imagesRepository;
    @Inject
    TypeRepository typeRepository;
    @Inject
    TagRepository tagRepository;
    @Inject
    UtilisateursRepository utilisateursRepository;

    public Multi<RessourcesResponce> getRessourceStream() {
        return ressourceStream;
    }

    @Transactional
    public Ressources findById(int id) {
        Ressources ressource = ressourcesRepository.findById(id);
        if (ressource != null) {
            ressource.setVue(ressource.getVue() + 1);
            ressourcesRepository.persist(ressource);
        }
        return ressource;
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

    public Ressources linkImage(int imageId, int ressource) {

        Ressources ressources = ressourcesRepository.findById(ressource);

        Images image = imagesRepository.findById(imageId);

        if (ressources == null) {
            return null;
        }

        ressources.setImage(image);


        ressourcesRepository.update(ressource, ressources);
        return ressources;
    }

    public Ressources updateRessource(int id, RessourcesRequest request) {
        Ressources ressource = ressourcesRepository.findById(id);
        if (ressource == null) {
            return null;
        }

        ressource.setTitre(request.getTitre());
        ressource.setDescription(request.getDescription());
        ressource.setVisibilite(request.getVisibilite());
        ressource.setDate_de_creation(request.getDateDeCreation());

        ressourcesRepository.persist(ressource);
        return ressource;
    }

    public void deleteRessource(int id) {
        Ressources ressource = findById(id);
        if (ressource != null) {
            ressourcesRepository.delete(ressource);
        }
    }

    public void deleteRessourcebyCreateur(int id_createur) {
        try {
            ressourcesRepository.deletebyCreateur(id_createur);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Ressources validateRessource(int id) {
        Ressources ressource = findById(id);
        if (ressource != null) {
            ressource.setValidate_Ressource(true);
            ressourcesRepository.persist(ressource);
            return ressource;
        }
        return null;
    }

    public List<Ressources> findByCreateurId(int createurId) {
        return ressourcesRepository.findByCreateurId(createurId);
    }
}
