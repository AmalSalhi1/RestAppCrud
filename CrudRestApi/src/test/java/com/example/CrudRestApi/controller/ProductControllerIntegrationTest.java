package com.example.CrudRestApi.controller;


import com.example.CrudRestApi.dto.ProductDTO;
import com.example.CrudRestApi.model.Product;
import com.example.CrudRestApi.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    private static Long productId;

    @BeforeEach
    public void setUp() {
        productRepository.deleteAll(); // Nettoyer la base avant chaque test
        Product product = new Product(null, "Test Product", 99.99);
        Product savedProduct = productRepository.save(product);
        productId = savedProduct.getId();
    }


    @Test
    @Order(1)
    public void testGetAllProducts() {
        ResponseEntity<Product[]> response = restTemplate.getForEntity("/api/products", Product[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }


    @Test
    @Order(2)
    public void testGetProductByIdFound() {
        ResponseEntity<Product> response = restTemplate.getForEntity("/api/products/" + productId, Product.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Product", response.getBody().getName());
    }


    @Test
    @Order(3)
    public void testGetProductByIdNotFound() {
        ResponseEntity<Product> response = restTemplate.getForEntity("/api/products/9999", Product.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    @Order(4)
    public void testCreateProduct() {
        ProductDTO newProduct = new ProductDTO("New Product", 150.50);
        ResponseEntity<Product> response = restTemplate.postForEntity("/api/products", newProduct, Product.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("New Product", response.getBody().getName());
    }


    @Test
    @Order(5)
    public void testUpdateProduct() {
        ProductDTO updatedProduct = new ProductDTO("Updated Product", 199.99);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductDTO> requestEntity = new HttpEntity<>(updatedProduct, headers);

        ResponseEntity<Product> response = restTemplate.exchange("/api/products/" + productId, HttpMethod.PUT, requestEntity, Product.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Product", response.getBody().getName());
    }


    @Test
    @Order(6)
    public void testDeleteProduct() {
        ResponseEntity<Void> response = restTemplate.exchange("/api/products/" + productId, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        ResponseEntity<Product> deletedProductResponse = restTemplate.getForEntity("/api/products/" + productId, Product.class);
        assertEquals(HttpStatus.NOT_FOUND, deletedProductResponse.getStatusCode());
    }
}
