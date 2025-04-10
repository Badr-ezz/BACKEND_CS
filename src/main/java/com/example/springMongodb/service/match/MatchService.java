package com.example.springMongodb.service.match;

import com.example.springMongodb.model.Match;
import com.example.springMongodb.model.TeamStanding;
import com.example.springMongodb.model.TopScorer;

import java.util.List;
import java.util.Map;

public interface MatchService {
    Match getMatchById(String id);
    List<Match> getMatchesByActivityId(String activityId);
    List<Match> getMatchesByActivityIdAndGameweek(String activityId, int gameweek);
    List<Match> generateMatchesForGameweek(String activityId, int gameweek);
    Match updateMatch(Match match);
    void deleteMatch(String id);
    List<TeamStanding> getStandings(String activityId);
    List<TopScorer> getTopScorers(String activityId);
}
