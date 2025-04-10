package com.example.springMongodb.repository;

import com.example.springMongodb.model.Match;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepo extends MongoRepository<Match, String> {
    List<Match> findByActivityId(String activityId);
    List<Match> findByActivityIdAndGameweek(String activityId, int gameweek);
    List<Match> findByTeamA_IdOrTeamB_Id(String teamId, String teamId2);
    List<Match> findByStatus(String status);
}
