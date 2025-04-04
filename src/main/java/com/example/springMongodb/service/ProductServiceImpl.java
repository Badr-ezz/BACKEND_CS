package com.example.springMongodb.service;

import com.example.springMongodb.model.Product;
import com.example.springMongodb.model.ProductSize;
import com.example.springMongodb.model.Users;
import com.example.springMongodb.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Override
    public long countProducts() {
        return productRepo.count();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(String id) {
        // Fix: Use orElse(null) instead of get() to avoid NoSuchElementException
        return productRepo.findById(id).orElse(null);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepo.insert(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public void deleteProduct(String id) {
        // Fix: Check if product exists before deleting
        Optional<Product> productOptional = productRepo.findById(id);
        if (productOptional.isPresent()) {
            productRepo.delete(productOptional.get());
        }
        // If product doesn't exist, do nothing (or you could throw a custom exception)
    }

    @Override
    public List<Product> findByProductCategory(List<String> categories) {
        return productRepo.findByProductCategoryIn(categories);
    }

    @Override
    public List<Product> findBySize(List<String> productSize) {
        return productRepo.findBySizeIn(productSize);
    }

    @Override
    public List<Product> findByColor(List<String> colors) {
        return productRepo.findByColorIn(colors);
    }
}