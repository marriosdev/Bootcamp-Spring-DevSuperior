package com.marrios.gcb.projeto_workshop.services;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jayway.jsonpath.Option;
import com.marrios.gcb.projeto_workshop.dto.ProductDTO;
import com.marrios.gcb.projeto_workshop.entities.Product;
import com.marrios.gcb.projeto_workshop.repositories.ProductRepository;
import com.marrios.gcb.projeto_workshop.services.exceptions.DatabaseException;
import com.marrios.gcb.projeto_workshop.services.exceptions.ResourceEntityNotFoundException;
import com.marrios.gcb.projeto_workshop.tests.Factory;

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
    private PageImpl<Product> page;
    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        dependentId = 4L;
        product = Factory.createProduct();
        page = new PageImpl<>(List.of(product));
        productDTO = new ProductDTO();
        
        Mockito.when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page); 

        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Quando nosso metodo retorne um VOID, temos essa padrão de comportamento configurado
        // Primeiro a AÇÃO e depois o cenário 
        Mockito.doNothing().when(repository).deleteById(nonExistingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
    }

    @Test
    public void deveriaRetornarProductDTOquandoIdExistir() {

    }

    @Test 
    public void FindAllPageShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductDTO> result = service.findAllPaged(pageable);

        Assertions.assertNotNull(result);
        Mockito.verify(repository).findAll(pageable);
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
