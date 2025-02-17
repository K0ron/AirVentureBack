package com.keca.AirVentureBack.reservation.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.*;

import com.keca.AirVentureBack.user.domain.entity.User;
import com.keca.AirVentureBack.activity.domain.entity.Activity;

@Entity
@Table(name = "final_reservation")
public class FinalReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reserved_at", nullable = false)
    private Date reservedAt = new Date();

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "payement_date", nullable = false)
    private Date payementDate = new Date();

    @Column(name = "participants", nullable = false)
    private int participants;

    @Column(name = "date_of_activity", nullable = false)
    private Date dateOfActivity = new Date();

    @ManyToMany(mappedBy = "finalReservations")
    private Set<User> users = new HashSet<>();

    @ManyToMany(mappedBy = "finalReservations")
    private Set<Activity> activities = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {

        PAID,
        CANCELLED,

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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getPayementDate() {
        return payementDate;
    }

    public void setPayementDate(Date payementDate) {
        this.payementDate = payementDate;
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
