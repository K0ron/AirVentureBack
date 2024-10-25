package com.keca.AirVentureBack.reservation.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keca.AirVentureBack.reservation.domain.entity.PreReservation;

public interface PreReservationRepository extends JpaRepository<PreReservation, Long> {

}
