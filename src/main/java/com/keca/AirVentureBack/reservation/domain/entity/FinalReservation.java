package com.keca.AirVentureBack.reservation.domain.entity;

import jakarta.persistence.*;
import java.util.*;

import org.hibernate.type.descriptor.java.BigDecimalJavaType;

import com.keca.AirVentureBack.user.domain.entity.User;
import com.keca.AirVentureBack.activity.domain.entity.Activity;

@Entity
@Table(name = "final_reservation")
public class FinalReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "reserved_at", nullable = false)
    private Date reservedAt = new Date();

    @Column(name = "total_price", nullable = false)
    private BigDecimalJavaType totalPrice;

    @Column(name = "payement_date", nullable = false)
    private Date payementDate = new Date();

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getReservedAt() {
        return reservedAt;
    }

    public void setReservedAt(Date reservedAt) {
        this.reservedAt = reservedAt;
    }

    public BigDecimalJavaType getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimalJavaType totalPrice) {
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

}
