package com.example.springMongodb.service.goalEvent;

import com.example.springMongodb.model.GoalEvent;
import com.example.springMongodb.repository.GoalEventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalEventServiceImpl implements GoalEventService {

    @Autowired
    private GoalEventRepo goalEventRepo;

    @Override
    public GoalEvent getGoalEventById(String id) {
        return goalEventRepo.findById(id).orElse(null);
    }

    @Override
    public List<GoalEvent> getGoalEventsByMatchId(String matchId) {
        return goalEventRepo.findByMatchId(matchId);
    }

    @Override
    public GoalEvent addGoalEvent(GoalEvent goalEvent) {
        // Validate goal event
        if (goalEvent.getMatchId() == null || goalEvent.getMatchId().isEmpty()) {
            throw new IllegalArgumentException("Match ID cannot be null or empty");
        }
        if (goalEvent.getTeamId() == null || goalEvent.getTeamId().isEmpty()) {
            throw new IllegalArgumentException("Team ID cannot be null or empty");
        }

        // We'll allow empty scorerId for backward compatibility, but warn about it
        if (goalEvent.getScorerId() == null || goalEvent.getScorerId().isEmpty()) {
            System.out.println("Warning: Scorer ID is empty for goal event in match " + goalEvent.getMatchId());
        }

        if (goalEvent.getScorerName() == null || goalEvent.getScorerName().isEmpty()) {
            throw new IllegalArgumentException("Scorer name cannot be null or empty");
        }

        if (goalEvent.getMinute() < 1 || goalEvent.getMinute() > 90) {
            throw new IllegalArgumentException("Minute must be between 1 and 90");
        }

        // Save goal event
        return goalEventRepo.save(goalEvent);
    }

    @Override
    public void deleteGoalEvent(String id) {
        goalEventRepo.deleteById(id);
    }
}
