package com.example.springMongodb.repository;

import com.example.springMongodb.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<Users, String> {
    Users findByEmail(String email);
    Users findByUsername(String email);
}
