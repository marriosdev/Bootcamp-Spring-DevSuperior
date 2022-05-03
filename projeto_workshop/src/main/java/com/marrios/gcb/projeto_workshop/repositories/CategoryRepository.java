package com.marrios.gcb.projeto_workshop.repositories;

import com.marrios.gcb.projeto_workshop.entities.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
