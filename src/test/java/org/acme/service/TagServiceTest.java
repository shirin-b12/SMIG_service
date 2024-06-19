package org.acme.service;

import org.acme.model.Tag;
import org.acme.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TagServiceTest {

    private TagService tagService;
    private TagRepository tagRepository;

    @BeforeEach
    public void setUp() {
        tagRepository = mock(TagRepository.class);
        tagService = new TagService();
    }

    @Test
    public void testGetTags() {
        // Préparer les données simulées
        Tag tag = new Tag();
        when(tagRepository.listAll()).thenReturn(Collections.singletonList(tag));

        // Appeler la méthode à tester
        List<Tag> tags = tagService.listAll();

        // Vérifier le résultat
        assertNotNull(tags);
        assertEquals(1, tags.size());
        assertEquals("tag", tags.get(0).getNom_tag());
    }

    @Test
    public void testCreateTag() {
        // Préparer les données d'entrée
        Tag tag = new Tag("tag");
        when(tagRepository.createTag(tag(Tag.class))).thenReturn(tag);

        // Appeler la méthode à tester
        Tag createdTag = tagService.createTag(tag);

        // Vérifier le résultat
        assertNotNull(createdTag);
        assertEquals("tag", createdTag.getNom_tag());
        verify(tagRepository, times(1)).createTag(tag(Tag.class));
    }

    @Test
    public void testCreateTagFailed() {
        // Préparer les données d'entrée invalides
        Tag tag = new Tag(""); // Nom de tag invalide

        // Appeler la méthode à tester et vérifier qu'une exception est levée
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.createTag(tag);
        });

        // Vérifier qu'aucune interaction avec le dépôt n'a eu lieu
        verify(tagRepository, never()).findById(tag(Tag.class));
    }
}
