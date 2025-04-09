package com.example.springMongodb.repository;

import com.example.springMongodb.model.GoalEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalEventRepo extends MongoRepository<GoalEvent, String> {
    List<GoalEvent> findByMatchId(String matchId);
    List<GoalEvent> findByScorerId(String scorerId);
    List<GoalEvent> findByTeamId(String teamId);
}
