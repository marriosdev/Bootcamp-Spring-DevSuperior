package com.marrios.gcb.projeto_workshop.services;

import javax.transaction.Transactional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.marrios.gcb.projeto_workshop.dto.ProductDTO;
import com.marrios.gcb.projeto_workshop.repositories.ProductRepository;
import com.marrios.gcb.projeto_workshop.services.exceptions.ResourceEntityNotFoundException;

@SpringBootTest
@Transactional
public class ProductServicesIT {
    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository repository;

    private Long nonExistsId;
    private Long existsId;
    private Long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception {
        nonExistsId = 1000L;
        existsId = 1L;
        countTotalProducts = 25L;
    }

    @Test
    public void deleShouldDeleteResourceWhenIdExists() {
        service.delete(existsId);
        Assertions.assertEquals(countTotalProducts - 1, repository.count());
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceEntityNotFoundException.class, () -> 
            service.delete(nonExistsId)
        );
    }

    @Test
    public void findAllPagedShouldReturnPageWhenPage0Size10() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        
        Page<ProductDTO> result =  service.findAllPaged(pageRequest);
        
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(0, result.getNumber());
        Assertions.assertEquals(10, result.getSize());
        Assertions.assertEquals(countTotalProducts, result.getTotalElements());
    }

    @Test
    public void findAllPagedShouldReturnEmptyPageWhenPageDowNotExist() {
        PageRequest pageRequest = PageRequest.of(50, 10);
        
        Page<ProductDTO> result =  service.findAllPaged(pageRequest);
        
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void findAllPagedShouldReturnOrderedPageWhenSortByName() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));
        
        Page<ProductDTO> result =  service.findAllPaged(pageRequest);
        
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Macbook Pro", result.getContent().get(0).getName());
        Assertions.assertEquals("PC Gamer", result.getContent().get(1).getName());
        Assertions.assertEquals("PC Gamer Alfa", result.getContent().get(2).getName());

    }
}
