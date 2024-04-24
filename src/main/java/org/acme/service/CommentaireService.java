package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Commentaires;
import org.acme.model.Ressources;
import org.acme.model.Utilisateurs;
import org.acme.repository.CommentaireRepository;
import org.acme.repository.RessourcesRepository;
import org.acme.repository.UtilisateursRepository;
import org.acme.request.CommentaireRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CommentaireService {
    @Inject
    CommentaireRepository commentaireRepository;
    @Inject
    UtilisateursRepository utilisateursRepository;
    @Inject
    RessourcesRepository ressourceRepository;
    public List<Commentaires> listAll() {
        return commentaireRepository.listAll();
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

    public List<Commentaires> getCommentsByRessourceId(int idRessource) {
        List<Commentaires> commentairesOriginaux = commentaireRepository.findByRessourceId(idRessource);
        return commentairesOriginaux;
    }

}
