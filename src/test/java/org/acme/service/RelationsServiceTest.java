package org.acme.service;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.model.Relations;
import org.acme.model.TypesRelation;
import org.acme.model.Utilisateurs;
import org.acme.repository.RelationRepository;
import org.acme.repository.TypesRelationRepository;
import org.acme.repository.UtilisateursRepository;
import org.acme.request.RelationRequest;
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
class RelationServiceTest {

    @InjectMocks
    RelationService relationService;

    @Mock
    RelationRepository relationRepository;

    @Mock
    UtilisateursRepository utilisateursRepository;

    @Mock
    TypesRelationRepository typesRelationRepository;

    private Utilisateurs user1;
    private Utilisateurs user2;
    private TypesRelation typesRelation;
    private Relations relation1;
    private Relations relation2;
    private RelationRequest relationRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = new Utilisateurs();
        user1.setId_utilisateur(1);

        user2 = new Utilisateurs();
        user2.setId_utilisateur(2);

        typesRelation = new TypesRelation();
        typesRelation.setIdTypeRelation(1);

        relation1 = new Relations();
        relation1.setIdRelation(1);
        relation1.setTypeRelation(typesRelation);
        relation1.setUtilisateur1(user1);
        relation1.setUtilisateur2(user2);

        relation2 = new Relations();
        relation2.setIdRelation(2);
        relation2.setTypeRelation(typesRelation);
        relation2.setUtilisateur1(user1);
        relation2.setUtilisateur2(user2);

        relationRequest = new RelationRequest(user1.getId_utilisateur(), user2.getId_utilisateur(), typesRelation.getIdTypeRelation());
    }

    @Test
    void testListAll() {
        when(relationRepository.listAll()).thenReturn(Arrays.asList(relation1, relation2));

        List<Relations> result = relationService.listAll();

        assertEquals(2, result.size());
    }

    @Test
    void testFindById() {
        when(relationRepository.findById(1)).thenReturn(relation1);

        Relations result = relationService.findById(1);

        assertNotNull(result);
        assertEquals(relation1.getIdRelation(), result.getIdRelation());

        // Test relation not found scenario
        when(relationRepository.findById(2)).thenReturn(null);

        Relations notFoundResult = relationService.findById(2);

        assertNull(notFoundResult);
    }

    @Test
    void testAddRelation() {
        when(utilisateursRepository.findById(1)).thenReturn(user1);
        when(utilisateursRepository.findById(2)).thenReturn(user2);
        when(typesRelationRepository.findById(1)).thenReturn(typesRelation);

        Relations result = relationService.addRelation(relationRequest);

        assertNotNull(result);
        assertEquals(user1, result.getUtilisateur1());
        assertEquals(user2, result.getUtilisateur2());
        assertEquals(typesRelation, result.getTypeRelation());
        verify(relationRepository, times(1)).persist(any(Relations.class));
    }

    @Test
    void testUpdateRelation() {
        when(relationRepository.findById(1)).thenReturn(relation1);

        Relations updates = new Relations();
        updates.setTypeRelation(typesRelation);
        updates.setUtilisateur1(user2); // changing the user1 to user2 for update
        updates.setUtilisateur2(user1); // changing the user2 to user1 for update

        Relations result = relationService.updateRelation(1, updates);

        assertNotNull(result);
        assertEquals(updates.getUtilisateur1(), result.getUtilisateur1());
        assertEquals(updates.getUtilisateur2(), result.getUtilisateur2());
        verify(relationRepository, times(1)).persist(relation1);

        // Test relation not found scenario
        when(relationRepository.findById(2)).thenReturn(null);

        Relations notFoundResult = relationService.updateRelation(2, updates);

        assertNull(notFoundResult);
    }

    @Test
    void testDeleteRelation() {
        when(relationRepository.findById(1)).thenReturn(relation1);
        doNothing().when(relationRepository).delete(relation1);

        relationService.deleteRelation(1);

        verify(relationRepository, times(1)).delete(relation1);
    }

    @Test
    void testGetRelationsByUserId() {
        when(relationRepository.findRelationsByUserId(1)).thenReturn(Arrays.asList(relation1, relation2));

        List<Relations> result = relationService.getRelationsByUserId(1);

        assertEquals(2, result.size());
    }

    @Test
    void testCheckExistingRelation() {
        when(relationRepository.existsRelationBetweenUsers(1, 2)).thenReturn(true);

        boolean result = relationService.checkExistingRelation(1, 2);

        assertTrue(result);

        when(relationRepository.existsRelationBetweenUsers(1, 3)).thenReturn(false);

        boolean resultFalse = relationService.checkExistingRelation(1, 3);

        assertFalse(resultFalse);
    }
}
