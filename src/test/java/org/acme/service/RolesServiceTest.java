package org.acme.service;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.model.Roles;
import org.acme.repository.RolesRepository;
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
class RolesServiceTest {

    @InjectMocks
    RolesService rolesService;

    @Mock
    RolesRepository rolesRepository;

    private Roles role1;
    private Roles role2;
    private Roles newRole;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        role1 = new Roles();
        role1.setId_role(1);
        role1.setNom_role("Admin");

        role2 = new Roles();
        role2.setId_role(2);
        role2.setNom_role("User");

        newRole = new Roles();
        newRole.setId_role(3);
        newRole.setNom_role("New Role Test");
    }

    @Test
    void testListAll() {
        when(rolesRepository.listAll()).thenReturn(Arrays.asList(role1, role2));

        List<Roles> result = rolesService.listAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(role1));
        assertTrue(result.contains(role2));
    }

    @Test
    void testCreateRole() {
        doNothing().when(rolesRepository).persist(newRole);

        Roles result = rolesService.createRole(newRole);

        assertNotNull(result);
        assertEquals(newRole.getId_role(), result.getId_role());
        assertEquals(newRole.getNom_role(), result.getNom_role());
        verify(rolesRepository, times(1)).persist(newRole);
    }
}