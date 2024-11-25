package com.keca.AirVentureBack.payement.domain.dto;

public class CreatePaymentResponse {

    private String clientSecret;
    private String dpmCheckerLink;

    public CreatePaymentResponse(String clientSecret, String transactionId) {
        this.clientSecret = clientSecret;
        // [DEV]: For demo purposes only, you should avoid exposing the PaymentIntent ID
        // in the client-side code.
        this.dpmCheckerLink = "https://dashboard.stripe.com/settings/payment_methods/review?transaction_id="
                + transactionId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getDpmCheckerLink() {
        return dpmCheckerLink;
    }

    public void setDpmCheckerLink(String dpmCheckerLink) {
        this.dpmCheckerLink = dpmCheckerLink;
    }

}
