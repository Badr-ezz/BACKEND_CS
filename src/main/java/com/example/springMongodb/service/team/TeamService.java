package com.example.springMongodb.service.team;

import com.example.springMongodb.model.Team;
import com.example.springMongodb.model.Users;

import java.util.List;

public interface TeamService {
    Team createTeam(Team team, String idActivity);
    Team getTeamById(String id);
    List<Team> getAllTeams();
    Team updateTeam(String id, Team team);
    void deleteTeam(String id);

    Team addMember(String teamId, Users member);
    Team removeMember(String teamId, String memberId);
    Team changeCaptain(String teamId, Users newCaptain);

    List<Team> getTeamsByCaptain(String captainId);
    List<Team> getTeamsByMember(String memberId);
    List<Team> searchTeamsByName(String name);
}