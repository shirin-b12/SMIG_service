package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.response.TopCreateur;
import org.acme.model.Favoris;
import org.acme.model.Ressources;
import org.acme.model.Utilisateurs;
import org.acme.repository.FavorieRepository;
import org.acme.repository.RessourcesRepository;
import org.acme.response.TopRessourceFavoris;
import org.acme.response.TypeMoisRessource;
import org.acme.response.RessourcesResponce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class StatAdminService {
    @Inject
    RessourcesService ressourcesService;
    @Inject
    FavorieService favorieService;
    @Inject
    FavorieRepository favorieRepository;
    @Inject
    RessourcesRepository ressourcesRepository;

    public List<TypeMoisRessource> getNombreDeRessoucesParCategoriesParMoi() {
        List<RessourcesResponce> listRessource = ressourcesService.listAll();
        Map<String, Integer> categoryCount = new HashMap<>();

        for (RessourcesResponce ressource : listRessource) {
            categoryCount.merge(ressource.getNomCategorie(), 1, Integer::sum);
        }

        return categoryCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .map(entry -> new TypeMoisRessource(entry.getKey(), entry.getValue(), null))
                .collect(Collectors.toList());
    }

    public List<TypeMoisRessource> getNombreDeRessoucesParTagsParMoi() {
        List<RessourcesResponce> listRessource = ressourcesService.listAll();
        Map<String, Integer> tagCount = new HashMap<>();

        for (RessourcesResponce ressource : listRessource) {
            tagCount.merge(ressource.getNomTag(), 1, Integer::sum);
        }

        return tagCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .map(entry -> new TypeMoisRessource(entry.getKey(), entry.getValue(), null))
                .collect(Collectors.toList());
    }


    public List<TopRessourceFavoris> getTopRessourceDansFavoris() {
        // Fetch the data from your database or data source
        List<Favoris> listressource = favorieRepository.all();

        Map<Integer, Integer> ressourceCount = new HashMap<>();

        // Populate the ressourceCount map with the fetched data
        for (Favoris ressource : listressource) {
            ressourceCount.merge(ressource.getId_ressource().getId_ressource(), 1, Integer::sum);
        }

        List<TopRessourceFavoris> result = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : ressourceCount.entrySet()) {
            RessourcesResponce ressource = null;
            try {
                ressource = ressourcesService.findById(entry.getKey());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ressource != null) {
                result.add(new TopRessourceFavoris(ressource.getTitre(), entry.getValue()));
            }
        }

        // Sort the result list in descending order based on the 'nombre' field
        result.sort((a, b) -> b.getNombre() - a.getNombre());

        // Return only the top 3 resources
        return result.size() > 3 ? result.subList(0, 3) : result;
    }
    public List<TopCreateur> getTopCreators() {
        return ressourcesRepository.listAll().stream()
                .collect(Collectors.groupingBy(Ressources::getCreateur, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<Utilisateurs, Long>comparingByValue().reversed())
                .limit(3)
                .map(entry -> new TopCreateur(entry.getKey().getNom(), entry.getValue().intValue()))
                .collect(Collectors.toList());
    }
}

