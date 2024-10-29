package com.keca.AirVentureBack.user.domain.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

import com.keca.AirVentureBack.activity.domain.entity.Review;
import com.keca.AirVentureBack.reservation.domain.entity.FinalReservation;
import com.keca.AirVentureBack.reservation.domain.entity.PreReservation;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    @JoinTable(name = "users_pre_reservation", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "pre_reservation_id"))
    private Set<PreReservation> preReservations = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "users_final_reservation", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "final_reservation_id"))
    private Set<FinalReservation> finalReservations = new HashSet<>();

    @OneToMany(mappedBy = "users")
    private Set<Review> reviews = new HashSet<>();

    public enum Role {
        PROFESIONAL,
        PARTICULAR
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<PreReservation> getPreReservations() {
        return preReservations;
    }

    public void setPreReservations(Set<PreReservation> preReservations) {
        this.preReservations = preReservations;
    }

    public Set<FinalReservation> getFinalReservations() {
        return finalReservations;
    }

    public void setFinalReservations(Set<FinalReservation> finalReservations) {
        this.finalReservations = finalReservations;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

}
