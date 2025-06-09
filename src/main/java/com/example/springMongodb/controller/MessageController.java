package com.example.springMongodb.controller;

import com.example.springMongodb.model.Message;
import com.example.springMongodb.model.Team;
import com.example.springMongodb.model.Users;
import com.example.springMongodb.service.message.MessageService;
import com.example.springMongodb.service.team.TeamService;
import com.example.springMongodb.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin("*")
public class MessageController {

    private final MessageService messageService;
    private final TeamService teamService;
    private final UserService userService;

    @Autowired
    public MessageController(MessageService messageService, TeamService teamService, UserService userService) {
        this.messageService = messageService;
        this.teamService = teamService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Map<String, Object> payload) {
        String content = (String) payload.get("content");
        String type = (String) payload.get("type");
        String senderId = (String) payload.get("senderId");
        String teamId = (String) payload.get("teamId");

        Users sender = userService.getUserById(senderId);
        Team team = teamService.getTeamById(teamId);

        Message message = new Message(content, type, sender, team);
        message.setCreatedAt(LocalDateTime.now());

        return ResponseEntity.ok(messageService.saveMessage(message));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Message>> createMessages(@RequestBody List<Map<String, Object>> messagesData) {
        List<Message> messages = new ArrayList<>();

        for (Map<String, Object> data : messagesData) {
            String content = (String) data.get("content");
            String type = (String) data.get("type");
            String senderId = (String) data.get("senderId");
            String teamId = (String) data.get("teamId");

            Users sender = userService.getUserById(senderId);
            Team team = teamService.getTeamById(teamId);

            Message message = new Message(content, type, sender, team);
            message.setCreatedAt(LocalDateTime.now());
            messages.add(message);
        }

        return ResponseEntity.ok(messageService.saveMessages(messages));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable String id) {
        return ResponseEntity.ok(messageService.getMessageById(id));
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<Map<String, Object>>> getMessagesByTeam(
            @PathVariable String teamId,
            @RequestParam(defaultValue = "10") int limit) {

        List<Message> messages = messageService.getMessagesByTeam(teamId, limit);
        List<Map<String, Object>> formattedMessages = new ArrayList<>();

        for (Message message : messages) {
            Map<String, Object> formatted = new HashMap<>();
            formatted.put("id", message.getId());
            formatted.put("content", message.getContent());
            formatted.put("type", message.getType());
            formatted.put("senderId", message.getSender().getId());
            formatted.put("senderName", message.getSender().getUsername());
            formatted.put("senderAvatar", message.getSender().getProfilePicture());
            formatted.put("teamId", message.getTeam().getId());
            formatted.put("createdAt", message.getCreatedAt().toString());

            formattedMessages.add(formatted);
        }

        return ResponseEntity.ok(formattedMessages);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Message>> getMessagesBySender(
            @PathVariable String userId,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(messageService.getMessagesBySender(userId, limit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable String id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }

//    // Add a new endpoint for getting current user
//    @GetMapping("/current")
//    public ResponseEntity<Map<String, Object>> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
//        try {
//            // Extract token from Authorization header
//            String token = authHeader.substring(7); // Remove "Bearer " prefix
//
//            // In a real implementation, you would decode the JWT token to get the user ID
//            // For now, we'll assume you have a method to get the current user from the token
//            Users currentUser = userService.getUserFromToken(token);
//
//            if (currentUser == null) {
//                return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
//            }
//
//            Map<String, Object> userInfo = new HashMap<>();
//            userInfo.put("id", currentUser.getId());
//            userInfo.put("username", currentUser.getUsername());
//            userInfo.put("email", currentUser.getEmail());
//            userInfo.put("profilePicture", currentUser.getProfilePicture());
//
//            return ResponseEntity.ok(userInfo);
//        } catch (Exception e) {
//            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
//        }
//    }
}
