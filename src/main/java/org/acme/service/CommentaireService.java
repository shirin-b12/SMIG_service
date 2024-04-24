package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Commentaires;
import org.acme.model.Utilisateurs;
import org.acme.response.CommentaireResponce;
import org.acme.repository.CommentaireRepository;
import org.acme.repository.RessourcesRepository;
import org.acme.repository.UtilisateursRepository;
import org.acme.request.CommentaireRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    public List<CommentaireResponce> getCommentsByRessourceId(int idRessource) {

        List<Commentaires> commentairesOriginaux = commentaireRepository.findByRessourceId(idRessource);
        List<CommentaireResponce> commentairesModifies = new ArrayList<>();
        try {
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

            CommentaireResponce reponce = new CommentaireResponce();
            reponce.setId_commentaire(commentaireModifie.getId_commentaire());
            reponce.setCommentaire(commentaireModifie.getCommentaire());
            reponce.setDate_de_creation(commentaireModifie.getDate_de_creation());
            reponce.setId_ressource(commentaireModifie.getId_ressource().getId_ressource());
            reponce.setId_utilisateur_redacteur(commentaireModifie.getId_utilisateur_redacteur().getId_utilisateur());
            CommentaireResponce reponceRep = getCommentaireReponce(commentaireModifie);
            reponce.setId_commentaire_rep(reponceRep);

            commentairesModifies.add(reponce);
        }
        } catch (Exception e) {
            LOGGER.severe("Une exception s'est produite lors du mapping des commentaires: " + e.getMessage());
        }

        return commentairesModifies;
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
