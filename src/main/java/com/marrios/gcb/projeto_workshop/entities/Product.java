package com.marrios.gcb.projeto_workshop.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_product")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition =  "TIMESTAMP WITHOUT TIME ZONE")
    private Instant created_at;
    
    @Column(columnDefinition =  "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updated_at;
    
    private String name;
    
    @Column(columnDefinition="TEXT")
    private String description;
    private Double price;
    private String imgUrl;
    private Instant date;

    // conjunto n aceita  repetições
    @ManyToMany
    @JoinTable(name = "tb_product_category",
        // joinColumn pega direto da propria classe: Product
        // inverseJoinColumns pega da classe setada na coleção: Category
        joinColumns = @JoinColumn(name = "product_id"), 
        inverseJoinColumns = @JoinColumn(name = "category_id")
        )  
    Set<Category> categories = new HashSet<>();

    public Product()
    {

    }

    public Product(Long id, String name, String description, Double price, String imgUrl, Instant date,
        Set<Category> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Category> getCategories() {
        return this.categories;
    }
}
