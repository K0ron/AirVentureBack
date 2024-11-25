package com.keca.AirVentureBack.payement.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatePaymentItem {

    @JsonProperty("id")
    String id;

    public String getId() {
        return id;
    }

    @JsonProperty("amount")
    Long amount;

    public Long getAmount() {
        return amount;
    }

}
