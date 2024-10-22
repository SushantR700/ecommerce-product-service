package com.ecommerce.Ecommerce;

import com.ecommerce.Ecommerce.controller.ProductController;
import com.ecommerce.Ecommerce.entity.Product;
import com.ecommerce.Ecommerce.entity.Rating;
import com.ecommerce.Ecommerce.service.ProductService;
import com.ecommerce.Ecommerce.repo.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testGetProductById() throws Exception {
        int productId = 1;
        Product product = Product.builder().Id(productId)
                .title("Test Product")
                .price(99.99)
                .description("This is a test product")
                .category("Test Category")
                .image("test_image.png")
                .rating(new Rating(4.5, 100))
                .build();

        // Mock the service's behaviour: when findProductById is called, return the product
        when(productService.findProductById(productId)).thenReturn(product);

        // Act & Assert: Send a GET request to fetch the product
        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(productId))
                .andExpect(jsonPath("$.data.title").value("Test Product"))
                .andExpect(jsonPath("$.data.price").value(99.99))
                .andExpect(jsonPath("$.data.description").value("This is a test product"))
                .andExpect(jsonPath("$.data.category").value("Test Category"))
                .andExpect(jsonPath("$.data.image").value("test_image.png"))
                .andExpect(jsonPath("$.data.rating.rate").value(4.5))
                .andExpect(jsonPath("$.data.rating.count").value(100));

        // Verify that findProductById was called only once
        verify(productService, times(1)).findProductById(productId);
    }

    @Test
    public void testGetProductsByCategory() throws Exception {
        String category = "Electronics";

        // Create some sample products for the "Electronics" category
        Product product1 = Product.builder().Id(1)
                .title("Product 1")
                .price(100.0)
                .description("Description 1")
                .category("Electronics")
                .image("image1.png")
                .rating(new Rating(4.5, 100))
                .build();

        Product product2 = Product.builder().Id(2)
                .title("Product 2")
                .price(200.0)
                .description("Description 2")
                .category("Electronics")
                .image("image2.png")
                .rating(new Rating(4.0, 50))
                .build();

        List<Product> productList = Arrays.asList(product1, product2);

        // Mock the service's behavior: when findProductByCategory is called, return the product list
        when(productService.findProductByCategory(category)).thenReturn(productList);

        // Act & Assert: Send a GET request to fetch products by category
        mockMvc.perform(get("/api/products/category/{category}", category))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))  // Check the size of the list
                .andExpect(jsonPath("$.data[*].title", containsInAnyOrder("Product 1", "Product 2")))
               ;

        verify(productService,times(1)).findProductByCategory(category);
    }

    @Test
    public void testGetAllProducts() throws Exception {
        Product product1 = Product.builder().Id(1)
                .title("Product 1")
                .price(100.0)
                .description("Description 1")
                .category("Electronics")
                .image("image1.png")
                .rating(new Rating(4.5, 100))
                .build();

        Product product2 = Product.builder().Id(2)
                .title("Product 2")
                .price(200.0)
                .description("Description 2")
                .category("Electronics")
                .image("image2.png")
                .rating(new Rating(4.0, 50))
                .build();

        List<Product> productList = Arrays.asList(product1, product2);
        when(productService.fetchandSaveProduct()).thenReturn(productList);

        mockMvc.perform(get("/api/products"))
                .andExpectAll(
                      jsonPath("$.data[*]",hasSize(2)), jsonPath("$.data[*].title",containsInAnyOrder("Product 1","Product 2"))

                );

        verify(productService,times(1)).fetchandSaveProduct();

    }
}
