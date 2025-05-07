package com.example.springMongodb.controller;


import com.example.springMongodb.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage, @AuthenticationPrincipal User user) {
        // Add sender info from JWT
        chatMessage.setSender(user.getUsername());
        chatMessage.setTimestamp(System.currentTimeMillis());

        // Broadcast to team channel
        messagingTemplate.convertAndSend("/topic/team." + chatMessage.getTeamId(), chatMessage);

        // You might also want to persist the message to database here
    }
}