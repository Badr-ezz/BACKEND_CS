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
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    private final JWTService jwtService;

    @Autowired
    public UserController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

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
            // Vérifier si l'utilisateur existe par email
            Users user = userService.getUserByEmail(request.getEmail());

            if (user != null) {
                // L'utilisateur existe, vérifier s'il a été créé avec Google
                if (user.isGoogleAccount() && user.getGoogleId() != null &&
                        user.getGoogleId().equals(request.getGoogleId())) {
                    // Générer un token JWT
                    String token = jwtService.generateToken(user);
                    return ResponseEntity.ok(token);
                } else if (!user.isGoogleAccount()) {
                    // L'utilisateur existe mais n'a pas été créé avec Google
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body("Cet email existe mais n'a pas été enregistré avec Google. Veuillez utiliser le login par mot de passe.");
                }
            } else {
                // L'utilisateur n'existe pas, le créer
                Users newUser = new Users();
                newUser.setEmail(request.getEmail());
                newUser.setUsername(request.getName());
                // Générer un mot de passe aléatoire que l'utilisateur n'utilisera jamais
                String randomPassword = UUID.randomUUID().toString();
                newUser.setPassword(randomPassword);
                newUser.setRole(RoleEnum.USER);
                newUser.setGoogleId(request.getGoogleId());
                newUser.setGoogleAccount(true);

                // Sauvegarder le nouvel utilisateur
                Users savedUser = userService.addUser(newUser);

                // Générer un token JWT
                String token = jwtService.generateToken(savedUser);
                return ResponseEntity.ok(token);
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de l'authentification Google");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'authentification Google: " + e.getMessage());
        }
    }
}
