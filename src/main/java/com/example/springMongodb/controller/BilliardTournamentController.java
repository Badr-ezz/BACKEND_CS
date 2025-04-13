package com.example.springMongodb.controller;

import com.example.springMongodb.model.BilliardTournament;
import com.example.springMongodb.service.Billard.BilliardTournamentService;
import com.example.springMongodb.model.Users;
import com.example.springMongodb.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/billiard-tournaments")
public class BilliardTournamentController {

    @Autowired
    private BilliardTournamentService billiardTournamentService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createTournament(@RequestBody Map<String, Object> request) {
        try {
            String activityId = (String) request.get("activityId");
            List<Map<String, Object>> participantsData = (List<Map<String, Object>>) request.get("participants");

            // Convert to Users objects
            List<Users> participants = participantsData.stream()
                    .map(userData -> userService.getUserById((String) userData.get("id")))
                    .collect(Collectors.toList());

            BilliardTournament tournament = billiardTournamentService.createTournament(activityId, participants);
            return ResponseEntity.ok(tournament);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating billiard tournament: " + e.getMessage());
        }
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<?> getTournament(@PathVariable String activityId) {
        try {
            BilliardTournament tournament = billiardTournamentService.getTournamentByActivityId(activityId);
            return ResponseEntity.ok(tournament);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Billiard tournament not found: " + e.getMessage());
        }
    }

    @PutMapping("/{activityId}/matches")
    public ResponseEntity<?> updateMatchResult(
            @PathVariable String activityId,
            @RequestBody Map<String, Object> request) {
        try {
            String round = (String) request.get("round");
            int matchIndex = (Integer) request.get("matchIndex");
            String winnerId = (String) request.get("winnerId");
            Integer player1Score = (Integer) request.get("player1Score");
            Integer player2Score = (Integer) request.get("player2Score");

            Users winner = userService.getUserById(winnerId);

            BilliardTournament updatedTournament = billiardTournamentService.updateMatchResult(
                    activityId, round, matchIndex, winner, player1Score, player2Score);

            return ResponseEntity.ok(updatedTournament);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating match result: " + e.getMessage());
        }
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<?> deleteTournament(@PathVariable String activityId) {
        try {
            billiardTournamentService.deleteTournament(activityId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting billiard tournament: " + e.getMessage());
        }
    }
}
