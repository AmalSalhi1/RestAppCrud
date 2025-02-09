package com.example.CrudRestApi.dto;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private double price;
}