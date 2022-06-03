package com.marrios.gcb.projeto_workshop.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.marrios.gcb.projeto_workshop.entities.Product;
import com.marrios.gcb.projeto_workshop.repositories.ProductRepository;
import com.marrios.gcb.projeto_workshop.services.exceptions.DatabaseException;
import com.marrios.gcb.projeto_workshop.services.exceptions.ResourceEntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
    
    // Não usamos o autowired, neste caso de teste usamos mockito
    @InjectMocks 
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        dependentId = 4L;


        Mockito.doNothing().when(repository).deleteById(nonExistingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenIdDependentId() {
        Assertions.assertThrows(DatabaseException.class, () -> 
            service.delete(dependentId)
        );
    }
    
    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdNotExists() {
        Assertions.assertThrows(ResourceEntityNotFoundException.class, () -> 
            service.delete(nonExistingId)
        );
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> service.delete(existingId));

        Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
    }

}
