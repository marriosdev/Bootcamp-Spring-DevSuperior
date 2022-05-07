package com.marrios.gcb.projeto_workshop.repositories;

import com.marrios.gcb.projeto_workshop.entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
