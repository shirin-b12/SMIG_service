package org.acme.service;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.model.TypesRelation;
import org.acme.repository.TypesRelationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class TypesRelationServiceTest {

    @InjectMocks
    TypesRelationService typesRelationService;

    @Mock
    TypesRelationRepository typesRelationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListAll() {
        TypesRelation relation1 = new TypesRelation();
        relation1.setIdTypeRelation(1);
        TypesRelation relation2 = new TypesRelation();
        relation2.setIdTypeRelation(2);

        when(typesRelationRepository.listAll()).thenReturn(Arrays.asList(relation1, relation2));

        List<TypesRelation> result = typesRelationService.listAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(relation1));
        assertTrue(result.contains(relation2));
    }

    @Test
    void testFindById() {
        TypesRelation expectedRelation = new TypesRelation();
        expectedRelation.setIdTypeRelation(1);

        when(typesRelationRepository.findById(1)).thenReturn(expectedRelation);

        TypesRelation result = typesRelationService.findById(1);

        assertNotNull(result);
        assertEquals(expectedRelation.getIdTypeRelation(), result.getIdTypeRelation());

        // Test relation not found scenario
        when(typesRelationRepository.findById(2)).thenReturn(null);

        TypesRelation notFoundResult = typesRelationService.findById(2);

        assertNull(notFoundResult);
    }

    @Test
    void testAddTypesRelation() {
        TypesRelation newRelation = new TypesRelation();
        newRelation.setIdTypeRelation(1);
        newRelation.setIntitule("New Relation");

        doNothing().when(typesRelationRepository).persist(newRelation);

        TypesRelation result = typesRelationService.addTypesRelation(newRelation);

        assertNotNull(result);
        assertEquals(newRelation.getIdTypeRelation(), result.getIdTypeRelation());
        assertEquals(newRelation.getIntitule(), result.getIntitule());
        verify(typesRelationRepository, times(1)).persist(newRelation);
    }

    @Test
    void testUpdateTypesRelation() {
        TypesRelation existingRelation = new TypesRelation();
        existingRelation.setIdTypeRelation(1);
        existingRelation.setIntitule("Old Relation");

        TypesRelation updates = new TypesRelation();
        updates.setIntitule("Updated Relation");

        when(typesRelationRepository.findById(1)).thenReturn(existingRelation);

        TypesRelation result = typesRelationService.updateTypesRelation(1, updates);

        assertNotNull(result);
        assertEquals(updates.getIntitule(), result.getIntitule());
        verify(typesRelationRepository, times(1)).persist(existingRelation);

        // Test relation not found scenario
        when(typesRelationRepository.findById(2)).thenReturn(null);

        TypesRelation notFoundResult = typesRelationService.updateTypesRelation(2, updates);

        assertNull(notFoundResult);
    }

   /* @Test
    void testDeleteTypesRelation() {
        TypesRelation relation = new TypesRelation();
        relation.setIdTypeRelation(1);

        when(typesRelationRepository.findById(1)).thenReturn(relation);
        doNothing().when(typesRelationRepository).delete(relation);

        typesRelationService.deleteTypesRelation(1);

        verify(typesRelationRepository, times(1)).delete(relation);

        // Test relation not found scenario
        when(typesRelationRepository.findById(2)).thenReturn(null);

        typesRelationService.deleteTypesRelation(2);

        verify(typesRelationRepository, times(0)).delete(any(TypesRelation.class));
    }*/
}

