package com.marrios.gcb.projeto_workshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.marrios.gcb.projeto_workshop.entities.Category;
import com.marrios.gcb.projeto_workshop.repositories.CategoryRepository;

import java.util.List;

@Service // Isso é um service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> findAll()
    {
        return repository.findAll();
    }
}
