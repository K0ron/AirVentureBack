package com.keca.AirVentureBack.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.*;

import com.keca.AirVentureBack.activity.domain.entity.Activity;
import com.keca.AirVentureBack.reservation.domain.entity.PreReservation;
import com.keca.AirVentureBack.user.domain.entity.User;

public class PreReservationTest {
    private PreReservation preReservation;

    @BeforeEach
    public void setUp() {
        preReservation = new PreReservation();
    }

    @Test
    public void testPreReservationId() {
        Long id = 1L;
        preReservation.setId(id);
        assertEquals(id, preReservation.getId());
    }

    @Test
    public void testPreReservationReservedAt() {
        Date reservedAt = new Date();
        preReservation.setReservedAt(reservedAt);
        assertEquals(reservedAt, preReservation.getReservedAt());
    }

    @Test
    public void testPreReservationExpirationDate() {
        Date expirationDate = new Date();
        preReservation.setExpirationDate(expirationDate);
        assertEquals(expirationDate, preReservation.getExpirationDate());
    }

    @Test
    public void testPreReservationTotalPrice() {
        BigDecimal totalPrice = new BigDecimal("500");
        preReservation.setTotalPrice(totalPrice);
        assertEquals(totalPrice, preReservation.getTotalPrice());
    }

    @Test
    public void testPreReservationUser() {
        Set<User> users = new HashSet<>();
        User user1 = new User();
        User user2 = new User();
        users.add(user1);
        users.add(user2);

        preReservation.setUsers(users);
        assertNotNull(preReservation.getUsers());
        assertEquals(2, preReservation.getUsers().size());
    }

    @Test
    public void testPreReservationActivity() {
        Set<Activity> activities = new HashSet<>();
        Activity activity1 = new Activity();
        Activity activity2 = new Activity();
        activities.add(activity1);
        activities.add(activity2);

        preReservation.setActivities(activities);
        assertNotNull(preReservation.getActivities());
        assertEquals(2, preReservation.getActivities().size());
    }

}
