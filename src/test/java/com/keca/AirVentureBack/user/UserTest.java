package com.keca.AirVentureBack.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashSet;
import java.util.Set;

import com.keca.AirVentureBack.activity.domain.entity.Review;
import com.keca.AirVentureBack.reservation.domain.entity.FinalReservation;
import com.keca.AirVentureBack.reservation.domain.entity.PreReservation;
import com.keca.AirVentureBack.user.domain.entity.User;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void testUserId() {
        Long id = 1L;
        user.setId(id);
        assertEquals(id, user.getId());
    }

    @Test
    public void testUserFirstName() {
        String firstName = "Test firstName";
        user.setFirstName(firstName);
        assertEquals(firstName, user.getFirstName());
    }

    @Test
    public void testUserLastName() {
        String lastName = "Test lastName";
        user.setLastName(lastName);
        assertEquals(lastName, user.getLastName());
    }

    @Test
    public void testUserEmail() {
        String email = "Test email";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testUserPassword() {
        String password = "Test password";
        user.setPassword(password);
        assertEquals(password, user.getPassword());
    }

    @Test
    public void testUserPreReservation() {
        Set<PreReservation> preReservations = new HashSet<>();
        PreReservation preReservation1 = new PreReservation();
        PreReservation preReservation2 = new PreReservation();
        preReservations.add(preReservation1);
        preReservations.add(preReservation2);

        user.setPreReservations(preReservations);
        assertNotNull(user.getPreReservations());
        assertEquals(2, user.getPreReservations().size());

    }

    @Test
    public void testUserFinalReservation() {
        Set<FinalReservation> finalReservations = new HashSet<>();
        FinalReservation finalReservation1 = new FinalReservation();
        FinalReservation finalReservation2 = new FinalReservation();
        finalReservations.add(finalReservation1);
        finalReservations.add(finalReservation2);

        user.setFinalReservations(finalReservations);
        assertNotNull(user.getFinalReservations());
        assertEquals(2, user.getFinalReservations().size());
    }

    @Test
    public void testUserReview() {
        Set<Review> reviews = new HashSet<>();
        Review review1 = new Review();
        Review review2 = new Review();
        reviews.add(review1);
        reviews.add(review2);

        user.setReviews(reviews);
        assertNotNull(user.getReviews());
        assertEquals(2, user.getReviews().size());

    }

}
