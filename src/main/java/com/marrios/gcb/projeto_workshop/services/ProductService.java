package com.marrios.gcb.projeto_workshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.marrios.gcb.projeto_workshop.dto.CategoryDTO;
import com.marrios.gcb.projeto_workshop.dto.ProductDTO;
import com.marrios.gcb.projeto_workshop.entities.Category;
import com.marrios.gcb.projeto_workshop.entities.Product;
import com.marrios.gcb.projeto_workshop.repositories.CategoryRepository;
import com.marrios.gcb.projeto_workshop.repositories.ProductRepository;
import com.marrios.gcb.projeto_workshop.services.exceptions.DatabaseException;
import com.marrios.gcb.projeto_workshop.services.exceptions.ResourceEntityNotFoundException;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

@Service // Isso é um service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        List<Product> list =  repository.findAll();
        return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = repository.findById(id);
        Product entity = obj.orElseThrow(()-> new ResourceEntityNotFoundException("Produto não encontrado"));
        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional(readOnly = true)
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copyDtoToEntity(dto, entity);

        // entity.setName(dto);
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDate(dto.getDate());

        entity.getCategories().clear();
        for (CategoryDTO catDto: dto.getCategories())
        {
            Category category  =  categoryRepository.getById(catDto.getId());
            entity.getCategories().add(category);
        }
    }

    @Transactional(readOnly = true)
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            // copyDtoToEntity(dto, entity);
            Product entity = repository.getById(id);
            entity.setName(dto.getName());
            entity = repository.save(entity);
            
        } catch (EntityNotFoundException e) {
            throw new ResourceEntityNotFoundException("Id not found: "+id);
        }
        return dto;
    }

    public  void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceEntityNotFoundException("Id not found: "+ id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }
    }

    public Page<ProductDTO> findAllPaged(Pageable page) {
        Page<Product> list =  repository.findAll(page);
        return list.map(x -> new ProductDTO(x));
    }
}
