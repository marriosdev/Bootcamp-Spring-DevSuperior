package com.marrios.gcb.projeto_workshop.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.marrios.gcb.projeto_workshop.entities.Category;
import com.marrios.gcb.projeto_workshop.entities.Product;

public class ProductDTO implements Serializable {
    private Long id;
    private Instant created_at;
    private Instant updated_at;
    private String name;
    
    private String description;
    private Double price;
    private String imgUrl;
    private Instant date;
    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO(Product entity) {
        this.id            = entity.getId();
        this.name          = entity.getName();
        this.description   = entity.getDescription();
        this.price         = entity.getPrice();
        this.imgUrl        = entity.getImgUrl();
        this.date          = entity.getDate();
    }
    
    public ProductDTO(Long id, Instant created_at, Instant updated_at, String name, String description, Double price,
            String imgUrl, Instant date, List<CategoryDTO> categories) {
        this.id             = id;
        this.created_at     = created_at;
        this.updated_at     = updated_at;
        this.name           = name;
        this.description    = description;
        this.price          = price;
        this.imgUrl         = imgUrl;
        this.date           = date;
    }

    public ProductDTO() {
    }

    public ProductDTO(Product entity, Set<Category> categories) {
        this(entity);
        categories.forEach(cat -> this.categories.add(new CategoryDTO(cat)));;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public Instant getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Instant updated_at) {
        this.updated_at = updated_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
    

    public List<CategoryDTO> getCategories() {
        return categories;
    }    
}
