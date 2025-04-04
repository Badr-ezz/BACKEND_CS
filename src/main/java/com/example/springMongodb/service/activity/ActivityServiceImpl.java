package com.example.springMongodb.service.activity;

import com.example.springMongodb.model.Activity;
import com.example.springMongodb.model.Team;
import com.example.springMongodb.model.Users;
import com.example.springMongodb.repository.ActivityRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepo activityRepository;

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

    public Activity addParticipant(String activityId, Object participant) {
        Activity activity = getActivityById(activityId);
        if (activity.getTeamParticipants() == null) {
            activity.getIndividualParticipants().add( (Users) participant);
        } else {
            activity.getTeamParticipants().add( (Team) participant);
        }
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