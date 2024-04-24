package org.acme.service;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.*;
import org.acme.repository.*;
import org.acme.request.RessourcesRequest;
import org.acme.response.RessourcesResponce;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
    private static final Logger LOGGER = Logger.getLogger(RessourcesService.class.getName());

    public Multi<RessourcesResponce> getRessourceStream() {
        return ressourceStream;
    }

    public RessourcesResponce findById(int id) throws Exception {
        Ressources ressource = ressourcesRepository.findById(id);
        if (ressource == null) {
            throw new Exception("La ressource n'a pas été trouvée.");
        }
        if (ressource.getCreateur() == null || ressource.getCategorie() == null || ressource.getType() == null || ressource.getTag() == null) {
            throw new Exception("Un des objets liés à la ressource est null.");
        }
        return MapperRessourceToRessourceResponse(ressource);

    }

    public List<RessourcesResponce> listAll() {
        List<Ressources> allRessources = ressourcesRepository.listAll();
        List<RessourcesResponce> responceList = new ArrayList<>();

        for (Ressources ressource : allRessources) {
            try {
                responceList.add(MapperRessourceToRessourceResponse(ressource));
            } catch (Exception e) {
              LOGGER.severe("Une exception s'est produite lors de la recherche des ressources: " + e.getMessage());
            }

        }
        return responceList;
    }

    public RessourcesResponce createRessource(RessourcesRequest request) throws Exception {

        Categories categorie = categoriesRepository.findById(request.getIdCat());
        Type type = typeRepository.findById(request.getIdType());
        Tag tag = tagRepository.findById(request.getIdTag());
        Utilisateurs createur = utilisateursRepository.findById(request.getIdCreateur());

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
        RessourcesResponce response = MapperRessourceToRessourceResponse(ressource);
        ressourceStream.onNext(response);
        return response;

    }

    public Ressources linkImage(int imageId, int ressource) {

        Ressources ressources = ressourcesRepository.findById(ressource);

        Images image = imagesRepository.findById(imageId);

        if (ressources == null ) {
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
    public void deleteRessource(int id) throws Exception {
        Ressources ressource = ressourcesRepository.findById(id);
        if (ressource != null) {
            ressourcesRepository.delete(ressource);
        }
    }
    public void deleteRessourcebyCreateur(int id_createur){
        try {
            ressourcesRepository.deletebyCreateur(id_createur);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public Ressources validateRessource(int id) throws Exception {
        Ressources ressource = ressourcesRepository.findById(id);
        if (ressource != null) {
            ressource.setValidate_Ressource(true);
            ressourcesRepository.persist(ressource);
            return ressource;
        }
        return null;
    }
    private RessourcesResponce MapperRessourceToRessourceResponse(Ressources ressource) {
        RessourcesResponce responce = new RessourcesResponce();
        responce.setId(ressource.getId_ressource());
        responce.setTitre(ressource.getTitre());
        responce.setDescription(ressource.getDescription());
        responce.setVisibilite(ressource.getVisibilite());
        responce.setDateDeCreation(ressource.getDate_de_creation().toString());
        responce.setNomCreateur(ressource.getCreateur().getNom());
        responce.setPrenomCreateur(ressource.getCreateur().getPrenom());
        responce.setNomCategorie(ressource.getCategorie().getNom_cat());
        responce.setNomType(ressource.getType().getNom_type());
        responce.setNomTag(ressource.getTag().getNom_tag());
        responce.setImage(ressource.getImage());
        return responce;
    }


}
