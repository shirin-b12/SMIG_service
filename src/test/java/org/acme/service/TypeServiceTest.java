package org.acme.service;

import org.acme.model.Type;
import org.acme.repository.TypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TypeServiceTest {

    @Mock
    private TypeRepository typeRepository;

    @InjectMocks
    private TypeService typeService;

    @Test
    public void testListAll() {
        Type type = new Type();
        when(typeRepository.listAll()).thenReturn(Collections.singletonList(type));

        List<Type> types = typeService.listAll();

        assertNotNull(types);
        assertFalse(types.isEmpty());
        assertEquals(type, types.get(0));
    }

    @Test
    public void testFindById() {
        Type type = new Type();
        when(typeRepository.findById(anyInt())).thenReturn(type);

        Type foundType = typeService.findById(1);

        assertNotNull(foundType);
        assertEquals(type, foundType);
    }

    @Test
    public void testCreateType() {
        Type type = new Type();
        type.setNom_type("Test Type");

        Type createdType = typeService.createType(type);

        assertNotNull(createdType);
        assertEquals(type, createdType);
        verify(typeRepository, times(1)).persist(any(Type.class));
    }
}
