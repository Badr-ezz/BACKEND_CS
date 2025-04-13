package com.example.springMongodb.service.Billard;

import com.example.springMongodb.model.BilliardTournament;
import com.example.springMongodb.repository.BilliardTournamentRepository;
import com.example.springMongodb.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BilliardTournamentService {

    @Autowired
    private BilliardTournamentRepository billiardTournamentRepository;

    public BilliardTournament createTournament(String activityId, List<Users> participants) {
        // Check if tournament already exists
        Optional<BilliardTournament> existingTournament = billiardTournamentRepository.findByActivityId(activityId);
        if (existingTournament.isPresent()) {
            billiardTournamentRepository.delete(existingTournament.get());
        }

        // Shuffle participants
        List<Users> shuffledParticipants = new ArrayList<>(participants);
        Collections.shuffle(shuffledParticipants);

        // Create tournament
        BilliardTournament tournament = new BilliardTournament();
        tournament.setActivityId(activityId);
        tournament.setStatus("in_progress");

        // Create quarter-finals (4 matches)
        List<BilliardTournament.Match> quarterFinals = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            BilliardTournament.Match match = new BilliardTournament.Match();
            if (shuffledParticipants.size() > i * 2) {
                match.setPlayer1(shuffledParticipants.get(i * 2));
            }
            if (shuffledParticipants.size() > i * 2 + 1) {
                match.setPlayer2(shuffledParticipants.get(i * 2 + 1));
            }
            quarterFinals.add(match);
        }
        tournament.setQuarterFinals(quarterFinals);

        // Initialize semi-finals (2 matches)
        List<BilliardTournament.Match> semiFinals = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            semiFinals.add(new BilliardTournament.Match());
        }
        tournament.setSemiFinals(semiFinals);

        // Initialize final match
        tournament.setFinalMatch(new BilliardTournament.Match());

        return billiardTournamentRepository.save(tournament);
    }

    public BilliardTournament getTournamentByActivityId(String activityId) {
        return billiardTournamentRepository.findByActivityId(activityId)
                .orElseThrow(() -> new RuntimeException("Billiard tournament not found for activity: " + activityId));
    }

    public BilliardTournament updateMatchResult(String activityId, String round, int matchIndex,
                                                Users winner, Integer player1Score, Integer player2Score) {
        BilliardTournament tournament = getTournamentByActivityId(activityId);

        if ("quarterFinals".equals(round)) {
            // Update quarter-final match
            BilliardTournament.Match match = tournament.getQuarterFinals().get(matchIndex);
            match.setWinner(winner);
            match.setPlayer1Score(player1Score);
            match.setPlayer2Score(player2Score);

            // Advance winner to semi-finals
            int semiIndex = matchIndex / 2;
            BilliardTournament.Match semiMatch = tournament.getSemiFinals().get(semiIndex);
            if (matchIndex % 2 == 0) {
                semiMatch.setPlayer1(winner);
            } else {
                semiMatch.setPlayer2(winner);
            }

        } else if ("semiFinals".equals(round)) {
            // Update semi-final match
            BilliardTournament.Match match = tournament.getSemiFinals().get(matchIndex);
            match.setWinner(winner);
            match.setPlayer1Score(player1Score);
            match.setPlayer2Score(player2Score);

            // Advance winner to final
            BilliardTournament.Match finalMatch = tournament.getFinalMatch();
            if (matchIndex == 0) {
                finalMatch.setPlayer1(winner);
            } else {
                finalMatch.setPlayer2(winner);
            }

        } else if ("final".equals(round)) {
            // Update final match
            BilliardTournament.Match match = tournament.getFinalMatch();
            match.setWinner(winner);
            match.setPlayer1Score(player1Score);
            match.setPlayer2Score(player2Score);

            // Set champion
            tournament.setChampion(winner);
            tournament.setStatus("completed");
        }

        return billiardTournamentRepository.save(tournament);
    }

    public void deleteTournament(String activityId) {
        billiardTournamentRepository.deleteByActivityId(activityId);
    }
}
