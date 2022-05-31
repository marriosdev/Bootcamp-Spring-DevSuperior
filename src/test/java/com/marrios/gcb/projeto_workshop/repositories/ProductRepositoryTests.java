package com.marrios.gcb.projeto_workshop.repositories;

import java.util.Optional;

import com.marrios.gcb.projeto_workshop.entities.Product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository repository;
    
    @Test
    public void deleteShouldDeleteObjectWhenIdExists()
    {
        Long idProduct = 1L;
        repository.deleteById(idProduct);

        Optional<Product> result = repository.findById(idProduct);

        Assertions.assertFalse(result.isPresent());
    }
}
