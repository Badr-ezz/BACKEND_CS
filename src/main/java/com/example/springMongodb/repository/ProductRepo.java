package com.example.springMongodb.repository;

import com.example.springMongodb.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends MongoRepository<Product, String> {
    List<Product> findByProductCategoryIn(List<String> categories);
    List<Product> findBySizeIn(List<String> sizes);
    List<Product> findByColorIn(List<String> colors);
}