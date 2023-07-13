package com.pdvProject.projectPdv.models;

import java.time.LocalDateTime;

import com.pdvProject.projectPdv.models.enums.EProductType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Campo Obrigatório")
    private String name;
    @NotNull(message = "Campo Obrigatório")
    private EProductType type;
    private String description;
    @NotNull(message = "Campo Obrigatório")
    private Double price;
    @NotEmpty(message = "Campo Obrigatório")
    private String code;
    private Boolean isActive;
    private Integer stock;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;
    
    public Product() {
    }

    public Product(@NotEmpty(message = "Campo Obrigatório") String name, String description,
            @NotNull(message = "Campo Obrigatório") EProductType type,
            @NotNull(message = "Campo Obrigatório") Double price,
            @NotEmpty(message = "Campo Obrigatório") String productCode, Integer stock) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.code = productCode;
        this.stock = stock;
        this.isActive = true;
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
        this.deleteAt = LocalDateTime.now();
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

    public String getProductCode() {
        return code;
    }

    public void setProductCode(String productCode) {
        this.code = productCode;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public LocalDateTime getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(LocalDateTime deleteAt) {
        this.deleteAt = deleteAt;
    }

    public EProductType getType() {
        return type;
    }

    public void setType(EProductType type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    
    
}
