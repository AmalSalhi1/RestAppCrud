package com.example.CrudRestApi.service;

import com.example.CrudRestApi.dto.ProductDTO;
import com.example.CrudRestApi.exception.ResourceNotFoundException;
import com.example.CrudRestApi.model.Product;
import com.example.CrudRestApi.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testGetAllProducts() {
        List<Product> mockProducts = Arrays.asList(
                new Product(1L, "Laptop", 1200.99),
                new Product(2L, "Phone", 699.99)
        );

        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getName());
        assertEquals("Phone", result.get(1).getName());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testGetProductByIdFound() {
        Product mockProduct = new Product(1L, "Laptop", 1200.99);
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));

        Optional<Product> result = productService.getProductById(1L);

        assertTrue(result.isPresent());
        assertEquals("Laptop", result.get().getName());
        assertEquals(1200.99, result.get().getPrice());

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetProductByIdNotFound() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Product> result = productService.getProductById(2L);

        assertFalse(result.isPresent());

        verify(productRepository, times(1)).findById(2L);
    }

    @Test
    public void testCreateProduct() {
        ProductDTO productDTO = new ProductDTO("Tablet", 499.99);
        Product savedProduct = new Product(1L, "Tablet", 499.99);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        Product result = productService.createProduct(productDTO);

        assertNotNull(result);
        assertEquals("Tablet", result.getName());
        assertEquals(499.99, result.getPrice());

        verify(productRepository, times(1)).save(any(Product.class));
    }


    @Test
    public void testUpdateProduct() {
        Product existingProduct = new Product(1L, "Laptop", 1200.99);
        ProductDTO updatedProductDTO = new ProductDTO("Updated Laptop", 1500.99);
        Product updatedProduct = new Product(1L, "Updated Laptop", 1500.99);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct(1L, updatedProductDTO);

        assertNotNull(result);
        assertEquals("Updated Laptop", result.getName());
        assertEquals(1500.99, result.getPrice());

        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testUpdateProductNotFound() {
        ProductDTO updatedProductDTO = new ProductDTO("Updated Laptop", 1500.99);

        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.updateProduct(2L, updatedProductDTO);
        });

        assertEquals("Product not found", exception.getMessage());

        verify(productRepository, times(1)).findById(2L);
        verify(productRepository, times(0)).save(any(Product.class));
    }

    @Test
    public void testDeleteProduct() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }


    @Test
    public void testDeleteProductNotFound() {
        when(productRepository.existsById(2L)).thenReturn(false);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.deleteProduct(2L);
        });

        assertEquals("Product not found", exception.getMessage());

        verify(productRepository, times(1)).existsById(2L);
        verify(productRepository, times(0)).deleteById(anyLong());
    }
}
