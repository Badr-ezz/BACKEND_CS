package com.example.springMongodb.service.activity;

import com.example.springMongodb.dto.ParticipantRequest;
import com.example.springMongodb.model.Activity;
import com.example.springMongodb.model.Team;
import com.example.springMongodb.model.Users;
import com.example.springMongodb.repository.ActivityRepo;
import com.example.springMongodb.repository.TeamRepo;
import com.example.springMongodb.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepo activityRepository;
    private final TeamRepo teamRepository;

    @Autowired
    public ActivityServiceImpl(ActivityRepo activityRepository, TeamRepo teamRepository) {
        this.activityRepository = activityRepository;
        this.teamRepository = teamRepository;
    }

    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Activity getActivityById(String id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
    }

    public List<Activity> getActivitiesByType(String type) {
        return activityRepository.findByType(type);
    }

    public List<Activity> getActivitiesBySport(String sport) {
        return activityRepository.findBySport(sport);
    }



//    @Override
//    public List<Activity> getActivitiesByDateRange(LocalDate start, LocalDate end) {
//        return activityRepository.findByDateBetween(start, end);
//    }
//
//    @Override
//    public List<Activity> getActivitiesByLocation(String localisation) {
//        return activityRepository.findByLocalisation(localisation);
//    }

    public Activity updateActivity(String id, Activity updatedActivity) {
        Activity existing = getActivityById(id);
        updatedActivity.setId(existing.getId());
        return activityRepository.save(updatedActivity);
    }

    public void deleteActivity(String id) {
        activityRepository.deleteById(id);
    }

    public Activity addParticipant(String activityId, ParticipantRequest request) {
        // Fetch the activity and explicitly initialize the participant lists
        Activity activity = getActivityById(activityId);

        // Force initialization of lazy-loaded collections
        activity.getTeamParticipants().size();  // This forces loading of the collection
        activity.getIndividualParticipants().size();  // This forces loading of the collection

        if ("TEAM".equals(request.getType())) {
            // Handle team addition
            Team team = request.getTeam();
            if (team == null) {
                throw new IllegalArgumentException("Team cannot be null");
            }

            // Save the team first to ensure it has an ID
            Team newTeam = teamRepository.save(team);

            boolean teamExists = activity.getTeamParticipants().stream()
                    .filter(Objects::nonNull)
                    .anyMatch(t -> t.getId() != null && t.getId().equals(newTeam.getId()));

            if (!teamExists) {
                activity.getTeamParticipants().add(newTeam);
                activity.setNbrCurrentTeam(activity.getNbrCurrentTeam() + 1);

                if (activity.getNbrCurrentTeam() >= activity.getNbrTeams()) {
                    activity.setIsTournamentFull(true);
                }
            }
        }
        else if ("INDIVIDUAL".equals(request.getType())) {
            Users participant = request.getParticipant();
            if (participant == null || participant.getId() == null) {
                throw new IllegalArgumentException("Participant or participant ID cannot be null");
            }

            boolean participantExists = activity.getIndividualParticipants().stream()
                    .filter(Objects::nonNull)
                    .anyMatch(p -> p.getId() != null && p.getId().equals(participant.getId()));

            if (!participantExists) {
                activity.getIndividualParticipants().add(participant);
                activity.setNbrCurrentParticipants(activity.getNbrCurrentParticipants() + 1);

                if (activity.getNbrCurrentParticipants() >= activity.getNbrParticipants()) {
                    activity.setIsTournamentFull(true);
                }
            }
        }

        // Save the updated activity
        return activityRepository.save(activity);
    }
    public Activity removeParticipant(String activityId, Object participant) {
        Activity activity = getActivityById(activityId);
        if (activity.getTeamParticipants() == null) {
            activity.getIndividualParticipants().remove( (Users) participant);
        }else {
            activity.getTeamParticipants().remove( (Team) participant);
        }
        return activityRepository.save(activity);
    }

    @Override
    public Boolean isTournamentFull(String activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new RuntimeException("Activity not found"));
//        return ( activity.getNbrTeams() == activity.getNbrCurrentTeam() )
//                ||  (activity.getNbrParticipants() == activity.getNbrCurrentParticipants() ) ;
        return activity.getIsTournamentFull();
    }

//    @Override
//    public List<Object> getActivityParticipants(String activityId) {
//        Activity searchActivity = activityRepository.findById(activityId)
//                .orElseThrow(() -> new RuntimeException("Activity not found"));
//
//        return searchActivity.getParticipants();
//    }

    @Override
    public boolean setTournamentFull(String activityId) {
        Activity searchActivity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
        searchActivity.setIsTournamentFull(true);
        return searchActivity.getIsTournamentFull();
    }
}