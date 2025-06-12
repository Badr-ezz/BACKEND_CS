package com.example.springMongodb.service.match;

import com.example.springMongodb.model.Activity;
import com.example.springMongodb.model.GoalEvent;
import com.example.springMongodb.model.Match;
import com.example.springMongodb.model.Team;
import com.example.springMongodb.model.TeamStanding;
import com.example.springMongodb.model.TopScorer;
import com.example.springMongodb.repository.MatchRepo;
import com.example.springMongodb.repository.GoalEventRepo;
import com.example.springMongodb.service.activity.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchRepo matchRepo;

    @Autowired
    private GoalEventRepo goalEventRepo;

    @Autowired
    private ActivityService activityService;

    @Override
    public Match getMatchById(String id) {
        return matchRepo.findById(id).orElse(null);
    }

    @Override
    public List<Match> getMatchesByActivityId(String activityId) {
        return matchRepo.findByActivityId(activityId);
    }

    @Override
    public List<Match> getMatchesByActivityIdAndGameweek(String activityId, int gameweek) {
        return matchRepo.findByActivityIdAndGameweek(activityId, gameweek);
    }

    @Override
    public List<Match> generateMatchesForGameweek(String activityId, int gameweek) {
        // Validate gameweek
        if (gameweek < 1 || gameweek > 3) {
            throw new IllegalArgumentException("Gameweek must be between 1 and 3");
        }

        // Check if matches already exist for this gameweek
        List<Match> existingMatches = matchRepo.findByActivityIdAndGameweek(activityId, gameweek);
        if (!existingMatches.isEmpty()) {
            throw new IllegalStateException("Matches already exist for gameweek " + gameweek);
        }

        // Get the activity
        Activity activity = activityService.getActivityById(activityId);
        if (activity == null) {
            throw new IllegalArgumentException("Activity not found");
        }

        // Validate activity type and sport
        if (!"tournament".equals(activity.getType()) || !"football".equals(activity.getSport())  ) {
            throw new IllegalArgumentException("Activity must be a football tournament");
        }

        // Validate number of teams
        List<Team> teams = activity.getTeamParticipants();
        if (teams == null || teams.size() != 6) {
            throw new IllegalArgumentException("Tournament must have exactly 6 teams");
        }

        // Generate matches based on round-robin algorithm
        List<Match> matches = new ArrayList<>();
        LocalDate matchDate = activity.getStartingDate();
        String location = activity.getLocalisation();

        // Round-robin algorithm for 6 teams
        // For each gameweek, we create 3 matches with different team pairings
        switch (gameweek) {
            case 1:
                // Gameweek 1: A vs B, C vs D, E vs F
                matches.add(new Match(activityId, gameweek, teams.get(0), teams.get(1), matchDate, location));
                matches.add(new Match(activityId, gameweek, teams.get(2), teams.get(3), matchDate, location));
                matches.add(new Match(activityId, gameweek, teams.get(4), teams.get(5), matchDate, location));
                break;
            case 2:
                // Gameweek 2: A vs C, B vs E, D vs F
                matches.add(new Match(activityId, gameweek, teams.get(0), teams.get(2), matchDate, location));
                matches.add(new Match(activityId, gameweek, teams.get(1), teams.get(4), matchDate, location));
                matches.add(new Match(activityId, gameweek, teams.get(3), teams.get(5), matchDate, location));
                break;
            case 3:
                // Gameweek 3: A vs D, B vs F, C vs E
                matches.add(new Match(activityId, gameweek, teams.get(0), teams.get(3), matchDate, location));
                matches.add(new Match(activityId, gameweek, teams.get(1), teams.get(5), matchDate, location));
                matches.add(new Match(activityId, gameweek, teams.get(2), teams.get(4), matchDate, location));
                break;
        }

        // Save all matches
        return matchRepo.saveAll(matches);
    }

    @Override
    public Match updateMatch(Match match) {
        // Validate match
        if (match.getId() == null) {
            throw new IllegalArgumentException("Match ID cannot be null");
        }

        // Check if match exists
        Match existingMatch = matchRepo.findById(match.getId()).orElse(null);
        if (existingMatch == null) {
            throw new IllegalArgumentException("Match not found");
        }

        // Update match
        return matchRepo.save(match);
    }

    @Override
    public void deleteMatch(String id) {
        matchRepo.deleteById(id);
    }

    @Override
    public List<TeamStanding> getStandings(String activityId) {
        // Get all matches for the activity
        List<Match> matches = matchRepo.findByActivityId(activityId);

        // Get the activity to get all teams
        Activity activity = activityService.getActivityById(activityId);
        if (activity == null) {
            throw new IllegalArgumentException("Activity not found");
        }

        // Initialize standings for all teams
        Map<String, TeamStanding> standingsMap = new HashMap<>();
        for (Team team : activity.getTeamParticipants()) {
            standingsMap.put(team.getId(), new TeamStanding(team));
        }

        // Process played matches
        for (Match match : matches) {
            if ("played".equals(match.getStatus()) && match.getScoreTeamA() != null && match.getScoreTeamB() != null) {
                // Update team A standing
                TeamStanding teamAStanding = standingsMap.get(match.getTeamA().getId());
                teamAStanding.addMatch(match.getScoreTeamA(), match.getScoreTeamB());

                // Update team B standing
                TeamStanding teamBStanding = standingsMap.get(match.getTeamB().getId());
                teamBStanding.addMatch(match.getScoreTeamB(), match.getScoreTeamA());
            }
        }

        // Convert map to list and sort
        List<TeamStanding> standings = new ArrayList<>(standingsMap.values());
        standings.sort((a, b) -> {
            // Sort by points (descending)
            int pointsCompare = Integer.compare(b.getPoints(), a.getPoints());
            if (pointsCompare != 0) return pointsCompare;

            // If points are equal, sort by goal difference (descending)
            int goalDiffCompare = Integer.compare(b.getGoalDifference(), a.getGoalDifference());
            if (goalDiffCompare != 0) return goalDiffCompare;

            // If goal difference is equal, sort by goals for (descending)
            return Integer.compare(b.getGoalsFor(), a.getGoalsFor());
        });

        return standings;
    }

    @Override
    public List<TopScorer> getTopScorers(String activityId) {
        // Get all matches for the activity
        List<Match> matches = matchRepo.findByActivityId(activityId);

        // Create a map to store unique goal scorers with their total goals
        Map<String, TopScorer> scorersMap = new HashMap<>();

        // Process all matches to collect goal events
        for (Match match : matches) {
            if ("played".equals(match.getStatus()) && match.getGoalEvents() != null) {
                for (GoalEvent event : match.getGoalEvents()) {
                    // Skip own goals for top scorers
                    if (event.isOwnGoal()) {
                        continue;
                    }

                    // If scorerId is empty but scorerName is not, we'll use the name as a key
                    String scorerId = event.getScorerId();
                    String scorerName = event.getScorerName();
                    String teamId = event.getTeamId();

                    // Skip if both scorerId and scorerName are empty
                    if ((scorerId == null || scorerId.isEmpty()) &&
                            (scorerName == null || scorerName.isEmpty())) {
                        continue;
                    }

                    // Use scorerId as key if available, otherwise use scorerName + teamId
                    String key = (scorerId != null && !scorerId.isEmpty()) ?
                            scorerId :
                            scorerName + "_" + teamId;

                    // Find the team for this scorer
                    Team team = null;
                    if (match.getTeamA().getId().equals(teamId)) {
                        team = match.getTeamA();
                    } else if (match.getTeamB().getId().equals(teamId)) {
                        team = match.getTeamB();
                    }

                    if (!scorersMap.containsKey(key)) {
                        // Create new top scorer entry
                        scorersMap.put(key, new TopScorer(
                                scorerId != null && !scorerId.isEmpty() ? scorerId : key,
                                scorerName,
                                team,
                                1
                        ));
                    } else {
                        // Update existing top scorer entry
                        TopScorer scorer = scorersMap.get(key);
                        scorer.addGoal();
                    }
                }
            }
        }

        // Convert map to list and sort by goals (descending)
        List<TopScorer> topScorers = new ArrayList<>(scorersMap.values());
        topScorers.sort((a, b) -> Integer.compare(b.getGoals(), a.getGoals()));

        return topScorers;
    }
}
