package com.marrios.gcb.projeto_workshop.resources;

import com.marrios.gcb.projeto_workshop.dto.CategoryDTO;
import com.marrios.gcb.projeto_workshop.entities.Category;
import com.marrios.gcb.projeto_workshop.services.CategoryService;

import  java.util.List;
import  java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController 
@RequestMapping(value = "/categories") //Definindo rota 
public class CategoryResources {
    
    @Autowired
    private CategoryService service;

    // Endpoit -> ResponseEntity encapsula uma resposta HTTP
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll () {
        List<CategoryDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }   
}
