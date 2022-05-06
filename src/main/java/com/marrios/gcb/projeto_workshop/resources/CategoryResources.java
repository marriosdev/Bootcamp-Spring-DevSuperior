package com.marrios.gcb.projeto_workshop.resources;

import com.marrios.gcb.projeto_workshop.dto.CategoryDTO;
import com.marrios.gcb.projeto_workshop.entities.Category;
import com.marrios.gcb.projeto_workshop.services.CategoryService;
import com.marrios.gcb.projeto_workshop.services.exceptions.ResourceEntityNotFoundException;

import  java.util.List;
import java.net.URI;
import  java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


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

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        CategoryDTO dto = service.findById(id);        
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}")
                    .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
