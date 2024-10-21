package com.keca.AirVentureBack.reservation.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.keca.AirVentureBack.reservation.domain.entity.PreReservation;
import com.keca.AirVentureBack.reservation.infrastructure.repository.PreReservationRepository;

@Service
public class PreReservationService {
    private PreReservationRepository preReservationRepository;

    public PreReservationService(PreReservationRepository preReservationRepository) {
        this.preReservationRepository = preReservationRepository;
    }

    public List<PreReservation> getAllPreReservation() {
        return preReservationRepository.findAll();
    }

    public PreReservation getOnePreReservation(UUID id) {
        return preReservationRepository.findById(id)
                .orElseThrow();
    }

    public PreReservation createPreReservation(PreReservation newPreReservation) {
        return preReservationRepository.save(newPreReservation);
    }

    public PreReservation updatePreReservation(PreReservation updatePreReservation, UUID id) {
        return preReservationRepository.findById(id)
                .map(preReservation -> {
                    preReservation.setReservedAt(updatePreReservation.getReservedAt());
                    preReservation.setExpirationDate(updatePreReservation.getExpirationDate());
                    preReservation.setStatus(updatePreReservation.getStatus());
                    preReservation.setTotalPrice(updatePreReservation.getTotalPrice());
                    return preReservationRepository.save(preReservation);

                })
                .orElseThrow();
    }

    public void deletePreReservation(UUID id) {
        preReservationRepository.deleteById(id);
    }

}
