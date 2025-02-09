package com.example.CrudRestApi.repository;


import com.example.CrudRestApi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}