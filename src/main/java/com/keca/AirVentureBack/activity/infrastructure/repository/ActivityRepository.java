package com.keca.AirVentureBack.activity.infrastructure.repository;

import com.keca.AirVentureBack.activity.domain.entity.Activity;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query(value = "SELECT * FROM air_venture_docker.activity WHERE category = :category", nativeQuery = true)
    public List<Activity> findByCategory(@Param("category") String category);


}
