package com.example.springMongodb.controller;

import com.example.springMongodb.model.FriendlyMatch;
import com.example.springMongodb.service.FriendlyMatch.FriendlyMatchService;
import com.example.springMongodb.model.Users;
import com.example.springMongodb.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/friendly-matches")
public class FriendlyMatchController {

    @Autowired
    private FriendlyMatchService friendlyMatchService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createFriendlyMatch(@RequestBody Map<String, Object> request) {
        try {
            String activityId = (String) request.get("activityId");

            // Get user IDs from request
            List<Map<String, Object>> teamAData = (List<Map<String, Object>>) request.get("teamA");
            List<Map<String, Object>> teamBData = (List<Map<String, Object>>) request.get("teamB");

            // Convert to Users objects
            List<Users> teamA = teamAData.stream()
                    .map(userData -> userService.getUserById((String) userData.get("id")))
                    .collect(Collectors.toList());

            List<Users> teamB = teamBData.stream()
                    .map(userData -> userService.getUserById((String) userData.get("id")))
                    .collect(Collectors.toList());

            FriendlyMatch friendlyMatch = friendlyMatchService.createFriendlyMatch(activityId, teamA, teamB);
            return ResponseEntity.ok(friendlyMatch);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating friendly match: " + e.getMessage());
        }
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<?> getFriendlyMatch(@PathVariable String activityId) {
        try {
            FriendlyMatch friendlyMatch = friendlyMatchService.getFriendlyMatchByActivityId(activityId);
            return ResponseEntity.ok(friendlyMatch);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Friendly match not found: " + e.getMessage());
        }
    }

    @PutMapping("/{activityId}")
    public ResponseEntity<?> updateMatchResult(
            @PathVariable String activityId,
            @RequestBody Map<String, Object> request) {
        try {
            Integer scoreTeamA = Integer.parseInt(request.get("scoreTeamA").toString());
            Integer scoreTeamB = Integer.parseInt(request.get("scoreTeamB").toString());
            String status = (String) request.get("status");

            // Get goal scorers from request if present
            List<FriendlyMatch.GoalScorer> goalScorers = null;
            if (request.containsKey("goalScorers")) {
                // Convert the JSON array to GoalScorer objects
                ObjectMapper mapper = new ObjectMapper();
                goalScorers = mapper.convertValue(
                        request.get("goalScorers"),
                        new TypeReference<List<FriendlyMatch.GoalScorer>>() {}
                );
            }

            FriendlyMatch updatedMatch = friendlyMatchService.updateMatchResult(
                    activityId, scoreTeamA, scoreTeamB, status, goalScorers);
            return ResponseEntity.ok(updatedMatch);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating match result: " + e.getMessage());
        }
    }

    @PostMapping("/{activityId}/goal-scorers")
    public ResponseEntity<?> addGoalScorer(
            @PathVariable String activityId,
            @RequestBody FriendlyMatch.GoalScorer goalScorer) {
        try {
            friendlyMatchService.addGoalScorer(activityId, goalScorer);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding goal scorer: " + e.getMessage());
        }
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<?> deleteFriendlyMatch(@PathVariable String activityId) {
        try {
            friendlyMatchService.deleteFriendlyMatch(activityId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting friendly match: " + e.getMessage());
        }
    }
}
