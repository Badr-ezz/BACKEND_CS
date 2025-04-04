package com.example.springMongodb.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "activities")

public class Activity {
    @Id
    private String id;
    private String type;
    private String name;
    private LocalDate date;
    private String description;
    private LocalDate startingDate;
    private LocalDate endingDate;
    private String localisation;
    private int nbrParticipants;
    private Boolean isTournamentFull;

    @DBRef
    private List<Team> teamParticipants;  // for group sport

    @DBRef
    private List<Users> individualParticipants; // for individual sport

    private String sport;
    private int nbrTeams;
    private int nbrCurrentTeam;
    private int nbrPerTeam;

    // For Team Sports (Tournaments)
    public Activity(String type,
                    String name,
                    LocalDate date,
                    String description,
                    LocalDate startingDate,
                    LocalDate endingDate,
                    String localisation,
                    int nbrParticipants,
                    String sport,
                    int nbrTeams,
                    int nbrPerTeam,
                    List<Team> teamParticipants) {
        this.type = type;
        this.name = name;
        this.date = date;
        this.description = description;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.localisation = localisation;
        this.nbrParticipants = nbrParticipants;
        this.sport = sport;
        this.nbrTeams = nbrTeams;
        this.nbrPerTeam = nbrPerTeam;
        this.teamParticipants = teamParticipants;
        this.individualParticipants = null; // Explicitly set to null
        this.isTournamentFull = false;
    }

    public Activity(String id, String type, String name, LocalDate date, String description, LocalDate startingDate, LocalDate endingDate, String localisation, int nbrParticipants, Boolean isTournamentFull, List<Team> teamParticipants, List<Users> individualParticipants, String sport, int nbrTeams, int nbrCurrentTeam, int nbrPerTeam) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.date = date;
        this.description = description;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.localisation = localisation;
        this.nbrParticipants = nbrParticipants;
        this.isTournamentFull = isTournamentFull;
        this.teamParticipants = teamParticipants;
        this.individualParticipants = individualParticipants;
        this.sport = sport;
        this.nbrTeams = nbrTeams;
        this.nbrCurrentTeam = nbrCurrentTeam;
        this.nbrPerTeam = nbrPerTeam;
    }

    public Activity() {
    }

    // For Individual Sports
    public Activity(String type,
                    String name,
                    LocalDate date,
                    String description,
                    LocalDate startingDate,
                    LocalDate endingDate,
                    String localisation,
                    int nbrParticipants,
                    List<Users> individualParticipants) {
        this.type = type;
        this.name = name;
        this.date = date;
        this.description = description;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.localisation = localisation;
        this.nbrParticipants = nbrParticipants;
        this.individualParticipants = individualParticipants;
        this.teamParticipants = null; // Explicitly set to null
        this.sport = null;
        this.nbrTeams = 0;
        this.nbrPerTeam = 0;
        this.isTournamentFull = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public int getNbrParticipants() {
        return nbrParticipants;
    }

    public void setNbrParticipants(int nbrParticipants) {
        this.nbrParticipants = nbrParticipants;
    }

    public Boolean getIsTournamentFull() {
        return isTournamentFull;
    }

    public void setIsTournamentFull(Boolean tournamentFull) {
        isTournamentFull = tournamentFull;
    }

    public List<Team> getTeamParticipants() {
        return teamParticipants;
    }

    public void setTeamParticipants(List<Team> teamParticipants) {
        this.teamParticipants = teamParticipants;
    }

    public List<Users> getIndividualParticipants() {
        return individualParticipants;
    }

    public void setIndividualParticipants(List<Users> individualParticipants) {
        this.individualParticipants = individualParticipants;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public int getNbrTeams() {
        return nbrTeams;
    }

    public void setNbrTeams(int nbrTeams) {
        this.nbrTeams = nbrTeams;
    }

    public int getNbrCurrentTeam() {
        return nbrCurrentTeam;
    }

    public void setNbrCurrentTeam(int nbrCurrentTeam) {
        this.nbrCurrentTeam = nbrCurrentTeam;
    }

    public int getNbrPerTeam() {
        return nbrPerTeam;
    }

    public void setNbrPerTeam(int nbrPerTeam) {
        this.nbrPerTeam = nbrPerTeam;
    }
}
