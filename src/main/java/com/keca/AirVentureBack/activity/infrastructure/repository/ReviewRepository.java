package com.keca.AirVentureBack.activity.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.keca.AirVentureBack.activity.domain.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByActivityId(Long activityId);

    List<Review> findByUsersId(Long usersId);

}
