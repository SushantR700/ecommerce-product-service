package com.ecommerce.Ecommerce.repo;

import com.ecommerce.Ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByCategory(String message);
}
