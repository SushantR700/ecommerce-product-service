package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.entity.Product;
import com.ecommerce.Ecommerce.repo.ProductRepository;
import com.ecommerce.Ecommerce.service.ProductService;
import com.ecommerce.Ecommerce.utils.ApiResponse;
import com.ecommerce.Ecommerce.utils.AppConstants;
import com.ecommerce.Ecommerce.utils.ProductPaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("productspaginated")
    public  ResponseEntity<ApiResponse<ProductPaginationResponse>> getAllProductsWithPagination(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize
    ){
        Page<Product> productPage = productService.fetchandSaveProductUsingPagination(pageNumber,pageSize);
        List<Product> products = productPage.getContent();
        return ResponseEntity.ok(new ApiResponse<>("success", new ProductPaginationResponse(products,pageNumber,pageSize,productPage.getTotalElements(), productPage.getTotalPages(), productPage.isLast())));
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
