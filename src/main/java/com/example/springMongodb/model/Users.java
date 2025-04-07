package com.example.springMongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "users")
public class Users {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private RoleEnum role;
    private String googleId;
    private boolean isGoogleAccount;
    private Boolean contributed = false;


    // Initialize fields with empty strings instead of null
    private String phoneNumber = "";
    private Date birthDate;  // Keep as null for Date type
    private String address = "";


    @Override
    public String toString() {
        return "Users{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", googleId='" + googleId + '\'' +
                ", isGoogleAccount=" + isGoogleAccount +
                ", contributed=" + contributed +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                ", address='" + address + '\'' +
                '}';
    }

    // New fields
    public Users() {
        this.phoneNumber = "";
        this.address = "";
    }

    // Constructors



    public Users(String username,
                 String email,
                 String password,
                 RoleEnum role,
                 String googleId,
                 boolean isGoogleAccount,
                 Boolean contributed,
                 String phoneNumber,
                 Date birthDate,
                 String address) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.googleId = googleId;
        this.isGoogleAccount = isGoogleAccount;
        this.contributed = contributed;
        this.phoneNumber = phoneNumber != null ? phoneNumber : "";
        this.birthDate = birthDate;
        this.address = address != null ? address : "";
    }

    // Existing getters and setters
    public Boolean getContributed() {
        return contributed;
    }

    public void setContributed(Boolean contributed) {
        this.contributed = contributed;
    }

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

    // New getters and setters for the added fields
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}


