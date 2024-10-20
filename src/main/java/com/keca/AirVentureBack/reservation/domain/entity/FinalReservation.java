package com.keca.AirVentureBack.reservation.domain.entity;

import jakarta.persistence.*;
import java.util.*;

import org.hibernate.type.descriptor.java.BigDecimalJavaType;

public class FinalReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "reserved_at", nullable = false)
    private Date reservedAt = new Date();

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @Column(name = "total_price", nullable = false)
    private BigDecimalJavaType totalPrice;

    @Enumerated(EnumType.STRING)
    private Status status;

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

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BigDecimalJavaType getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimalJavaType totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
