package com.example.springMongodb.service.users;

import com.example.springMongodb.model.Users;
import com.example.springMongodb.repository.UserRepo;
import com.example.springMongodb.service.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserRepo userRepo;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


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
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.insert(user);
    }

    @Override
    public Users updateUser(Users user) {
        Users existingUser = userRepo.findById(user.getId()).orElse(null);
        if (existingUser == null) {
            throw (new RuntimeException("User does not exist"));
        }

        // Only encode password if it's different from the existing one
        // This prevents re-encoding an already encoded password
        if (user.getPassword() != null && !user.getPassword().equals(existingUser.getPassword())) {
            user.setPassword(encoder.encode(user.getPassword()));
        } else {
            // Keep the existing password if not changed
            user.setPassword(existingUser.getPassword());
        }

        return userRepo.save(user);
    }

    @Override
    public Users updateUserProfile(String id, Users userDetails) {
        Users existingUser = userRepo.findById(id).orElse(null);
        if (existingUser == null) {
            throw (new RuntimeException("User does not exist"));
        }

        // Update only profile fields, not sensitive information
        if (userDetails.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(userDetails.getPhoneNumber());
        }

        if (userDetails.getBirthDate() != null) {
            existingUser.setBirthDate(userDetails.getBirthDate());
        }

        if (userDetails.getAddress() != null) {
            existingUser.setAddress(userDetails.getAddress());
        }

        // Optional: update username if provided
        if (userDetails.getUsername() != null && !userDetails.getUsername().isEmpty()) {
            existingUser.setUsername(userDetails.getUsername());
        }

        return userRepo.save(existingUser);
    }

    @Override
    public void deleteUser(String id) {
        Users check_user_exist = userRepo.findById(id).orElse(null);
        if (check_user_exist == null) {
            throw (new RuntimeException("User does not exist"));
        }
        userRepo.deleteById(id);
    }

    @Override
    public String verify(Users user) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if(auth.isAuthenticated()) {
            Users foundUser = userRepo.findByEmail(user.getEmail());
            System.out.println(foundUser);
            return jwtService.generateToken(foundUser);
        }
        return "fail";
    }

    @Override
    public String logout (String token) {
        return jwtService.logout(token);
    }

    @Override
    public Users setContributed(String idUser) {
        Users searchUser =  userRepo.findById(idUser).orElseThrow(() -> new RuntimeException("Activity not found"));
        searchUser.setContributed(true);
        return userRepo.save(searchUser);
    }
}