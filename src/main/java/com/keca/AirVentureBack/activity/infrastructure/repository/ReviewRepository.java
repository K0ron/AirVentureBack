package com.keca.AirVentureBack.activity.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.keca.AirVentureBack.activity.domain.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    List<Review> findByActivityId(UUID activityId);

    List<Review> findByUserId(UUID userId);

}
