package com.example.springMongodb.service.goalEvent;

import com.example.springMongodb.model.GoalEvent;
import java.util.List;

public interface GoalEventService {
    GoalEvent getGoalEventById(String id);
    List<GoalEvent> getGoalEventsByMatchId(String matchId);
    GoalEvent addGoalEvent(GoalEvent goalEvent);
    void deleteGoalEvent(String id);
}
