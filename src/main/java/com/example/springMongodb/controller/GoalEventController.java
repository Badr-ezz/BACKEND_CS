package com.example.springMongodb.controller;

import com.example.springMongodb.model.GoalEvent;
import com.example.springMongodb.service.goalEvent.GoalEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/goalevents")
public class GoalEventController {

    @Autowired
    private GoalEventService goalEventService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getGoalEventById(@PathVariable String id) {
        try {
            GoalEvent goalEvent = goalEventService.getGoalEventById(id);
            if (goalEvent == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Goal event not found");
            }
            return ResponseEntity.ok(goalEvent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving goal event: " + e.getMessage());
        }
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<?> getGoalEventsByMatchId(@PathVariable String matchId) {
        try {
            List<GoalEvent> goalEvents = goalEventService.getGoalEventsByMatchId(matchId);
            return ResponseEntity.ok(goalEvents);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving goal events: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addGoalEvent(@RequestBody GoalEvent goalEvent) {
        try {
            GoalEvent savedGoalEvent = goalEventService.addGoalEvent(goalEvent);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedGoalEvent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding goal event: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoalEvent(@PathVariable String id) {
        try {
            goalEventService.deleteGoalEvent(id);
            return ResponseEntity.ok("Goal event deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting goal event: " + e.getMessage());
        }
    }
}
