package com.keca.AirVentureBack.payement.application;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.keca.AirVentureBack.payement.domain.dto.CreatePayment;
import com.keca.AirVentureBack.payement.domain.dto.CreatePaymentItem;
import com.keca.AirVentureBack.payement.domain.dto.CreatePaymentResponse;
import com.keca.AirVentureBack.reservation.domain.entity.FinalReservation;
import com.keca.AirVentureBack.reservation.domain.entity.PreReservation;
import com.keca.AirVentureBack.reservation.domain.service.FinalReservationService;
import com.keca.AirVentureBack.reservation.domain.service.PreReservationService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

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

}
