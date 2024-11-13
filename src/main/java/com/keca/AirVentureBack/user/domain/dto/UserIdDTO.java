package com.keca.AirVentureBack.user.domain.dto;

import com.keca.AirVentureBack.reservation.domain.entity.FinalReservation;
import com.keca.AirVentureBack.reservation.domain.entity.PreReservation;

import java.util.HashSet;
import java.util.Set;

public class UserIdDTO {
    private Long id;
    private String  firstName;
    private String lastName;
    private String email;
    private Set<FinalReservation> finalReservations = new HashSet<>();
    private Set<PreReservation> preReservations = new HashSet<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<FinalReservation> getFinalReservations() {
        return finalReservations;
    }

    public void setFinalReservations(Set<FinalReservation> finalReservations) {
        this.finalReservations = finalReservations;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<PreReservation> getPreReservations() {
        return preReservations;
    }

    public void setPreReservations(Set<PreReservation> preReservations) {
        this.preReservations = preReservations;
    }
}
