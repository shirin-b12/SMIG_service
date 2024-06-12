package org.acme.service;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.model.*;
import org.acme.repository.FavorieRepository;
import org.acme.response.CategorieStatSurRessource;
import org.acme.response.RessourcesResponce;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@QuarkusTest
class StatServiceTest {

    @InjectMocks
    StatService statService;

    @Mock
    FavorieService favorieService;

    @Mock
    RessourcesService ressourcesService;

    @Mock
    FavorieRepository favorieRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTopCategories() {
        Favoris favorite1 = mock(Favoris.class);
        Favoris favorite2 = mock(Favoris.class);
        Categories cat1 = new Categories();
        Categories cat2 = new Categories();
        Ressources res1 = mock(Ressources.class);
        Ressources res2 = mock(Ressources.class);

        when(favorite1.getId_ressource()).thenReturn(res1);
        when(favorite2.getId_ressource()).thenReturn(res2);
        when(res1.getCategorie()).thenReturn(cat1);
        when(res2.getCategorie()).thenReturn(cat2);

        when(favorieService.listFavorie(anyInt())).thenReturn(Arrays.asList(favorite1, favorite2));

        List<Categories> result = statService.getTopCategories(1);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetTopType() {
        Favoris favorite1 = mock(Favoris.class);
        Favoris favorite2 = mock(Favoris.class);
        Type type1 = new Type();
        Type type2 = new Type();
        Ressources res1 = mock(Ressources.class);
        Ressources res2 = mock(Ressources.class);

        when(favorite1.getId_ressource()).thenReturn(res1);
        when(favorite2.getId_ressource()).thenReturn(res2);
        when(res1.getType()).thenReturn(type1);
        when(res2.getType()).thenReturn(type2);

        when(favorieService.listFavorie(anyInt())).thenReturn(Arrays.asList(favorite1, favorite2));

        List<Type> result = statService.getTopType(1);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetTopViewedResources() {
        RessourcesResponce res1 = new RessourcesResponce();
        res1.setVue(10);
        RessourcesResponce res2 = new RessourcesResponce();
        res2.setVue(20);

        when(ressourcesService.findByCreateurId(anyInt())).thenReturn(Arrays.asList(res1, res2));

        List<RessourcesResponce> result = statService.getTopViewedResources(1);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(20, result.get(0).getVue());
    }

    @Test
    void testGetTopViewedRessourcesInFavoris() {
        Favoris favorite1 = mock(Favoris.class);
        Favoris favorite2 = mock(Favoris.class);
        Ressources res1 = mock(Ressources.class);
        Ressources res2 = mock(Ressources.class);
        Utilisateurs user = mock(Utilisateurs.class);
        when(user.getId_utilisateur()).thenReturn(1);
        when(res1.getCreateur()).thenReturn(user);
        when(res2.getCreateur()).thenReturn(user);
        RessourcesResponce resResp1 = new RessourcesResponce();
        RessourcesResponce resResp2 = new RessourcesResponce();

        when(favorite1.getId_ressource()).thenReturn(res1);
        when(favorite2.getId_ressource()).thenReturn(res2);
        when(res1.mapperRessourceToRessourceResponse()).thenReturn(resResp1);
        when(res2.mapperRessourceToRessourceResponse()).thenReturn(resResp2);

        when(favorieRepository.all()).thenReturn(Arrays.asList(favorite1, favorite2));

        List<RessourcesResponce> result = statService.getTopViewedRessourcesInFavoris(1);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetCategoriesSurRessources() {
        RessourcesResponce res1 = new RessourcesResponce();
        res1.setNomCategorie("Cat1");
        RessourcesResponce res2 = new RessourcesResponce();
        res2.setNomCategorie("Cat2");

        when(ressourcesService.findByCreateurId(anyInt())).thenReturn(Arrays.asList(res1, res2));

        List<CategorieStatSurRessource> result = statService.getCategoriesSurRessources(1);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Cat1", result.get(1).getNom());
        assertEquals("Cat2", result.get(0).getNom());
    }
}
