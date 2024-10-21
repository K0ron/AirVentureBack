package com.keca.AirVentureBack.reservation.application;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.keca.AirVentureBack.reservation.domain.entity.FinalReservation;
import com.keca.AirVentureBack.reservation.domain.service.FinalReservationService;

@RestController
public class FinalReservationController {
    private final FinalReservationService finalReservationService;

    public FinalReservationController(FinalReservationService finalReservationService) {
        this.finalReservationService = finalReservationService;
    }

    @GetMapping("/finalReservations")
    List<FinalReservation> getAllFinalReservation() {
        return finalReservationService.getAllFinalReservation();
    }

    @GetMapping("/finalReservation/{id}")
    FinalReservation getOneFinalReservation(@PathVariable UUID id) {
        return finalReservationService.getOneFinalReservation(id);
    }

    @PostMapping("/finalReservation")
    public ResponseEntity<?> createFinalReservation(@RequestBody FinalReservation newFinalReservation) {
        try {
            return ResponseEntity.ok().body(finalReservationService.createFinalRerservation(newFinalReservation));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/finalReservation/{id}")
    FinalReservation updateFinalReservation(@RequestBody FinalReservation updateFinalReservation,
            @PathVariable UUID id) {
        return finalReservationService.updateFinalReservation(updateFinalReservation, id);
    }

    @DeleteMapping("/finalReservation/{id}")
    void deleteFinalReservation(@PathVariable UUID id) {
        finalReservationService.deleteFinalReservation(id);
    }

}
