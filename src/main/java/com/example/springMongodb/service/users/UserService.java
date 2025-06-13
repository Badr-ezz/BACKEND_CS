package com.example.springMongodb.service.users;

import com.example.springMongodb.model.Users;

import java.util.List;


public interface UserService {
    public long countUsers();
    public List<Users> getAllUsers();
    public Users getUserById(String id);
    public Users getUserByEmail(String email);
    public Users getUserByUsername(String username);
    public Users addUser(Users user);
    public Users updateUser(Users user);
    public Users updateUserProfile(String id, Users userDetails);
    public void deleteUser(String id);
    public String verify(Users user);
    public String logout (String token);
    public Users setContributed(String idUser);
    public String resetPassword(String email, String newPassword);
    Users updateProfilePicture(String id, String pictureUrl);
}


