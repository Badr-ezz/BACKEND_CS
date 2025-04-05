package com.example.springMongodb.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private int nbrCurrentParticipants;
    private Boolean isTournamentFull;
    private String time ;
    private String image;

    public Activity() {
    }

    @DBRef
    private List<Team> teamParticipants = new ArrayList<>();  // for group sport

    @DBRef
    private List<Users> individualParticipants = new ArrayList<>(); // for individual sport

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
                    int nbrParticipants, int nbrCurrentParticipants,
                    String sport,
                    int nbrTeams,
                    int nbrPerTeam,
                    List<Team> teamParticipants,
                    String time,
                    String image) {
        this.type = type;
        this.name = name;
        this.date = date;
        this.description = description;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.localisation = localisation;
        this.nbrParticipants = nbrParticipants;
        this.nbrCurrentParticipants = nbrCurrentParticipants;
        this.sport = sport;
        this.nbrTeams = nbrTeams;
        this.nbrPerTeam = nbrPerTeam;
        this.teamParticipants = teamParticipants;
        this.time = time;
        this.image = image;
        this.individualParticipants = null; // Explicitly set to null
        this.isTournamentFull = false;
    }

    public Activity(String id, String time, String image, String type, String name, LocalDate date, String description, LocalDate startingDate, LocalDate endingDate, String localisation, int nbrParticipants, int nbrCurrentParticipants, Boolean isTournamentFull, List<Team> teamParticipants, List<Users> individualParticipants, String sport, int nbrTeams, int nbrCurrentTeam, int nbrPerTeam) {
        this.id = id;
        this.time = time;
        this.image = image;
        this.type = type;
        this.name = name;
        this.date = date;
        this.description = description;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.localisation = localisation;
        this.nbrParticipants = nbrParticipants;
        this.nbrCurrentParticipants = nbrCurrentParticipants;
        this.isTournamentFull = isTournamentFull;
        this.teamParticipants = teamParticipants;
        this.individualParticipants = individualParticipants;
        this.sport = sport;
        this.nbrTeams = nbrTeams;
        this.nbrCurrentTeam = nbrCurrentTeam;
        this.nbrPerTeam = nbrPerTeam;
    }

    public Activity(int nbrCurrentParticipants) {
        this.nbrCurrentParticipants = nbrCurrentParticipants;
    }

    // For Individual Sports
    public Activity(String type,
                    String name,
                    LocalDate date,
                    String description,
                    LocalDate startingDate,
                    LocalDate endingDate,
                    String localisation,
                    int nbrParticipants, int nbrCurrentParticipants,
                    List<Users> individualParticipants,
                    String time,
                    String image) {
        this.type = type;
        this.name = name;
        this.date = date;
        this.description = description;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.localisation = localisation;
        this.nbrParticipants = nbrParticipants;
        this.nbrCurrentParticipants = nbrCurrentParticipants;
        this.individualParticipants = individualParticipants;
        this.time = time;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getTournamentFull() {
        return isTournamentFull;
    }

    public void setTournamentFull(Boolean tournamentFull) {
        isTournamentFull = tournamentFull;
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

    public int getNbrCurrentParticipants() {
        return nbrCurrentParticipants;
    }

    public void setNbrCurrentParticipants(int nbrCurrentParticipants) {
        this.nbrCurrentParticipants = nbrCurrentParticipants;
    }
}
