package com.pdvProject.projectPdv.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="item")
public class CartItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private ShoppingCart cart;

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private Integer amounts;
    
    @Column(nullable = false)
    private Double price;

    public CartItem() {
    }

    public CartItem(ShoppingCart cart, Product product, int amounts) {       
        this.setCart(cart);
        this.setProduct(product);
        this.setAmounts(amounts);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;

        if(product != null && this.price == null){
            this.setPrice(product.getPrice());
        }
    }

    public int getAmounts() {
        return amounts;
    }

    public void setAmounts(int amounts) {
        this.amounts = amounts;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    

    
}
