package com.keca.AirVentureBack.activity.application;

import com.keca.AirVentureBack.activity.domain.entity.Activity;
import com.keca.AirVentureBack.activity.domain.service.ActivityService;
import com.keca.AirVentureBack.authentication.application.AuthenticationController;
import com.keca.AirVentureBack.upload.services.UploadScalewayService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ActivityController {

    private final ActivityService activityService;
    private final UploadScalewayService uploadScalewayService;

    public ActivityController(ActivityService activityService, UploadScalewayService uploadScalewayService) {
        this.activityService = activityService;
       this.uploadScalewayService = uploadScalewayService;
    }

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    @GetMapping("/activities")
    List<Activity> getAll() {
        return activityService.getAllActivities();
    }

    @GetMapping("/activity/{id}")
    Activity getOne(@PathVariable Long id) {
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
    Activity update(@RequestBody Activity newActivity, @PathVariable Long id) {
        return activityService.updateActivity(newActivity, id);
    }

    @DeleteMapping("/activity/{id}")
    void delete(@PathVariable Long id) {
        activityService.deleteActivity(id);
    }

    @PostMapping("/activity/{id}/upload-pictures")
        public ResponseEntity<List<String>> uploadActivityPictures(@RequestParam("files") MultipartFile[] files, @PathVariable("id") Long activityId) {

            try {
                List<MultipartFile> fileList = Arrays.asList(files);
                List<String> fileUrls = uploadScalewayService.uploadFiles(fileList, activityId, "activities");
                
                activityService.addActivityPictures(activityId, fileUrls);

                
                
                return ResponseEntity.ok(fileUrls);
                
                
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonList("Error: " + e.getMessage()));
            }
        }

    @GetMapping("activity/{id}/pictures")
    public ResponseEntity<List<String>> getActivityPictures(@PathVariable("id") Long activityId) {
        try {
            List<String> pictures = activityService.getActivityPictures(activityId);
            logger.info("Activity pictures: " + pictures);
            return ResponseEntity.ok(pictures);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Collections.singletonList("Activity not found: " + e.getMessage()));
        }

    }

    @GetMapping("activities/category/{category}")
    public List<Activity> getActivitiesByCategory(@PathVariable String category) {
        return activityService.getActivitiesByCategory(category);
    }


    @DeleteMapping("activity/{id}/delete-picture/{imageUrl}")
    public String deletePicture(@PathVariable String imageUrl, @PathVariable Long id) {
        try {

            String decodedUrl = URLDecoder.decode(imageUrl, StandardCharsets.UTF_8);

            String key = decodedUrl.replace("https://images-airventure.s3.fr-par.scw.cloud/", "");
            uploadScalewayService.deleteImage(key);

            activityService.removePictureFromActivity(id, decodedUrl);

            return "Image supprimée avec succès : " + key;
        } catch (Exception e) {
            return "Erreur lors de la suppression de l'image : " + e.getMessage();
        }
      
    }

}


