package com.keca.AirVentureBack.authentication;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.keca.AirVentureBack.authentication.infrastructure.repository.UserRepository;
import com.keca.AirVentureBack.user.domain.entity.User;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testRegister() throws Exception {

        JSONObject jsonUser = new JSONObject();
        jsonUser.put("lastName", "Lebron");
        jsonUser.put("firstName", "James");
        jsonUser.put("password", "Lakers/23");
        jsonUser.put("email", "lebron@gmail.com");
        jsonUser.put("role", "PROFESIONAL");

        mockMvc
                .perform(MockMvcRequestBuilders.post("/register")
                        .content(jsonUser.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Lebron"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("James"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("lebron@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("PROFESIONAL"));
    }

    @AfterEach
    public void cleanup() {
        User user = userRepository.findByEmail("lebron@gmail.com");
        if (user != null) {
            userRepository.delete(user);
        }
    }

    @Test
    public void testLogin() throws Exception {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("email", "john@gmail.com");
        jsonUser.put("password", "Johndoe/00");

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .content(jsonUser.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.cookie().exists("token"))
                .andExpect(MockMvcResultMatchers.cookie().value("token", Matchers.notNullValue()))
                .andReturn();

    }

    @Test
    public void testLoginWithWrongCredentials() throws Exception {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("email", "john@gmail.com");
        jsonUser.put("password", "wrongPassword");

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .content(jsonUser.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.cookie().doesNotExist("token"))
                .andReturn();
    }

}
