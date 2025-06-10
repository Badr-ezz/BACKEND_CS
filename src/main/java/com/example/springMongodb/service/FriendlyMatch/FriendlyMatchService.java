package com.example.springMongodb.service.FriendlyMatch;

import com.example.springMongodb.model.FriendlyMatch;
import com.example.springMongodb.repository.FriendlyMatchRepository;
import com.example.springMongodb.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.springMongodb.model.FriendlyMatch.*;

@Service
public class FriendlyMatchService {

    @Autowired
    private FriendlyMatchRepository friendlyMatchRepository;

    public FriendlyMatch createFriendlyMatch(String activityId, List<Users> teamA, List<Users> teamB) {
        // Check if a match already exists for this activity
        Optional<FriendlyMatch> existingMatch = friendlyMatchRepository.findByActivityId(activityId);
        if (existingMatch.isPresent()) {
            // Delete existing match
            friendlyMatchRepository.delete(existingMatch.get());
        }

        FriendlyMatch friendlyMatch = new FriendlyMatch();
        friendlyMatch.setActivityId(activityId);
        friendlyMatch.setTeamA(teamA);
        friendlyMatch.setTeamB(teamB);
        friendlyMatch.setScoreTeamA(0);
        friendlyMatch.setScoreTeamB(0);
        friendlyMatch.setStatus("pending");

        return friendlyMatchRepository.save(friendlyMatch);
    }

    public FriendlyMatch getFriendlyMatchByActivityId(String activityId) {
        return friendlyMatchRepository.findByActivityId(activityId)
                .orElseThrow(() -> new RuntimeException("Friendly match not found for activity: " + activityId));
    }

    public FriendlyMatch updateMatchResult(String activityId, Integer scoreTeamA, Integer scoreTeamB,
                                           String status, List<GoalScorer> goalScorers) {
        FriendlyMatch friendlyMatch = getFriendlyMatchByActivityId(activityId);
        friendlyMatch.setScoreTeamA(scoreTeamA);
        friendlyMatch.setScoreTeamB(scoreTeamB);
        friendlyMatch.setStatus(status);

        // Update goal scorers if provided
        if (goalScorers != null) {
            friendlyMatch.setGoalScorers(goalScorers);
        }

        return friendlyMatchRepository.save(friendlyMatch);
    }

    // Keep the old method for backward compatibility
    public FriendlyMatch updateMatchResult(String activityId, Integer scoreTeamA, Integer scoreTeamB, String status) {
        return updateMatchResult(activityId, scoreTeamA, scoreTeamB, status, null);
    }

    public void addGoalScorer(String activityId, GoalScorer goalScorer) {
        FriendlyMatch friendlyMatch = getFriendlyMatchByActivityId(activityId);
        friendlyMatch.getGoalScorers().add(goalScorer);
        friendlyMatchRepository.save(friendlyMatch);
    }

    public void deleteFriendlyMatch(String activityId) {
        friendlyMatchRepository.deleteByActivityId(activityId);
    }
}
