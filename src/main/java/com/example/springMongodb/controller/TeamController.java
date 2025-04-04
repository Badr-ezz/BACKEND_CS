package com.example.springMongodb.controller;

import com.example.springMongodb.model.Team;
import com.example.springMongodb.model.Users;
import com.example.springMongodb.service.team.TeamService;
import com.example.springMongodb.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TeamController {
    private final TeamService teamService;
    private final UserService userService;

    @PostMapping
    public Team createTeam(@RequestBody Team team) {
        // Verify users exist first
        team.getMembers().forEach(user -> {
            if( userService.getUserById(user.getId()) == null ) {
                throw new RuntimeException("User not found: " + user.getId());
            }
        });
        return teamService.createTeam(team);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable String id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(
            @PathVariable String id,
            @RequestBody Team team) {
        return ResponseEntity.ok(teamService.updateTeam(id, team));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable String id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{teamId}/members")
    public ResponseEntity<Team> addMember(
            @PathVariable String teamId,
            @RequestBody Users member) {
        return ResponseEntity.ok(teamService.addMember(teamId, member));
    }

    @DeleteMapping("/{teamId}/members/{memberId}")
    public ResponseEntity<Team> removeMember(
            @PathVariable String teamId,
            @PathVariable String memberId) {
        return ResponseEntity.ok(teamService.removeMember(teamId, memberId));
    }

    @PutMapping("/{teamId}/captain")
    public ResponseEntity<Team> changeCaptain(
            @PathVariable String teamId,
            @RequestBody Users newCaptain) {
        return ResponseEntity.ok(teamService.changeCaptain(teamId, newCaptain));
    }

    @GetMapping("/captain/{captainId}")
    public ResponseEntity<List<Team>> getTeamsByCaptain(
            @PathVariable String captainId) {
        return ResponseEntity.ok(teamService.getTeamsByCaptain(captainId));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Team>> getTeamsByMember(
            @PathVariable String memberId) {
        return ResponseEntity.ok(teamService.getTeamsByMember(memberId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Team>> searchTeamsByName(
            @RequestParam String name) {
        return ResponseEntity.ok(teamService.searchTeamsByName(name));
    }
}