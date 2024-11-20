package com.keca.AirVentureBack.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.TestPropertySource;

import com.keca.AirVentureBack.activity.domain.entity.Activity;
import com.keca.AirVentureBack.activity.domain.entity.Review;
import com.keca.AirVentureBack.reservation.domain.entity.FinalReservation;
import com.keca.AirVentureBack.reservation.domain.entity.PreReservation;

// @SpringBootTest
// @AutoConfigureMockMvc
// @TestPropertySource(locations = "classpath:application-test.properties")
@ExtendWith(MockitoExtension.class)
public class ActivityTest {
    private Activity activity;

    @BeforeEach
    public void setUp() {
        activity = new Activity();
    }

    @Test
    public void testActivityId() {
        Long id = 1L;
        activity.setId(id);
        assertEquals(id, activity.getId());
    }

    @Test
    public void testActivityName() {
        String name = "New Activity";
        activity.setName(name);
        assertEquals(name, activity.getName());
    }

    @Test
    public void testActivityDescription() {
        String description = "This is the description of my activity";
        activity.setDescription(description);
        assertEquals(description, activity.getDescription());
    }

    @Test
    public void testActivityDuration() {
        Integer duration = 1;
        activity.setDuration(duration);
        assertEquals(duration, activity.getDuration());
    }

    @Test
    public void testActivityCity() {
        String city = "Biarritz";
        activity.setCity(city);
        assertEquals(city, activity.getCity());
    }

    @Test
    public void testActivityAdress() {
        String adress = "La grande plage";
        activity.setAdress(adress);
        assertEquals(adress, activity.getAdress());
    }

    @Test
    public void testActivityZipCode() {
        String zpiCode = "64200";
        activity.setZipCode(zpiCode);
        assertEquals(zpiCode, activity.getZipCode());
    }

    @Test
    public void testActivityPrice() {
        Integer price = 100;
        activity.setPrice(price);
        assertEquals(price, activity.getPrice());
    }

    @Test
    public void testActivityMaxParticipants() {
        Integer maxParticipants = 5;
        activity.setMaxParticipants(maxParticipants);
        assertEquals(maxParticipants, activity.getMaxParticipants());
    }

    @Test
    public void testActivityPreReservation() {
        Set<PreReservation> preReservations = new HashSet<>();
        PreReservation preReservation1 = new PreReservation();
        PreReservation preReservation2 = new PreReservation();
        preReservations.add(preReservation1);
        preReservations.add(preReservation2);

        activity.setPreReservations(preReservations);
        assertNotNull(activity.getPreReservations());
        assertEquals(2, activity.getPreReservations().size());
    }

    @Test
    public void testActivityFinalReservation() {
        Set<FinalReservation> finalReservations = new HashSet<>();
        FinalReservation finalReservation1 = new FinalReservation();
        FinalReservation finalReservation2 = new FinalReservation();
        finalReservations.add(finalReservation1);
        finalReservations.add(finalReservation2);

        activity.setFinalReservations(finalReservations);
        assertNotNull(activity.getFinalReservations());
        assertEquals(2, activity.getFinalReservations().size());
    }

    @Test
    public void testActivityReview() {
        Set<Review> reviews = new HashSet<>();
        Review review1 = new Review();
        Review review2 = new Review();
        reviews.add(review1);
        reviews.add(review2);

        activity.setReviews(reviews);
        assertNotNull(activity.getReviews());
        assertEquals(2, activity.getReviews().size());
    }

}
