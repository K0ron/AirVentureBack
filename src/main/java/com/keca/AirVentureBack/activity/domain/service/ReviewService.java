package com.keca.AirVentureBack.activity.domain.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.keca.AirVentureBack.activity.domain.entity.Review;
import com.keca.AirVentureBack.activity.infrastructure.repository.ReviewRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getReviewsByActivityId(Long activityId) {
        return reviewRepository.findByActivityId(activityId);
    }

    public List<Review> getReviewsByUsersId(Long usersId) {
        return reviewRepository.findByUsersId(usersId);
    }

    public Review createReview(Review newReview) {
        return reviewRepository.save(newReview);
    }

    public Review updateReview(Review updateReview, Long id) {
        return reviewRepository.findById(id)
                .map(review -> {
                    review.setTitle(updateReview.getTitle());
                    review.setBody(updateReview.getBody());
                    return reviewRepository.save(review);
                })
                .orElseThrow();

    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

}
