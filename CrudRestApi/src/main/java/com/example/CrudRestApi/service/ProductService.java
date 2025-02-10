package com.example.CrudRestApi.service;


import com.example.CrudRestApi.dto.ProductDTO;
import com.example.CrudRestApi.exception.ResourceNotFoundException;
import com.example.CrudRestApi.model.Product;
import com.example.CrudRestApi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Cacheable("products")
    public Optional<Product> getProductById(Long id) {
        System.out.println("Fetching product from database for ID: " + id);
        return productRepository.findById(id);
    }


    public Product createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        return productRepository.save(product);
    }
    @CacheEvict(value = "products", key = "#id")
    public Product updateProduct(Long id, ProductDTO productDetails) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        return productRepository.save(product);
    }
    
    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}