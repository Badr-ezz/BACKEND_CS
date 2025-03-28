package com.example.springMongodb.service;

import com.example.springMongodb.model.Product;
import com.example.springMongodb.model.ProductSize;
import com.example.springMongodb.model.Users;

import java.util.List;

public interface ProductService {
    public long countProducts();
    public List<Product> getAllProducts();
    public Product getProductById(String id);
    public Product addProduct(Product prompt);
    public Product updateProduct(Product prompt);
    public void deleteProduct(String id);
    public List<Product> findByProductCategory(List<String> categories);
    public List<Product> findBySize(List<String> productSize);
    public List<Product> findByColor(List<String> colors);
}
