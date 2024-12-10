package com.keca.AirVentureBack.activity.domain.entity;

import jakarta.persistence.*;
import java.util.*;

import com.keca.AirVentureBack.reservation.domain.entity.FinalReservation;
import com.keca.AirVentureBack.reservation.domain.entity.PreReservation;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "adress", nullable = false)
    private String adress;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "price", nullable = false)
    private Integer price;

    @ElementCollection
    @CollectionTable(name = "activity_pictures", joinColumns = @JoinColumn(name = "activity_id"))
    private List<String> pictures = new ArrayList<>();

    @Column(name = "max_participants", nullable = false)
    private Integer maxParticipants;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToMany
    @JoinTable(name = "activity_pre_reservation", joinColumns = @JoinColumn(name = "activity_id"), inverseJoinColumns = @JoinColumn(name = "pre_reservation_id"))
    private Set<PreReservation> preReservations = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "activity_final_reservation", joinColumns = @JoinColumn(name = "activity_id"), inverseJoinColumns = @JoinColumn(name = "final_reservation_id"))
    private Set<FinalReservation> finalReservations = new HashSet<>();

    @OneToMany(mappedBy = "activity")
    private Set<Review> reviews = new HashSet<>();

    public enum Category {
        SPORT,
        EXTERIEUR,
        DECOUVERTE
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

}
