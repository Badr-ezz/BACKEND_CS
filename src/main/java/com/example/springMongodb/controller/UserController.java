package com.example.springMongodb.controller;

import com.example.springMongodb.model.Users;
import com.example.springMongodb.service.jwt.JWTService;
import com.example.springMongodb.service.users.UserService;
import com.example.springMongodb.model.GoogleLoginRequest;
import com.example.springMongodb.model.RoleEnum;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JWTService jwtService;

    @Autowired
    public UserController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    // Existing methods...

    // New endpoint for updating profile information
    @PatchMapping("/profile/{id}")
    public ResponseEntity<Users> updateUserProfile(@PathVariable String id, @RequestBody Users userDetails) {
        try {
            Users updatedUser = userService.updateUserProfile(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Existing methods...
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user) {
        String response = userService.verify(user);
        if (response.equals("fail")) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Extract token
            System.out.println("the token is " + token);
            String result = userService.logout(token);
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or missing Authorization header.");
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countUsers() {
        return ResponseEntity.ok(userService.countUsers());
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable String id) {
        Users user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // Get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<Users> getUserByEmail(@PathVariable String email) {
        Users user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // Get user by username
    @GetMapping("/username/{username}")
    public ResponseEntity<Users> getUserByUsername(@PathVariable String username) {
        Users user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // Add new user
    @PostMapping
    public ResponseEntity<Users> addUser(@RequestBody Users user) {
        try {
            Users newUser = userService.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/setcontribited/{id}")
    public ResponseEntity<Users> setContribited(@PathVariable String id) {
        Users user = userService.setContributed(id);
        return ResponseEntity.ok(user);
    }

    // Update existing user
    @PutMapping
    public ResponseEntity<Users> updateUser(@RequestBody Users user) {
        try {
            Users updatedUser = userService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/google-login")
    public ResponseEntity<String> googleLogin(@RequestBody GoogleLoginRequest request) {
        try {
            // Find user by email
            Users user = userService.getUserByEmail(request.getEmail());

            if (user != null) {
                // User exists, check if created with Google
                if (user.isGoogleAccount() && user.getGoogleId() != null &&
                        user.getGoogleId().equals(request.getGoogleId())) {
                    // Generate JWT token
                    String token = jwtService.generateToken(user);
                    return ResponseEntity.ok(token);
                } else if (!user.isGoogleAccount()) {
                    // User exists but not created with Google
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body("This email exists but was not registered with Google. Please use password login.");
                }
            } else {
                // User doesn't exist, create new user
                Users newUser = new Users();
                newUser.setEmail(request.getEmail());
                newUser.setUsername(request.getName());
                // Generate random password
                String randomPassword = UUID.randomUUID().toString();
                newUser.setPassword(randomPassword);
                newUser.setRole(RoleEnum.USER);
                newUser.setGoogleId(request.getGoogleId());
                newUser.setGoogleAccount(true);

                // Save new user
                Users savedUser = userService.addUser(newUser);

                // Generate JWT token
                String token = jwtService.generateToken(savedUser);
                return ResponseEntity.ok(token);
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Google authentication failed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during Google login: " + e.getMessage());
        }
    }


}