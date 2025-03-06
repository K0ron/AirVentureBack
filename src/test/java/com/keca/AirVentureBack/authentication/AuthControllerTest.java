package com.keca.AirVentureBack.authentication;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.cache.ContextCache;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.keca.AirVentureBack.authentication.application.AuthenticationController;
import com.keca.AirVentureBack.authentication.domain.service.JwtTokenService;
import com.keca.AirVentureBack.authentication.domain.service.UserDetailsServiceImpl;
import com.keca.AirVentureBack.authentication.domain.service.UserLoginService;
import com.keca.AirVentureBack.authentication.infrastructure.repository.UserRepository;
import com.keca.AirVentureBack.user.domain.entity.User;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContextCache contextCache;


    @MockBean
    private UserLoginService userLoginService;

    @MockBean
    private JwtTokenService jwtTokenService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void cleanCache() {
        contextCache.clear();
    }

    @BeforeEach
    public void setup() throws Exception {
        // Créer un utilisateur pour les tests sans manipuler les variables d'environnement ici
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("email", "john@gmail.com");
        jsonUser.put("password", "Johndoe/00");
        jsonUser.put("lastName", "Doe");
        jsonUser.put("firstName", "John");
        jsonUser.put("role", "USER");

        // Enregistrer l'utilisateur via le contrôleur, directement avant chaque test
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .content(jsonUser.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testRegister() throws Exception {
        // Créer un nouvel utilisateur à enregistrer
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("lastName", "Lebron");
        jsonUser.put("firstName", "James");
        jsonUser.put("password", "Lakers/23");
        jsonUser.put("email", "lebron@gmail.com");
        jsonUser.put("role", "PROFESIONAL");

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .content(jsonUser.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Lebron"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("James"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("lebron@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("PROFESIONAL"));
    }

    @Test
    public void testLogin() throws Exception {
        // Test de connexion avec des informations valides
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
        // Test de connexion avec des informations invalides
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("email", "john@gmail.com");
        jsonUser.put("password", "wrongPassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .content(jsonUser.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.cookie().doesNotExist("token"));
    }

    @AfterEach
    public void cleanup() {
        // Nettoyage après chaque test : supprime l'utilisateur créé
        User user = userRepository.findByEmail("john@gmail.com");
        if (user != null) {
            userRepository.delete(user);
        }

        // Optionnel : Ajoutez des nettoyages supplémentaires si nécessaire pour d'autres utilisateurs
    }
}
