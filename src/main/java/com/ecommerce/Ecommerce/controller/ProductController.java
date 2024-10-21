package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.entity.Product;
import com.ecommerce.Ecommerce.repo.ProductRepository;
import com.ecommerce.Ecommerce.service.ProductService;
import com.ecommerce.Ecommerce.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @GetMapping("products")
        public ResponseEntity<ApiResponse<List<Product>>> getAllProducts(){
        List<Product> products = productService.fetchandSaveProduct();
        ApiResponse<List<Product>> response = new ApiResponse<>("success", products);
        return new ResponseEntity<>(response, HttpStatus.OK);
        }

    @GetMapping("products/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable int id){
        Product product = productService.findProductById(id);
        return ResponseEntity.ok(new ApiResponse<>("success",product));
    }

    @GetMapping("products/category/{category}")
    public ResponseEntity<ApiResponse<List<Product>>> getProductsByCategory(@PathVariable String category){
        List<Product> productsByCategory = productService.findProductByCategory(category);
        return ResponseEntity.ok(new ApiResponse<List<Product>>("success",productsByCategory));
    }
}
