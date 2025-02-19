package com.keca.AirVentureBack.payement.application;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.keca.AirVentureBack.payement.domain.dto.CreatePaymentResponse;
import com.keca.AirVentureBack.reservation.domain.entity.PreReservation;
import com.keca.AirVentureBack.reservation.domain.service.PreReservationService;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;

import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

@RestController
public class PaymentController {
    private PreReservationService preReservationService;

    public PaymentController(PreReservationService preReservationService) {
        this.preReservationService = preReservationService;
    }

    @PostMapping("/create-payement-intent")
    public ResponseEntity<?> createPayemenyIntent(@RequestBody Long id)
            throws StripeException {

        try {
            // Récupérer la clé API Stripe depuis l'environnement
            Stripe.apiKey = System.getenv("STRIPE_API_KEY");

            PreReservation preReservation = preReservationService.getOnePreReservation(id);

            if (preReservation == null) {
                throw new RuntimeException("Reservation not found");
            }

            // Calculer le montant total
            BigDecimal amount = preReservation.getTotalPrice();
            long amountInCents = amount.multiply(BigDecimal.valueOf(100)).longValue();

            // Créer les paramètres pour le PaymentIntent
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amountInCents)
                    .setCurrency("eur")
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                    .setEnabled(true)
                                    .build())
                    .build();

            // Créer le PaymentIntent
            PaymentIntent paymentIntent = PaymentIntent.create(params);

            // Retourner la réponse avec le client_secret et l'id du PaymentIntent
            return ResponseEntity.ok(new CreatePaymentResponse(paymentIntent.getClientSecret(), paymentIntent.getId()));
        } catch (StripeException e) {
            // Retourner une réponse appropriée en cas d'erreur Stripe
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur Stripe : " + e.getMessage());
        } catch (Exception e) {
            // Gérer toute autre erreur inattendue
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur est survenue : " + e.getMessage());
        }

    };

    @PostMapping("/create-checkout-session")
    public ResponseEntity<?> createCheckoutSession(@RequestBody Long id) {
        try {
            // Définition de la clé API Stripe
            Stripe.apiKey = System.getenv("STRIPE_API_KEY");

            // Récupération de la réservation
            PreReservation preReservation = preReservationService.getOnePreReservation(id);
            if (preReservation == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Réservation introuvable");
            }

            BigDecimal amount = preReservation.getTotalPrice();
            long amountInCents = amount.multiply(BigDecimal.valueOf(100)).longValue(); // Conversion en centimes

            // Création de la session Stripe Checkout
            SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:4200/success")
                .setCancelUrl("http://localhost:4200/cancel")
                .addLineItem(
                    SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("eur")
                                .setUnitAmount(amountInCents)  // Montant en centimes
                                .setProductData(
                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Réservation AirVenture")
                                        .build()
                                )
                                .build()
                        )
                        .build()
                )
                .build();

            // Création de la session Stripe Checkout
            Session session = Session.create(params);
            Map<String, String> jsonUrl = new HashMap<>();
            jsonUrl.put("url", session.getUrl());

            // Retourne l'URL de redirection vers la page de paiement Stripe
            return ResponseEntity.ok(jsonUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur lors de la création de la session : " + e.getMessage());
        }
    }

}
