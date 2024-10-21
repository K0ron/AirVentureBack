package com.keca.AirVentureBack.reservation.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.keca.AirVentureBack.reservation.domain.entity.FinalReservation;
import com.keca.AirVentureBack.reservation.infrastructure.repository.FinalReservationRepository;

@Service
public class FinalReservationService {

    private final FinalReservationRepository finalReservationRepository;

    public FinalReservationService(FinalReservationRepository finalReservationRepository) {
        this.finalReservationRepository = finalReservationRepository;
    }

    public List<FinalReservation> getAllFinalReservation() {
        return finalReservationRepository.findAll();
    }

    public FinalReservation getOneFinalReservation(UUID id) {
        return finalReservationRepository.findById(id)
                .orElseThrow();
    }

    public FinalReservation createFinalRerservation(FinalReservation newFinalReservation) {

        return finalReservationRepository.save(newFinalReservation);

    }

    public FinalReservation updateFinalReservation(FinalReservation updateFinalReservation, UUID id) {
        return finalReservationRepository.findById(id)
                .map(finalReservation -> {
                    finalReservation.setPayementDate(updateFinalReservation.getPayementDate());
                    finalReservation.setReservedAt(updateFinalReservation.getReservedAt());
                    finalReservation.setStatus(updateFinalReservation.getStatus());
                    finalReservation.setTotalPrice(updateFinalReservation.getTotalPrice());
                    return finalReservationRepository.save(finalReservation);
                })
                .orElseThrow();
    }

    public void deleteFinalReservation(UUID id) {
        finalReservationRepository.deleteById(id);
    }

}
