package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Commentaires;
import org.acme.model.Ressources;
import org.acme.model.Utilisateurs;
import org.acme.response.CommentaireResponce;
import org.acme.repository.CommentaireRepository;
import org.acme.repository.RessourcesRepository;
import org.acme.repository.UtilisateursRepository;
import org.acme.request.CommentaireRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.logging.Logger;

@ApplicationScoped
public class CommentaireService {
    @Inject
    CommentaireRepository commentaireRepository;
    @Inject
    UtilisateursRepository utilisateursRepository;
    @Inject
    RessourcesRepository ressourceRepository;
    private static final Logger LOGGER = Logger.getLogger(CommentaireService.class.getName());
    public List<CommentaireResponce> listAll() {
        List<Commentaires> commentaires = commentaireRepository.listAll();
        List<CommentaireResponce> commentairesModifies = new ArrayList<>();
        for (Commentaires commentaire : commentaires) {
            try {
                CommentaireResponce reponce = new CommentaireResponce();
                reponce.setId_commentaire(commentaire.getId_commentaire());
                reponce.setCommentaire(commentaire.getCommentaire());
                reponce.setDate_de_creation(commentaire.getDate_de_creation());
                reponce.setId_ressource(commentaire.getId_ressource().getId_ressource());
                reponce.setId_utilisateur_redacteur(commentaire.getId_utilisateur_redacteur().getId_utilisateur());
                CommentaireResponce reponceRep = getCommentaireReponce(commentaire);
                reponce.setId_commentaire_rep(reponceRep);
                commentairesModifies.add(reponce);
            } catch (Exception e) {
                LOGGER.severe("Une exception s'est produite lors du mapping des commentaires: " + e.getMessage());
            }
        }
        return commentairesModifies;
    }

    public Commentaires createCommentaire(CommentaireRequest commentaires) {
        Commentaires commentaire = new Commentaires();
        commentaire.setCommentaire(commentaires.getCommentaire());

        Optional<Utilisateurs> foundUser = Optional.ofNullable(utilisateursRepository.findById(commentaires.getIdCreateur()));
        if (!foundUser.isPresent()) {
            throw new RuntimeException("User not found with ID: " + commentaires.getIdCreateur());
        } else {
            commentaire.setId_utilisateur_redacteur(foundUser.get());
        }

        Optional<Ressources> foundResource = Optional.ofNullable(ressourceRepository.findById(commentaires.getIdRessource()));
        if (!foundResource.isPresent()) {
            throw new RuntimeException("Resource not found with ID: " + commentaires.getIdRessource());
        } else {
            commentaire.setId_ressource(foundResource.get());
        }

        LocalDateTime dateDeCreation = commentaires.getDateDeCreation();
        if (dateDeCreation == null) {
            dateDeCreation = LocalDateTime.now();
        }
        commentaire.setDate_de_creation(dateDeCreation);

        if (commentaires.getIdCommentaireRep() != 0) {
            Optional<Commentaires> foundComment = Optional.ofNullable(commentaireRepository.findById(commentaires.getIdCommentaireRep()));
            if (!foundComment.isPresent()) {
                throw new RuntimeException("Reply Comment not found with ID: " + commentaires.getIdCommentaireRep());
            } else {
                commentaire.setId_commentaire_rep(foundComment.get());
            }
        }
        commentaireRepository.persist(commentaire);
        return commentaire;
    }


//TODO:logger
    public List<Commentaires> getCommentsByRessourceId(int idRessource) {
        List<Commentaires> commentairesOriginaux = commentaireRepository.findByRessourceId(idRessource);
        return commentairesOriginaux;
    }

    private static CommentaireResponce getCommentaireReponce(Commentaires commentaireModifie) {
        CommentaireResponce reponceRep = new CommentaireResponce();
        try {
            if (commentaireModifie.getId_commentaire_rep() != null) {
                reponceRep.setId_commentaire(commentaireModifie.getId_commentaire_rep().getId_commentaire());
                reponceRep.setCommentaire(commentaireModifie.getId_commentaire_rep().getCommentaire());
                reponceRep.setDate_de_creation(commentaireModifie.getId_commentaire_rep().getDate_de_creation());
                reponceRep.setId_ressource(commentaireModifie.getId_commentaire_rep().getId_ressource().getId_ressource());
                reponceRep.setId_utilisateur_redacteur(commentaireModifie.getId_commentaire_rep().getId_utilisateur_redacteur().getId_utilisateur());
                return reponceRep;
            }
            return null;
        } catch (Exception e) {
           LOGGER.severe("Une exception s'est produite lors de la recherche du commentaire de reponse: " + e.getMessage());
            return null;
        }
    }

}
