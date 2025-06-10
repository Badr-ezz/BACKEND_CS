package com.example.springMongodb.model;

import com.example.springMongodb.model.Users;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Document(collection = "billiardTournaments")
public class BilliardTournament {
    @Id
    private String id;

    private String activityId;

    private List<Match> quarterFinals = new ArrayList<>();
    private List<Match> semiFinals = new ArrayList<>();
    private Match finalMatch;

    @DBRef
    private Users champion;

    private String status; // "pending", "in_progress", "completed"

    public static class Match {
        @DBRef
        private Users player1;

        @DBRef
        private Users player2;

        @DBRef
        private Users winner;

        private Integer player1Score;
        private Integer player2Score;

        public Match() {
            this.player1Score = 0;
            this.player2Score = 0;
        }

        public Match(Users player1, Users player2) {
            this.player1 = player1;
            this.player2 = player2;
            this.player1Score = 0;
            this.player2Score = 0;
        }

        public Users getPlayer1() {
            return player1;
        }

        public void setPlayer1(Users player1) {
            this.player1 = player1;
        }

        public Users getPlayer2() {
            return player2;
        }

        public void setPlayer2(Users player2) {
            this.player2 = player2;
        }

        public Users getWinner() {
            return winner;
        }

        public void setWinner(Users winner) {
            this.winner = winner;
        }

        public Integer getPlayer1Score() {
            return player1Score;
        }

        public void setPlayer1Score(Integer player1Score) {
            this.player1Score = player1Score;
        }

        public Integer getPlayer2Score() {
            return player2Score;
        }

        public void setPlayer2Score(Integer player2Score) {
            this.player2Score = player2Score;
        }
    }

    public BilliardTournament() {
    }

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

    public List<Match> getQuarterFinals() {
        return quarterFinals;
    }

    public void setQuarterFinals(List<Match> quarterFinals) {
        this.quarterFinals = quarterFinals;
    }

    public List<Match> getSemiFinals() {
        return semiFinals;
    }

    public void setSemiFinals(List<Match> semiFinals) {
        this.semiFinals = semiFinals;
    }

    public Match getFinalMatch() {
        return finalMatch;
    }

    public void setFinalMatch(Match finalMatch) {
        this.finalMatch = finalMatch;
    }

    public Users getChampion() {
        return champion;
    }

    public void setChampion(Users champion) {
        this.champion = champion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Helper method to convert to a map for easier JSON serialization
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("activityId", activityId);
        map.put("quarterFinals", quarterFinals);
        map.put("semiFinals", semiFinals);
        map.put("finalMatch", finalMatch);
        map.put("champion", champion);
        map.put("status", status);
        return map;
    }
}
