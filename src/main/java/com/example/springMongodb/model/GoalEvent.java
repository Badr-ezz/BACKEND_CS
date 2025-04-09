package com.example.springMongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "goalEvents")
public class GoalEvent {
    @Id
    private String id;
    private String matchId;
    private String scorerId;
    private String scorerName;
    private String teamId;
    private int minute;
    private boolean isOwnGoal;

    // Constructors
    public GoalEvent() {
    }

    public GoalEvent(String matchId, String scorerId, String scorerName, String teamId, int minute, boolean isOwnGoal) {
        this.matchId = matchId;
        this.scorerId = scorerId;
        this.scorerName = scorerName;
        this.teamId = teamId;
        this.minute = minute;
        this.isOwnGoal = isOwnGoal;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getScorerId() {
        return scorerId;
    }

    public void setScorerId(String scorerId) {
        this.scorerId = scorerId;
    }

    public String getScorerName() {
        return scorerName;
    }

    public void setScorerName(String scorerName) {
        this.scorerName = scorerName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isOwnGoal() {
        return isOwnGoal;
    }

    public void setOwnGoal(boolean ownGoal) {
        isOwnGoal = ownGoal;
    }
}
