package com.example.springMongodb.repository;

import com.example.springMongodb.model.FriendlyMatch;
import com.example.springMongodb.model.FriendlyMatch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendlyMatchRepository extends MongoRepository<FriendlyMatch, String> {
    Optional<FriendlyMatch> findByActivityId(String activityId);
    void deleteByActivityId(String activityId);
}
