package com.keca.AirVentureBack.reservation.application;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.keca.AirVentureBack.reservation.domain.entity.PreReservation;
import com.keca.AirVentureBack.reservation.domain.service.PreReservationService;

@RestController
public class PreReservationController {
    private PreReservationService preReservationService;

    public PreReservationController(PreReservationService preReservationService) {
        this.preReservationService = preReservationService;
    }

    @GetMapping("/preReservations")
    List<PreReservation> getAllPreReservation() {
        return preReservationService.getAllPreReservation();
    }

    @GetMapping("/preReservation/{id}")
    PreReservation getOnePreReservation(@PathVariable Long id) {
        return preReservationService.getOnePreReservation(id);
    }

    @PostMapping("/preReservation")
    public ResponseEntity<?> createPreReservation(@RequestBody PreReservation newPreReservation) throws Exception {
        try {
            return ResponseEntity.ok().body(preReservationService.createPreReservation(newPreReservation));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/preReservation/{id}")
    PreReservation updatePreReservation(@RequestBody PreReservation updatePreReservation, @PathVariable Long id) {
        return preReservationService.updatePreReservation(updatePreReservation, id);
    }

    @DeleteMapping("/preReservation/{id}")
    public void deletePreReservation(@PathVariable Long id) {
        preReservationService.deletePreReservation(id);
    }

}
