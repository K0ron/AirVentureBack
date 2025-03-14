package com.keca.AirVentureBack.reservation.domain.entity;


import java.math.BigDecimal;
import java.util.*;

import com.keca.AirVentureBack.activity.domain.entity.Activity;
import com.keca.AirVentureBack.user.domain.entity.User;

import jakarta.persistence.*;

@Entity
@Table(name = "pre_reservation")
public class PreReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reserved_at", nullable = false)
    private Date reservedAt = new Date();

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "participants", nullable = false)
    private int participants;

    @Column(name = "date_of_activity", nullable = false)
    private Date dateOfActivity = new Date();

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany(mappedBy = "preReservations")
    private Set<User> users = new HashSet<>();

    @ManyToMany(mappedBy = "preReservations")
    private Set<Activity> activities = new HashSet<>();

    public enum Status {

        PENDING,
        CANCELLED,
        EXPIRED

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getReservedAt() {
        return reservedAt;
    }

    public void setReservedAt(Date reservedAt) {
        this.reservedAt = reservedAt;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public Date getDateOfActivity() {
        return dateOfActivity;
    }

    public void setDateOfActivity(Date dateOfActivity) {
        this.dateOfActivity = dateOfActivity;
    }

    

    

}
