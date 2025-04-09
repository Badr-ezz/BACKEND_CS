package com.example.springMongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Document(collection = "matches")
public class Match {
    @Id
    private String id;
    private String activityId;
    private int gameweek;
    private Team teamA;
    private Team teamB;
    private Integer scoreTeamA;
    private Integer scoreTeamB;
    private String status; // scheduled, played, canceled
    private LocalDate date;
    private String location;
    private List<GoalEvent> goalEvents;

    // Constructors
    public Match() {
        this.goalEvents = new ArrayList<>();
    }

    public Match(String activityId, int gameweek, Team teamA, Team teamB, LocalDate date, String location) {
        this.activityId = activityId;
        this.gameweek = gameweek;
        this.teamA = teamA;
        this.teamB = teamB;
        this.date = date;
        this.location = location;
        this.status = "scheduled";
        this.goalEvents = new ArrayList<>();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public int getGameweek() {
        return gameweek;
    }

    public void setGameweek(int gameweek) {
        this.gameweek = gameweek;
    }

    public Team getTeamA() {
        return teamA;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
    }

    public Integer getScoreTeamA() {
        return scoreTeamA;
    }

    public void setScoreTeamA(Integer scoreTeamA) {
        this.scoreTeamA = scoreTeamA;
    }

    public Integer getScoreTeamB() {
        return scoreTeamB;
    }

    public void setScoreTeamB(Integer scoreTeamB) {
        this.scoreTeamB = scoreTeamB;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<GoalEvent> getGoalEvents() {
        return goalEvents;
    }

    public void setGoalEvents(List<GoalEvent> goalEvents) {
        this.goalEvents = goalEvents;
    }

    public void addGoalEvent(GoalEvent goalEvent) {
        if (this.goalEvents == null) {
            this.goalEvents = new ArrayList<>();
        }
        this.goalEvents.add(goalEvent);
    }
}
