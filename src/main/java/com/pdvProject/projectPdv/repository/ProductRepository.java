package com.pdvProject.projectPdv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdvProject.projectPdv.models.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{

    Optional<Product> findByCode(String productCode);
    boolean existsByCode(String productCode);
    List<Product> findByNameContainingIgnoreCase(String name);

}
