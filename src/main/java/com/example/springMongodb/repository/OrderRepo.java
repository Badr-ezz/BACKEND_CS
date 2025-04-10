package com.example.springMongodb.repository;

import com.example.springMongodb.model.Order;
import com.example.springMongodb.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepo extends MongoRepository<Order, String> {
    List<Order> findByUserId(String userId);
    List<Order> findByProductId(String productId);
    List<Order> findByStatus(String status);
    List<Order> findByStatusAndUser(String status, Users user);
}