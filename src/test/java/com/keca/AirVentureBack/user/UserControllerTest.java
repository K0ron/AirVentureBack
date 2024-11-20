package com.keca.AirVentureBack.user;

import org.json.JSONObject;
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

import jakarta.servlet.http.Cookie;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    public String generateAuthToken() throws Exception {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("email", "john@gmail.com");
        jsonUser.put("password", "Johndoe/00");

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .content(jsonUser.toString())
                        .contentType(MediaType.APPLICATION_JSON));

        MvcResult result = resultActions.andReturn();

        return result.getResponse().getCookie("token").getValue();
    }

    @Test
    public void testGetOne() throws Exception {
        Long id = 2L;
        mockMvc
                .perform(MockMvcRequestBuilders.get("/user/{id}", id)
                        .cookie(new Cookie("token", generateAuthToken()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
