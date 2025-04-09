package com.example.springMongodb.controller;

import com.example.springMongodb.model.Match;
import com.example.springMongodb.model.TeamStanding;
import com.example.springMongodb.model.TopScorer;
import com.example.springMongodb.service.match.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getMatchById(@PathVariable String id) {
        try {
            Match match = matchService.getMatchById(id);
            if (match == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Match not found");
            }
            return ResponseEntity.ok(match);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving match: " + e.getMessage());
        }
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<?> getMatchesByActivityId(@PathVariable String activityId) {
        try {
            List<Match> matches = matchService.getMatchesByActivityId(activityId);
            return ResponseEntity.ok(matches);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving matches: " + e.getMessage());
        }
    }

    @GetMapping("/activity/{activityId}/gameweek/{gameweek}")
    public ResponseEntity<?> getMatchesByActivityIdAndGameweek(
            @PathVariable String activityId,
            @PathVariable int gameweek) {
        try {
            List<Match> matches = matchService.getMatchesByActivityIdAndGameweek(activityId, gameweek);
            return ResponseEntity.ok(matches);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving matches: " + e.getMessage());
        }
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateMatchesForGameweek(
            @RequestParam String activityId,
            @RequestParam int gameweek) {
        try {
            List<Match> matches = matchService.generateMatchesForGameweek(activityId, gameweek);
            return ResponseEntity.ok(matches);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating matches: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMatch(@PathVariable String id, @RequestBody Match match) {
        try {
            if (!id.equals(match.getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Match ID in path does not match ID in body");
            }
            Match updatedMatch = matchService.updateMatch(match);
            return ResponseEntity.ok(updatedMatch);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating match: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMatch(@PathVariable String id) {
        try {
            matchService.deleteMatch(id);
            return ResponseEntity.ok("Match deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting match: " + e.getMessage());
        }
    }

    @GetMapping("/activity/{activityId}/standings")
    public ResponseEntity<?> getStandings(@PathVariable String activityId) {
        try {
            List<TeamStanding> standings = matchService.getStandings(activityId);
            return ResponseEntity.ok(standings);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving standings: " + e.getMessage());
        }
    }

    @GetMapping("/activity/{activityId}/topscorers")
    public ResponseEntity<?> getTopScorers(@PathVariable String activityId) {
        try {
            List<TopScorer> topScorers = matchService.getTopScorers(activityId);
            return ResponseEntity.ok(topScorers);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving top scorers: " + e.getMessage());
        }
    }
}


