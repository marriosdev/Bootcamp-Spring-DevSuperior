package com.marrios.gcb.projeto_workshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marrios.gcb.projeto_workshop.dto.CategoryDTO;
import com.marrios.gcb.projeto_workshop.entities.Category;
import com.marrios.gcb.projeto_workshop.repositories.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service // Isso é um service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional
    public List<CategoryDTO> findAll()
    {
        List<Category> list =  repository.findAll();
        return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());

    }
}
