package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Commentaires;
import org.acme.model.Utilisateurs;
import org.acme.repository.CommentaireRepository;
import org.acme.repository.RessourcesRepository;
import org.acme.repository.UtilisateursRepository;
import org.acme.request.CommentaireRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
        commentaire.setId_utilisateur_redacteur(utilisateursRepository.findById(commentaires.getIdCreateur()));
        LocalDateTime dateDeCreation = commentaires.getDateDeCreation();
        if (dateDeCreation == null) {
            dateDeCreation = LocalDateTime.now();
        }
        commentaire.setDate_de_creation(dateDeCreation);
        commentaire.setId_ressource(ressourceRepository.findById(commentaires.getIdRessource()));
        if (commentaires.getIdCommentaireRep() != 0) {
            commentaire.setId_commentaire_rep(commentaireRepository.findById(commentaires.getIdCommentaireRep()));
        }
        commentaireRepository.persist(commentaire);
        return commentaire;
    }

    public List<Commentaires> getCommentsByRessourceId(int idRessource) {
        List<Commentaires> commentairesOriginaux = commentaireRepository.findByRessourceId(idRessource);
        List<Commentaires> commentairesModifies = new ArrayList<>();

        for (Commentaires commentaireOriginal : commentairesOriginaux) {
            Commentaires commentaireModifie = new Commentaires();
            commentaireModifie.setId_commentaire(commentaireOriginal.getId_commentaire());
            commentaireModifie.setCommentaire(commentaireOriginal.getCommentaire());
            commentaireModifie.setDate_de_creation(commentaireOriginal.getDate_de_creation());
            commentaireModifie.setId_ressource(commentaireOriginal.getId_ressource());
            commentaireModifie.setId_commentaire_rep(commentaireOriginal.getId_commentaire_rep());

            // Set temporary redacteur
            Utilisateurs redacteurTemporaire = utilisateursRepository.findById(5); // Supposons que 5 est l'ID de l'utilisateur temporaire
            commentaireModifie.setId_utilisateur_redacteur(redacteurTemporaire);

            commentairesModifies.add(commentaireModifie);
        }

        return commentairesModifies;
    }

}
