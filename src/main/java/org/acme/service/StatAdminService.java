package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.controller.TopCreateur;
import org.acme.model.Favoris;
import org.acme.model.Ressources;
import org.acme.model.Utilisateurs;
import org.acme.repository.FavorieRepository;
import org.acme.repository.RessourcesRepository;
import org.acme.response.TopRessourceFavoris;
import org.acme.response.TypeMoisRessource;
import org.acme.response.RessourcesResponce;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
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
        // Fetch the data from your database or data source
        List<RessourcesResponce> listressource = ressourcesService.listAll();

        Map<String, Map<String, Integer>> monthCategoryCount = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        // Populate the monthCategoryCount map with the fetched data
        for (RessourcesResponce ressource : listressource) {
            LocalDateTime dateTime = LocalDateTime.parse(ressource.getDateDeCreation(), formatter);
            String monthInFrench = dateTime.getMonth().getDisplayName(TextStyle.FULL, Locale.FRENCH);
            monthCategoryCount.computeIfAbsent(monthInFrench, k -> new HashMap<>()).merge(ressource.getNomCategorie(), 1, Integer::sum);
        }

        List<TypeMoisRessource> result = new ArrayList<>();

        for (Map.Entry<String, Map<String, Integer>> entry : monthCategoryCount.entrySet()) {
            for (Map.Entry<String, Integer> categoryEntry : entry.getValue().entrySet()) {
                result.add(new TypeMoisRessource(categoryEntry.getKey(), categoryEntry.getValue(), entry.getKey()));
            }
        }

        return result;
    }
    public List<TypeMoisRessource> getNombreDeRessoucesParTagsParMoi() {
        // Fetch the data from your database or data source
        List<RessourcesResponce> listressource = ressourcesService.listAll();

        Map<String, Map<String, Integer>> monthTagCount = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        // Populate the monthTagCount map with the fetched data
        for (RessourcesResponce ressource : listressource) {
            LocalDateTime dateTime = LocalDateTime.parse(ressource.getDateDeCreation(), formatter);
            String monthInFrench = dateTime.getMonth().getDisplayName(TextStyle.FULL, Locale.FRENCH);
            monthTagCount.computeIfAbsent(monthInFrench, k -> new HashMap<>()).merge(ressource.getNomTag(), 1, Integer::sum);
        }

        List<TypeMoisRessource> result = new ArrayList<>();

        for (Map.Entry<String, Map<String, Integer>> entry : monthTagCount.entrySet()) {
            for (Map.Entry<String, Integer> tagEntry : entry.getValue().entrySet()) {
                result.add(new TypeMoisRessource(tagEntry.getKey(), tagEntry.getValue(), entry.getKey()));
            }
        }

        return result;
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
    public List<RessourcesResponce> getTopFastestResources() {
        List<RessourcesResponce> listressource = ressourcesRepository.listAll().stream()
                .map(Ressources::mapperRessourceToRessourceResponse)
                .collect(Collectors.toList());
        listressource.sort((a, b) -> b.getVue() - a.getVue());

        return listressource.stream()
                .limit(3)
                .collect(Collectors.toList());
    }
}

