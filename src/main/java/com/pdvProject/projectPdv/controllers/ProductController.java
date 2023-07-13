package com.pdvProject.projectPdv.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdvProject.projectPdv.payload.requests.ProductRequest;
import com.pdvProject.projectPdv.services.ProductService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge=3600)
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    
    @Autowired
    private ProductService _productService;

    @PostMapping("/addProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest request){
        return _productService.createNewProduct(request);
    }

    @GetMapping("/search/getAll")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getAllProduct(){
        return _productService.getAllProducts();
    }

    @GetMapping("/search/byCode")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getProductByCode(@RequestBody Map<String, String> requestBody) {
        String productCode = requestBody.get("productCode");
        return _productService.getProductByProductCode(productCode);
    }

    @GetMapping("/search/productName")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> searchProducts(@RequestBody Map<String, String> requestBody){
        String search = requestBody.get("name");
        return _productService.getProductListByName(search);
    }

    @PutMapping("/update/{productId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody ProductRequest request){
        return _productService.updateProductById(productId, request);
    }

    @PatchMapping("/deleteProduct/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId){
        return _productService.softDeleteProduct(productId);
    }
}
