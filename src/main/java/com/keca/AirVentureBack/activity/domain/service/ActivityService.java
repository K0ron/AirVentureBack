package com.keca.AirVentureBack.activity.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.keca.AirVentureBack.activity.domain.entity.Activity;
import com.keca.AirVentureBack.activity.infrastructure.repository.ActivityRepository;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Activity getOneActivity(UUID id) {
        return activityRepository.findById(id).orElseThrow();
    }

    public Activity createActivity(Activity newActivity) {
        return activityRepository.save(newActivity);
    }

    public Activity updateActivity(Activity newActivity, UUID id) {
        return activityRepository.findById(id)
                .map(activity -> {
                    activity.setName(newActivity.getName());
                    activity.setDescription(newActivity.getDescription());
                    activity.setDuration(newActivity.getDuration());
                    activity.setAdress(newActivity.getAdress());
                    activity.setCity(newActivity.getCity());
                    activity.setZipCode(newActivity.getZipCode());
                    activity.setPrice(newActivity.getPrice());
                    return activityRepository.save(activity);
                })
                .orElseThrow();
    }

    public void deleteActivity(UUID id) {
        activityRepository.deleteById(id);
    }

}
