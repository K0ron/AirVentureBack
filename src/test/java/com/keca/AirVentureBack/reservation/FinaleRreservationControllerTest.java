package com.keca.AirVentureBack.reservation;

import static org.mockito.Mockito.never;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ch.qos.logback.core.encoder.EchoEncoder;
import jakarta.servlet.http.Cookie;
import net.minidev.json.JSONObject;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class FinaleRreservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    public String getAuthenticationCookie() throws Exception {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("email", "john@gmail.com");
        jsonUser.put("password", "Johndoe/00");

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .content(jsonUser.toString())
                        .contentType(MediaType.APPLICATION_JSON));

        MvcResult result = resultActions.andReturn();
        return result.getResponse().getCookie("token").getValue();

    }

    @Test
    public void testGetFinalReservations() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/finalReservations")
                        .cookie(new Cookie("token", getAuthenticationCookie()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetOneFinalReservation() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/finalReservation/1")
                        .cookie(new Cookie("token", getAuthenticationCookie()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payementDate").value("2024-11-19T23:00:00.000+00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservedAt").value("2024-11-19T23:00:00.000+00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("PAID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice").value(100));
    }
}
