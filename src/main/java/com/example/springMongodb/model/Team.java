package com.example.springMongodb.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "teams")

public class Team {
    @Id
    private String id;

    private String name;
    private String logo;

    @DBRef
    private Users captain ;

    @DBRef
    private List<Users> members = new ArrayList<>();

    public Team(String id, String name, String logo, Users captain, List<Users> members) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.captain = captain;
        this.members = members;
    }

    public Team(String name, String logo, Users captain, List<Users> members) {
        this.name = name;
        this.logo = logo;
        this.captain = captain;
        this.members = members;
    }

    public Team() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Users getCaptain() {
        return captain;
    }

    public void setCaptain(Users captain) {
        this.captain = captain;
    }

    public List<Users> getMembers() {
        return members;
    }

    public void setMembers(List<Users> members) {
        this.members = members;
    }
}