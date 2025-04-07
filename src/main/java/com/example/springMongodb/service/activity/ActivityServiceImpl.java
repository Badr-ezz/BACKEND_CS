package com.example.springMongodb.service.activity;

import com.example.springMongodb.dto.ParticipantRequest;
import com.example.springMongodb.model.Activity;
import com.example.springMongodb.model.Team;
import com.example.springMongodb.model.Users;
import com.example.springMongodb.repository.ActivityRepo;
import com.example.springMongodb.repository.TeamRepo;
import com.example.springMongodb.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepo activityRepository;
    private final TeamRepo teamRepository;
    private final UserRepo userRepository;

    @Autowired
    public ActivityServiceImpl(ActivityRepo activityRepository, TeamRepo teamRepository, UserRepo userRepository) {
        this.activityRepository = activityRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
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

    @Override
    public Activity addteam(String activityId, Team team) {
        Activity activity = getActivityById(activityId);

        System.out.println("team in the request : " + team);
        Team persisedTeam = teamRepository.save(team);
         persisedTeam = teamRepository.findById(persisedTeam.getId())
                .orElseThrow(() -> new RuntimeException("Team not found"));
        System.out.println(persisedTeam);

        List<Team> tempTeams = activity.getTeamParticipants();
        tempTeams.add(persisedTeam);
        activity.setTeamParticipants(tempTeams);
        activity.setNbrCurrentTeam(activity.getNbrCurrentTeam() + 1);
        if (activity.getNbrCurrentTeam() >= activity.getNbrTeams()) {
            activity.setIsTournamentFull(true);
        }
        return activityRepository.save(activity);
    }

    @Override
    public Activity addIndividuel(String activityId, Users user) {

        Activity activity = getActivityById(activityId);
        System.out.println("activity  id in the request : " + activityId);

        Users participant = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        


        boolean participantExists = activity.getIndividualParticipants().stream()
                .filter(Objects::nonNull)
                .anyMatch(p -> p.getId() != null && p.getId().equals(participant.getId()));

        if (!participantExists) {
            // for test
            List<Users> temp = activity.getIndividualParticipants();
            System.out.println("temp before inserting" + temp);

            temp.add(participant);
            System.out.println("temp after inserting " + temp);

            activity.setIndividualParticipants(temp);
            activity.setNbrCurrentParticipants(activity.getNbrCurrentParticipants() + 1);

            if (activity.getNbrCurrentParticipants() >= activity.getNbrParticipants()) {
                activity.setIsTournamentFull(true);
            }
        }

        return activityRepository.save(activity);
    }

    // In ActivityServiceImpl.java
    @Override
    public long countActivitiesByType(String type) {
        return activityRepository.countByType(type);
    }
}