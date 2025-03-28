package com.example.springMongodb.service;

import com.example.springMongodb.model.Product;
import com.example.springMongodb.model.ProductSize;
import com.example.springMongodb.model.Users;
import com.example.springMongodb.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

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
        return productRepo.findById(id).get();
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
        Product prompt = productRepo.findById(id).get();
        if (prompt != null) {
            productRepo.delete(prompt);
        }
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
