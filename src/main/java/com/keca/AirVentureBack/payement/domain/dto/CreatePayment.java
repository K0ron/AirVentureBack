package com.keca.AirVentureBack.payement.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatePayment {

    @JsonProperty("items")
    CreatePaymentItem[] items;

    public CreatePaymentItem[] getItems() {
        return items;
    }

}
