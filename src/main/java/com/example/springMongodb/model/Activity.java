package com.example.springMongodb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "activities")
@AllArgsConstructor
@NoArgsConstructor
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
}
