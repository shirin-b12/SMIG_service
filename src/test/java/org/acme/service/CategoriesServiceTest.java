package org.acme.service;

import org.acme.model.Categories;
import org.acme.repository.CategoriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoriesServiceTest {

    private CategoriesService categoriesService;
    private CategoriesRepository categoriesRepository;

    @BeforeEach
    public void setUp() {
        categoriesRepository = mock(CategoriesRepository.class);
        categoriesService = new CategoriesService(categoriesRepository);
    }

    @Test
    public void testGetCategories() {
        // Préparer les données simulées
        Categories category = new Categories("technologie");
        when(categoriesRepository.listAll()).thenReturn(Collections.singletonList(category));

        // Appeler la méthode à tester
        List<Categories> categories = categoriesService.getId_cat();

        // Vérifier le résultat
        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertEquals("technologie", categories.get(0).getNom_cat());
    }

    @Test
    public void testCreateCategory() {
        // Préparer les données d'entrée
        Categories category = new Categories("technologie");
        when(categoriesRepository.create(any(Categories.class))).thenReturn(category);

        // Appeler la méthode à tester
        Categories createdCategory = categoriesService.createCategory(category);

        // Vérifier le résultat
        assertNotNull(createdCategory);
        assertEquals("technologie", createdCategory.getNomCat());
        verify(categoriesRepository, times(1)).create(any(Category.class));
    }

    @Test
    public void testCreateCategoryFailed() {
        // Préparer les données d'entrée invalides
        Category category = new Category("");

        // Appeler la méthode à tester et vérifier qu'une exception est levée
        assertThrows(IllegalArgumentException.class, () -> {
            categoriesService.createCategory(category);
        });

        // Vérifier qu'aucune interaction avec le dépôt n'a eu lieu
        verify(categoriesRepository, never()).create(any(Category.class));
    }
}
