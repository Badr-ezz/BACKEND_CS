package com.example.springMongodb.repository;

import com.example.springMongodb.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ActivityRepo extends MongoRepository<Activity, String> {
    List<Activity> findByType(String type);
    List<Activity> findBySport(String sport);
    List<Activity> findByDateBetween(LocalDate start, LocalDate end);
    List<Activity> findByLocalisation(String localisation);
    // In ActivityRepo.java
    long countByType(String type);
}