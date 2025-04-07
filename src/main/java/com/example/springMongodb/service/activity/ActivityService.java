package com.example.springMongodb.service.activity;

import com.example.springMongodb.dto.ParticipantRequest;
import com.example.springMongodb.model.Activity;
import com.example.springMongodb.model.Team;
import com.example.springMongodb.model.Users;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface ActivityService {
    Activity createActivity(Activity activity);
    List<Activity> getAllActivities();
    Activity getActivityById(String id);
    Activity updateActivity(String id, Activity updatedActivity);
    void deleteActivity(String id);

    // can be rendered in the front using Filter method in the whole activities list
//    List<Activity> getActivitiesByType(String type);
//    List<Activity> getActivitiesBySport(String sport);


    Activity removeParticipant(String activityId, Object participant);
    Boolean isTournamentFull(String activityId);
    boolean setTournamentFull(String activityId);

    Activity addteam(String activityId, Team team);

    Activity addIndividuel(String activityId, Users user);
}