package com.example.springMongodb.service.team;

import com.example.springMongodb.model.Activity;
import com.example.springMongodb.model.Team;
import com.example.springMongodb.model.Users;
import com.example.springMongodb.repository.ActivityRepo;
import com.example.springMongodb.repository.TeamRepo;
import com.example.springMongodb.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepo teamRepository;
    private final ActivityRepo activityRepository;


    @Autowired
    public TeamServiceImpl(TeamRepo teamRepository, ActivityRepo activityRepository) {
        this.teamRepository = teamRepository;
        this.activityRepository = activityRepository;
    }



    @Override
    public Team createTeam(Team team, String idActivity) {
        // Save the team first
        Team newTeam = teamRepository.insert(team);

        // Fetch the activity
        Activity searchActivity = activityRepository.findById(idActivity)
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        // Check if teamParticipants is null and initialize if needed
        if (searchActivity.getTeamParticipants() == null) {
            searchActivity.setTeamParticipants(new ArrayList<>());
        }

        // Add the new team to the activity's teamParticipants
        searchActivity.getTeamParticipants().add(newTeam);
        searchActivity.setNbrCurrentTeam(searchActivity.getNbrCurrentTeam() + 1);

        // Check if the tournament is full
        if (searchActivity.getNbrCurrentTeam() == searchActivity.getNbrTeams()) {
            searchActivity.setIsTournamentFull(true);
        }

        // Save the updated activity
        activityRepository.save(searchActivity);

        return newTeam;
    }

    @Override
    public Team getTeamById(String id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));
    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Team updateTeam(String id, Team team) {
        Team existingTeam = getTeamById(id);
        team.setId(existingTeam.getId());
        return teamRepository.save(team);
    }

    @Override
    public void deleteTeam(String id) {
        teamRepository.deleteById(id);
    }

    @Override
    public Team addMember(String teamId, Users member) {
        Team team = getTeamById(teamId);
        team.getMembers().add(member);
        return teamRepository.save(team);
    }

    @Override
    public Team removeMember(String teamId, String memberId) {
        Team team = getTeamById(teamId);
        team.setMembers(
                team.getMembers().stream()
                        .filter(member -> !member.getId().equals(memberId))
                        .toList()
        );
        return teamRepository.save(team);
    }

    @Override
    public Team changeCaptain(String teamId, Users newCaptain) {
        Team team = getTeamById(teamId);
        team.setCaptain(newCaptain);
        return teamRepository.save(team);
    }

    @Override
    public List<Team> getTeamsByCaptain(String captainId) {
        return teamRepository.findByCaptainId(captainId);
    }

    @Override
    public List<Team> getTeamsByMember(String memberId) {
        return teamRepository.findByMembersId(memberId);
    }

    @Override
    public List<Team> searchTeamsByName(String name) {
        return teamRepository.findByNameContainingIgnoreCase(name);
    }
}