package com.ecommerce.Ecommerce.service;

import com.ecommerce.Ecommerce.entity.Product;
import com.ecommerce.Ecommerce.exception.ProductNotFoundException;
import com.ecommerce.Ecommerce.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private String FAKESTORE_URL = "https://fakestoreapi.com/products";
    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;

    public List<Product> fetchandSaveProduct(){
        // Check if data is present in the database
        long count = productRepository.count();

        // If no data is present, fetch from the API
        if (count == 0) {
            Product[] products = restTemplate.getForObject(FAKESTORE_URL, Product[].class);

            // Save products to the database
            if (products != null && products.length > 0) {
                // Use saveAll for batch saving
                List<Product> productList = Arrays.asList(products);
                productRepository.saveAll(productList);
                return productList;
            }
        }

        // If data is present, return the existing product list
        return productRepository.findAll();
    }

    public Page<Product> fetchandSaveProductUsingPagination(Integer pageNumber, Integer pageSize){
        // Check if data is present in the database
        long count = productRepository.count();

        // If no data is present, fetch from the API
        if (count == 0) {
            Product[] products = restTemplate.getForObject(FAKESTORE_URL, Product[].class);

            // Save products to the database
            if (products != null && products.length > 0) {
                // Use saveAll for batch saving
                List<Product> productList = Arrays.asList(products);
                productRepository.saveAll(productList);
            }
        }
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage;
    }

    public Product findProductById(int id){
        return productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("Product with id " + id + " not found"));
    }

    public List<Product> findProductByCategory(String category){
        List<Product> productsByCategory = productRepository.findByCategory(category);
        if (productsByCategory.isEmpty()) {
            throw new ProductNotFoundException("Products of this category not found");
        }
        return productsByCategory;
    }

}
