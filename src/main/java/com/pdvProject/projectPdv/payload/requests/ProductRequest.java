package com.pdvProject.projectPdv.payload.requests;

import com.pdvProject.projectPdv.models.enums.EProductType;


public class ProductRequest {
    
    private String name;
    private EProductType type;
    private String description;
    private Double price;
    private Integer stock;

    public EProductType getType() {
        return type;
    }
    public void setType(EProductType type) {
        this.type = type;
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
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }

}
