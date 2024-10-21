package com.keca.AirVentureBack.activity.infrastructure.repository;

import com.keca.AirVentureBack.activity.domain.entity.Activity;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID> {

}
