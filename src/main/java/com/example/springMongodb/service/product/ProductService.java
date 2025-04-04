package com.example.springMongodb.service.product;

import com.example.springMongodb.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {
    Long countProducts();
    List<Product> getAllProducts();
    Product getProductById(String id);
    Product addProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(String id);
    List<Product> findByProductCategory(List<String> categories);
    List<Product> findBySize(List<String> sizes);
    List<Product> findByColor(List<String> colors);

    // New methods
    List<Product> filterProducts(List<String> categories, List<String> colors, List<String> sizes);
    List<String> getAllCategories();
    List<String> getAllColors();
    List<String> getAllSizes();
}