package com.keca.AirVentureBack.activity.application;

import com.keca.AirVentureBack.activity.domain.entity.Activity;
import com.keca.AirVentureBack.activity.domain.service.ActivityService;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/activities")
    List<Activity> getAll() {
        return activityService.getAllActivities();
    }

    @GetMapping("/activity/{id}")
    Activity getOne(@PathVariable UUID id) {
        return activityService.getOneActivity(id);
    }

    @PostMapping("/activity")
    public ResponseEntity<?> creatActivity(@RequestBody Activity newActivity) throws Exception {
        try {
            return ResponseEntity.status(201).body(activityService.createActivity(newActivity));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("activity/{id}")
    Activity update(@RequestBody Activity newActivity, @PathVariable UUID id) {
        return activityService.updateActivity(newActivity, id);
    }

    @DeleteMapping("/activity/{id}")
    void delete(@PathVariable UUID id) {
        activityService.deleteActivity(id);
    }

}
