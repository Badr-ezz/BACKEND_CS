package com.example.springMongodb.model;

public class TopScorer {
    private String scorerId;
    private String scorerName;
    private Team team;
    private int goals;

    public TopScorer() {
    }

    public TopScorer(String scorerId, String scorerName, Team team, int goals) {
        this.scorerId = scorerId;
        this.scorerName = scorerName;
        this.team = team;
        this.goals = goals;
    }

    // Getters and setters
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public void addGoal() {
        this.goals++;
    }
}
