package com.example.gccoffee.model;


import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
public class Product {
    //private final UUID productId;
    private final String productId;
    private String productName;
    private Category category;
    private long price;

    private String description;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    public Product(String productId, String productName, Category category, long price) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.createdAt = LocalDateTime.now().withNano(0);
        this.updatedAt = LocalDateTime.now().withNano(0);
    }


    public Product(String productId, String productName, Category category, long price, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setPrice(long price) {
        this.price = price;
        this.updatedAt = LocalDateTime.now().withNano(0);
    }

    public void setCategory(Category category) {
        this.category = category;
        this.updatedAt = LocalDateTime.now().withNano(0);
    }

    public void setProductName(String productName) {
        this.productName = productName;
        this.updatedAt = LocalDateTime.now().withNano(0);
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now().withNano(0);
    }
}
