package com.marrios.gcb.projeto_workshop.repositories;

import java.util.Optional;

import com.marrios.gcb.projeto_workshop.entities.Product;
import com.marrios.gcb.projeto_workshop.tests.Factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

@DataJpaTest
public class ProductRepositoryTests {
    
    private Long exintingId;
    private Long nonExistingId;
    private Long countTotalProducts;

    @Autowired
    private ProductRepository repository;
    
    @BeforeEach
    void setUp() {
        exintingId          = 1L;
        nonExistingId       = 1000L;
        countTotalProducts  = 25L;
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        repository.deleteById(exintingId);
        Optional<Product> result = repository.findById(exintingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });
    } 

    @Test
    public void saveShouldPersistWithAutoicrementWhenIdIsNull() {
        Product product = Factory.createProduct();
        product.setId(null);
        product = repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }

    @Test
    public void findShouldReturnNotNullIfIdExists() {
        Optional<Product> result = repository.findById(exintingId);
        Assertions.assertNotNull(result);
    }

    @Test
    public void findShouldReturnNullIfIdNotExists() {
        Optional<Product> result = repository.findById(nonExistingId);
        Assertions.assertTrue(result.isEmpty());
    }
}
