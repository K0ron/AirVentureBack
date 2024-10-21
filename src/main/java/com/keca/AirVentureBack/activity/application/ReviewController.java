package com.keca.AirVentureBack.activity.application;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.keca.AirVentureBack.activity.domain.entity.Review;
import com.keca.AirVentureBack.activity.domain.service.ReviewService;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getReviewsByActivityId(@RequestParam UUID activityId) {
        try {
            List<Review> reviews = reviewService.getReviewsByActivityId(activityId);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/review")
    public ResponseEntity<List<Review>> getReviewByUserId(@RequestParam UUID userId) {
        try {
            List<Review> reviews = reviewService.getReviewsByUserId(userId);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/review/{id}")
    Review create(@RequestBody Review newReview, @PathVariable UUID id) {
        return reviewService.createReview(newReview);
    }

    @PutMapping("/review/{id}")
    Review update(@RequestBody Review updateReview, @PathVariable UUID id) {
        return reviewService.updateReview(updateReview, id);
    }

    @DeleteMapping("/review/{id}")
    void delete(@PathVariable UUID id) {
        reviewService.deleteReview(id);
    }

}
