package com.keca.AirVentureBack.reservation.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keca.AirVentureBack.reservation.domain.entity.FinalReservation;

public interface FinalReservationRepository extends JpaRepository<FinalReservation, UUID> {

}
