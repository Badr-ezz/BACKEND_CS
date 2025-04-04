package com.example.springMongodb.controller;

import com.example.springMongodb.model.Activity;
import com.example.springMongodb.service.activity.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin("*")
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        return ResponseEntity.ok(activityService.createActivity(activity));
    }

    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        return ResponseEntity.ok(activityService.getAllActivities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable String id) {
        return ResponseEntity.ok(activityService.getActivityById(id));
    }

//    @GetMapping("/type/{type}")
//    public ResponseEntity<List<Activity>> getActivitiesByType(@PathVariable String type) {
//        return ResponseEntity.ok(activityService.getActivitiesByType(type));
//    }
//
//    @GetMapping("/sport/{sport}")
//    public ResponseEntity<List<Activity>> getActivitiesBySport(@PathVariable String sport) {
//        return ResponseEntity.ok(activityService.getActivitiesBySport(sport));
//    }

    @GetMapping("/actifity/full/{id}")
    public ResponseEntity<Boolean> setTournamentFull(@PathVariable String id) {
        return ResponseEntity.ok(activityService.setTournamentFull(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(
            @PathVariable String id,
            @RequestBody Activity updatedActivity) {
        return ResponseEntity.ok(activityService.updateActivity(id, updatedActivity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable String id) {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{activityId}/participants")
    public ResponseEntity<Activity> addParticipant(
            @PathVariable String activityId,
            @RequestBody Object participant) {
        return ResponseEntity.ok(activityService.addParticipant(activityId, participant));
    }

    @DeleteMapping("/{activityId}/participants")
    public ResponseEntity<Activity> removeParticipant(
            @PathVariable String activityId,
            @RequestBody Object participant) {
        return ResponseEntity.ok(activityService.removeParticipant(activityId, participant));
    }
}