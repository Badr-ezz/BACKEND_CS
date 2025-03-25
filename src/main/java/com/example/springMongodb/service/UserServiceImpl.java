package com.example.springMongodb.service;

import com.example.springMongodb.model.Users;
import com.example.springMongodb.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private  UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public long countUsers() {
        return userRepo.count();
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Users getUserById(String id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public Users getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public Users getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public Users addUser(Users user) {
        Users check_user_exist = userRepo.findByEmail(user.getEmail());
        if (check_user_exist != null) {
            throw (new RuntimeException("User already exists with email: " + user.getEmail()));
        }
        return userRepo.insert(user);
    }

    @Override
    public Users updateUser(Users user) {
        Users check_user_exist = userRepo.findById(user.getId()).orElse(null);
        if (check_user_exist == null) {
            throw (new RuntimeException("User does not exist"));
        }
        return userRepo.save(user);    }

    @Override
    public void deleteUser(String id) {
        Users check_user_exist = userRepo.findById(id).orElse(null);
        if (check_user_exist == null) {
            throw (new RuntimeException("User does not exist"));
        }
        userRepo.deleteById(id);
    }
}
