package com.keca.AirVentureBack.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.http.cookie.Cookie;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.keca.AirVentureBack.TestSecurityConfig;
import com.keca.AirVentureBack.authentication.application.AuthenticationController;
import com.keca.AirVentureBack.authentication.domain.service.JwtTokenService;
import com.keca.AirVentureBack.authentication.domain.service.UserDetailsServiceImpl;
import com.keca.AirVentureBack.authentication.domain.service.UserLoginService;
import com.keca.AirVentureBack.authentication.domain.service.UserRegisterService;
import com.keca.AirVentureBack.user.domain.entity.User;

import io.github.cdimascio.dotenv.Dotenv;

import com.keca.AirVentureBack.authentication.infrastructure.repository.UserRepository;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserLoginService userLoginService;

    @MockBean
    private UserRegisterService userRegisterService;

    @MockBean
    private JwtTokenService jwtTokenService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
public void testDotenvLoading() {
    Dotenv dotenv = Dotenv.load();
    String secretKey = dotenv.get("SECRET_KEY");
    System.out.println("Secret key: " + secretKey);
    assertNotNull(secretKey, "The SECRET_KEY should be loaded from the .env file");
}


    @Test
    public void testRegister() throws Exception {
        // Tentative d'enregistrement d'un nouvel utilisateur
        String newUserJson = "{\n" +
                "  \"email\": \"jane.doe@example.com\",\n" +
                "  \"password\": \"StrongPass123!\",\n" +
                "  \"firstName\": \"Jane\",\n" +
                "  \"lastName\": \"Doe\",\n" +
                "  \"role\": \"PROFESIONAL\"\n" +
                "}";
    
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .content(newUserJson) // Corps de la requête avec l'utilisateur à créer
                .contentType(MediaType.APPLICATION_JSON)) // Type de contenu JSON
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated()); // Vérification du statut HTTP 201 - Created
                // .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("jane.doe@example.com")) // Vérification de l'email
                // .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Jane")) // Vérification du prénom
                // .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe")) // Vérification du nom
                // .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("PROFESIONAL")); // Vérification du rôle
    }

    @Test
    public void testRegisterWithMissingEmail() throws Exception {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("email", "");
        jsonUser.put("password", "Password123");
    
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .content(jsonUser.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Email is required"));
    }


    
    @Test
    public void testLogin() throws Exception {
        // Test de connexion avec un utilisateur existant dans la base de données
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("email", "john.doe@example.com"); // Utilisez l'email de l'utilisateur créé dans la BDD
        jsonUser.put("password", "Jokegepa/91120"); // Utilisez le mot de passe correspondant à l'utilisateur
    
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .content(jsonUser.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Vérifie que la réponse est 200 OK
                .andExpect(MockMvcResultMatchers.cookie().exists("token")) // Vérifie que le cookie "token" existe
                .andReturn();
    
        // Récupérer la réponse
        MockHttpServletResponse response = result.getResponse();
        System.out.println("Response: " + response.getContentAsString());
    
        // Vérifier le corps de la réponse (response body)
        String responseBody = response.getContentAsString();
        System.out.println("Response body: " + responseBody);
        assertTrue(responseBody.contains("id"), "Response body should contain 'id'");
        assertTrue(responseBody.contains("token"), "Response body should contain 'token'");
    
        // Vérifier le cookie "token"
        Cookie cookie = (Cookie) response.getCookie("token");
        assertNotNull(cookie, "Cookie 'token' should not be null");
        assertEquals("token", cookie.getName(), "Cookie name should be 'token'");
        assertNotNull(cookie.getValue(), "Cookie value should not be null");
    
        // Optionnel : Vérifier que le cookie contient la bonne valeur (ici, le token JWT)
        String token = cookie.getValue();
        System.out.println("TOKEN == "+token);
        assertTrue(token.startsWith("eyJhbGciOiJIUzI1NiJ9"), "Token should be a valid JWT");
    }
    
    



@Test
public void testLoginWithWrongCredentials() throws Exception {
    // Test de connexion avec des informations invalides
    JSONObject jsonUser = new JSONObject();
    jsonUser.put("email", "john.doe@example.com"); // Utilisez un email valide mais avec un mauvais mot de passe
    jsonUser.put("password", "wrongPassword"); // Utilisez un mot de passe incorrect

    mockMvc.perform(MockMvcRequestBuilders.post("/login")
            .content(jsonUser.toString())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()) // Vérifie que la réponse est 401 Unauthorized
            .andExpect(MockMvcResultMatchers.cookie().doesNotExist("token")); // Vérifie que le cookie "token" n'existe pas
            //.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid credentials")); // Vérifie le message d'erreur
}


    @Test
    public void testLoginWithMissingFields() throws Exception {
        // Test de connexion avec des informations manquantes
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("email", "john@gmail.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .content(jsonUser.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
                //.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Missing required fields"));
    }
}

