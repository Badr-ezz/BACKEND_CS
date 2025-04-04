package com.example.springMongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class Users {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private RoleEnum role;
    private String googleId; // Nouveau champ pour stocker l'ID Google
    private boolean isGoogleAccount; // Pour identifier les comptes créés avec Google


    private Boolean contributed = false ;

    // Constructors
    public Users() {}

    // Constructeurs


    public Boolean getContributed() {
        return contributed;
    }

    public void setContributed(Boolean contributed) {
        this.contributed = contributed;
    }

    public Users(String username, String email, String password, RoleEnum role, Boolean contributed) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.contributed = contributed;
        this.isGoogleAccount = false;
    }

    public Users(String username, String email, String password, RoleEnum role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isGoogleAccount = false;
    }
    public Users(String username, String email, String password, RoleEnum role, String googleId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.googleId = googleId;
        this.isGoogleAccount = true;
    }

    // Getters et Setters existants...

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public boolean isGoogleAccount() {
        return isGoogleAccount;
    }

    public void setGoogleAccount(boolean googleAccount) {
        isGoogleAccount = googleAccount;
    }
}