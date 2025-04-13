package com.example.springMongodb.model;

import com.example.springMongodb.model.Users;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "friendlyMatches")
public class FriendlyMatch {
    @Id
    private String id;

    private String activityId;

    @DBRef
    private List<Users> teamA = new ArrayList<>();

    @DBRef
    private List<Users> teamB = new ArrayList<>();

    private Integer scoreTeamA;

    private Integer scoreTeamB;

    private List<GoalScorer> goalScorers = new ArrayList<>();

    private String status; // pending, played

    // Nested class for goal scorers
    public static class GoalScorer {
        private String playerId;
        private String playerName;
        private String team; // "teamA" or "teamB"
        private Integer minute;

        public GoalScorer() {}

        public GoalScorer(String playerId, String playerName, String team, Integer minute) {
            this.playerId = playerId;
            this.playerName = playerName;
            this.team = team;
            this.minute = minute;
        }

        public String getPlayerId() {
            return playerId;
        }

        public void setPlayerId(String playerId) {
            this.playerId = playerId;
        }

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public Integer getMinute() {
            return minute;
        }

        public void setMinute(Integer minute) {
            this.minute = minute;
        }
    }

    public FriendlyMatch() {}

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

    public List<Users> getTeamA() {
        return teamA;
    }

    public void setTeamA(List<Users> teamA) {
        this.teamA = teamA;
    }

    public List<Users> getTeamB() {
        return teamB;
    }

    public void setTeamB(List<Users> teamB) {
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

    public List<GoalScorer> getGoalScorers() {
        return goalScorers;
    }

    public void setGoalScorers(List<GoalScorer> goalScorers) {
        this.goalScorers = goalScorers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
