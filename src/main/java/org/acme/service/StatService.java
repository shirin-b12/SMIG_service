package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Categories;
import org.acme.model.Favoris;
import org.acme.model.Ressources;
import org.acme.model.Type;
import org.acme.repository.FavorieRepository;
import org.acme.response.CategorieStatSurRessource;
import org.acme.response.RessourcesResponce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped

public class StatService {
    @Inject
    FavorieService favorieService;
    @Inject
    RessourcesService ressourcesService;
    @Inject
    FavorieRepository favorieRepository;

    public List<Categories> getTopCategories(int id) {

        List<Favoris> favorites = favorieService.listFavorie(id);

        Map<Categories, Integer> categoryCounts = new HashMap<>();
        for (Favoris favorite : favorites) {
            Categories categorie = favorite.getId_ressource().getCategorie();
            categoryCounts.put(categorie, categoryCounts.getOrDefault(categorie, 0) + 1);
        }

        List<Map.Entry<Categories, Integer>> sortedCategories = new ArrayList<>(categoryCounts.entrySet());
        sortedCategories.sort(Map.Entry.comparingByValue());

        List<Categories> topCategories = new ArrayList<>();
        for (int i = 0; i < 3 && i < sortedCategories.size(); i++) {
            topCategories.add(sortedCategories.get(i).getKey());
        }

        return topCategories;
    }
    public List<Type> getTopType(int id) {

        List<Favoris> favorites = favorieService.listFavorie(id);

        Map<Type, Integer> categoryCounts = new HashMap<>();
        for (Favoris favorite : favorites) {
            Type type = favorite.getId_ressource().getType();
            categoryCounts.put(type, categoryCounts.getOrDefault(type, 0) + 1);
        }

        List<Map.Entry<Type, Integer>> sortedCategories = new ArrayList<>(categoryCounts.entrySet());
        sortedCategories.sort(Map.Entry.comparingByValue());

        List<Type> topType = new ArrayList<>();
        for (int i = 0; i < 3 && i < sortedCategories.size(); i++) {
            topType.add(sortedCategories.get(i).getKey());
        }

        return topType;
    }
    public List<RessourcesResponce> getTopViewedResources(int id) {

        List<RessourcesResponce> userResources = ressourcesService.findByCreateurId(id);

        userResources.sort((r1, r2) -> Integer.compare(r2.getVue(), r1.getVue()));

        List<RessourcesResponce> topViewedResources = userResources.stream().limit(3).collect(Collectors.toList());

        return topViewedResources;
    }
    public List <RessourcesResponce> getTopViewedRessourcesInFavoris(int id) {

        List<Favoris> favorites = favorieRepository.all();
        Map<RessourcesResponce, Integer> ressourceCounts = new HashMap<>();

        for (Favoris favorite : favorites) {
            if(favorite.getId_ressource().getCreateur().getId_utilisateur() == id){
                RessourcesResponce ressource = favorite.getId_ressource().mapperRessourceToRessourceResponse();
                ressourceCounts.put(ressource, ressourceCounts.getOrDefault(ressource, 0) + 1);}

        }

        List<Map.Entry<RessourcesResponce, Integer>> sortedRessources = new ArrayList<>(ressourceCounts.entrySet());
        sortedRessources.sort(Map.Entry.comparingByValue());
        List<RessourcesResponce> topRessources = new ArrayList<>();

        for (int i = 0; i < 3 && i < sortedRessources.size(); i++) {
            topRessources.add(sortedRessources.get(i).getKey());
        }
        return topRessources;
    }

    public List<CategorieStatSurRessource> getCategoriesSurRessources(int id) {
        List<RessourcesResponce> listRessource = ressourcesService.findByCreateurId(id);
        Map<String, Integer> categoryCounts = new HashMap<>();
        for (RessourcesResponce ressource : listRessource) {
            String categorieName = ressource.getNomCategorie();
            categoryCounts.put(categorieName, categoryCounts.getOrDefault(categorieName, 0) + 1);
        }

        return new ArrayList<>(categoryCounts.entrySet().stream()
                .map(entry -> new CategorieStatSurRessource(entry.getKey(), entry.getValue(), id))
                .collect(Collectors.toList()));
    }
}
