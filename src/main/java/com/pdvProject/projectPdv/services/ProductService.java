package com.pdvProject.projectPdv.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pdvProject.projectPdv.Utils.ProductCode;
import com.pdvProject.projectPdv.models.Product;
import com.pdvProject.projectPdv.payload.requests.ProductRequest;
import com.pdvProject.projectPdv.payload.responses.MessageResponse;
import com.pdvProject.projectPdv.repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository _productRepository;

    private String genCode(int maxAttempts){
        
        if (maxAttempts <= 0) {
            return "-1";
        }
        
        String code = ProductCode.generateCode();

        if(_productRepository.existsByCode(code)){
            return genCode(maxAttempts - 1);    
        }else{
            return code;
        }
    }

    public ResponseEntity<?> createProducts(List<ProductRequest> requestList){
        List<Product> productList = new ArrayList<>();
        for (ProductRequest request : requestList) {
            String productCode = genCode(100);
            if(productCode == "-1"){
                return ResponseEntity.internalServerError().body(new MessageResponse("Error: Número de tentativas de geração de código do produto foram superadas."));
            }

            Product product = new Product(request.getName(), 
                                request.getDescription(), 
                                request.getType(), 
                                request.getPrice(), 
                                productCode, 
                                request.getStock());

            productList.add(product);

        }

        _productRepository.saveAll(productList);
        return ResponseEntity.ok(new MessageResponse("Produtos cadastrados!"));
    }

    public ResponseEntity<?> getAllProducts(){
        List<Product> products = _productRepository.findAll();
        List<Product> productsActive = new ArrayList<>();
        for (Product product : products) {
            if(product.getIsActive() == true){
                productsActive.add(product);
            }
        }
        return ResponseEntity.ok(productsActive);
    }

    public ResponseEntity<?> getProductByProductCode(String productCode){
        
        if(_productRepository.existsByCode(productCode)){
            if(_productRepository.findByCode(productCode).get().getIsActive() == false){
                ResponseEntity.badRequest().body(new MessageResponse("Error: Produto de código " + productCode + " não faz parte do nosso banco!"));
            }
            return ResponseEntity.ok(_productRepository.findByCode(productCode));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Error: Produto de código " + productCode + " não encontrado"));
    }
    
    public ResponseEntity<?> getProductListByName(String requestName){
        
        List<Product> productList = _productRepository.findByNameContainingIgnoreCase(requestName);
        if(productList.isEmpty()){
            return ResponseEntity.badRequest().body(new MessageResponse("Não existe produtos com a referência: " + requestName));
        }else{
            return ResponseEntity.ok(productList);
        }
    }

    public ResponseEntity<?> updateProductById(Long productId, ProductRequest request) {
        Product product = _productRepository.findById(productId)
            .orElse(null);
    
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse("Produto não encontrado!"));
        }
    
        if (!Objects.equals(product.getName(), request.getName())) {
            product.setName(request.getName());
        }
        if (!Objects.equals(product.getDescription(), request.getDescription())) {
            product.setDescription(request.getDescription());
        }
        if (!Objects.equals(product.getType(), request.getType())) {
            product.setType(request.getType());
        }
        if (!Objects.equals(product.getPrice(), request.getPrice())) {
            product.setPrice(request.getPrice());
        }
        if (!Objects.equals(product.getStock(), request.getStock())) {
            product.setStock(request.getStock());
        }
    
        product.setUpdateAt(LocalDateTime.now());
    
        _productRepository.save(product);
        return ResponseEntity.ok(product);
    }

    public ResponseEntity<?> softDeleteProduct(Long productId){
        Product product = _productRepository.findById(productId)
            .orElse(null);
    
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse("Produto não encontrado!"));
        }

        product.setIsActive(false);
        product.setDeleteAt(LocalDateTime.now());
        _productRepository.save(product);
        return ResponseEntity.ok(product);
    }
}
