package com.example.springMongodb.repository;

import com.example.springMongodb.model.Product;
import com.example.springMongodb.model.ProductSize;
import com.example.springMongodb.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepo extends MongoRepository<Product, String> {
    public List<Product> findByProductCategoryIn(List<String> categories);
    public List<Product> findBySizeIn(List<String> productSize);
    public List<Product> findByColorIn(List<String> colors);
}
