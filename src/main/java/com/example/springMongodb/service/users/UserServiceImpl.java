package com.example.springMongodb.service.users;

import com.example.springMongodb.model.RoleEnum;
import com.example.springMongodb.model.Users;
import com.example.springMongodb.repository.UserRepo;
import com.example.springMongodb.service.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;
    private final JWTService jwtService;

    @Autowired
    public UserServiceImpl(UserRepo userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public long countUsers() {
        return userRepository.count();
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Users getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Users getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Users addUser(Users user) {
        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }

        // Set default role if not provided
        if (user.getRole() == null) {
            user.setRole(RoleEnum.USER);
        }

        return userRepository.save(user);
    }

    @Override
    public Users updateUser(Users user) {
        // Check if user exists
        if (!userRepository.existsById(user.getId())) {
            throw new RuntimeException("User not found");
        }

        return userRepository.save(user);
    }

    @Override
    public Users updateUserProfile(String id, Users userDetails) {
        Users existingUser = getUserById(id);
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }

        // Update only profile fields
        if (userDetails.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(userDetails.getPhoneNumber());
        }
        if (userDetails.getBirthDate() != null) {
            existingUser.setBirthDate(userDetails.getBirthDate());
        }
        if (userDetails.getAddress() != null) {
            existingUser.setAddress(userDetails.getAddress());
        }
        if (userDetails.getProfilePicture() != null) {
            existingUser.setProfilePicture(userDetails.getProfilePicture());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public String verify(Users user) {
        Users existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return jwtService.generateToken(existingUser);
        }
        return "fail";
    }

    @Override
    public String logout(String token) {
        // Implement token invalidation logic here
        return "Logged out successfully";
    }

    @Override
    public Users setContributed(String idUser) {
        Users user = getUserById(idUser);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        user.setContributed(true);
        return userRepository.save(user);
    }

    @Override
    public Users updateProfilePicture(String id, String pictureUrl) {
        Users user = getUserById(id);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        user.setProfilePicture(pictureUrl);
        return userRepository.save(user);
    }

}
