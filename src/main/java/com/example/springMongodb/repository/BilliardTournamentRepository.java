package com.example.springMongodb.repository;

import com.example.springMongodb.model.BilliardTournament;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BilliardTournamentRepository extends MongoRepository<BilliardTournament, String> {
    Optional<BilliardTournament> findByActivityId(String activityId);
    void deleteByActivityId(String activityId);
}
