package com.keca.AirVentureBack.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import com.keca.AirVentureBack.activity.domain.entity.Activity;
import com.keca.AirVentureBack.reservation.domain.entity.FinalReservation;
import com.keca.AirVentureBack.user.domain.entity.User;


public class FinalReservationTest {

    private FinalReservation finalReservation;

    @BeforeEach
    public void setUp() {
        finalReservation = new FinalReservation();
    }

    @Test
    public void testPreReservationId() {
        Long id = 1L;
        finalReservation.setId(id);
        assertEquals(id, finalReservation.getId());
    }

    @Test
    public void testPreReservationReservedAt() {
        Date reservedAt = new Date();
        finalReservation.setReservedAt(reservedAt);
        assertEquals(reservedAt, finalReservation.getReservedAt());
    }

    @Test
    public void testPreReservationTotalPrice() {
        BigDecimal totalPrice = new BigDecimal("500");
        finalReservation.setTotalPrice(totalPrice);
        assertEquals(totalPrice, finalReservation.getTotalPrice());
    }

    @Test
    public void testPreReservationUser() {
        Set<User> users = new HashSet<>();
        User user1 = new User();
        User user2 = new User();
        users.add(user1);
        users.add(user2);

        finalReservation.setUsers(users);
        assertNotNull(finalReservation.getUsers());
        assertEquals(2, finalReservation.getUsers().size());
    }

    @Test
    public void testPreReservationActivity() {
        Set<Activity> activities = new HashSet<>();
        Activity activity1 = new Activity();
        Activity activity2 = new Activity();
        activities.add(activity1);
        activities.add(activity2);

        finalReservation.setActivities(activities);
        assertNotNull(finalReservation.getActivities());
        assertEquals(2, finalReservation.getActivities().size());
    }

}
