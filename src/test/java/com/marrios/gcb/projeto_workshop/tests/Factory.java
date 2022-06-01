package com.marrios.gcb.projeto_workshop.tests;

import java.time.Instant;

import com.marrios.gcb.projeto_workshop.dto.ProductDTO;
import com.marrios.gcb.projeto_workshop.entities.Category;
import com.marrios.gcb.projeto_workshop.entities.Product;

public class Factory {
    
    public static Product createProduct() {
        Product product = new Product(1L, "Phone", "Good Phone", 8000.0, "https://img.com/img.png", Instant.parse("2020-08-02T03:00:00Z"));
        product.getCategories().add(new Category(1L, "Electronics"));
        return product;
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }
}
