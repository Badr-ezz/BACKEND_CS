package com.example.springMongodb.dto;

import com.example.springMongodb.model.Team;
import com.example.springMongodb.model.Users;

public class ParticipantRequest {
    private String type; // "TEAM" or "INDIVIDUAL"
    private Team team;
    private Users participant;

    public ParticipantRequest() {
    }

    public ParticipantRequest(String type, Team team, Users participant) {
        this.type = type;
        this.team = team;
        this.participant = participant;
    }

    // getters and setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }
    public Users getParticipant() { return participant; }
    public void setParticipant(Users participant) { this.participant = participant; }
}