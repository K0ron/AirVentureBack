package com.keca.AirVentureBack.authentication.application;

import com.keca.AirVentureBack.authentication.domain.dto.CurrentUserDTO;
import com.keca.AirVentureBack.authentication.domain.dto.UserPrincipal;
import com.keca.AirVentureBack.authentication.domain.entity.Token;
import com.keca.AirVentureBack.authentication.domain.service.JwtTokenService;
import com.keca.AirVentureBack.authentication.domain.service.UserDetailsServiceImpl;
import com.keca.AirVentureBack.authentication.domain.service.UserLoginService;
import com.keca.AirVentureBack.authentication.domain.service.UserRegisterService;
import com.keca.AirVentureBack.user.domain.dto.UserDTO;
import com.keca.AirVentureBack.user.domain.dto.UserIdDTO;
import com.keca.AirVentureBack.user.domain.entity.User;

import jakarta.servlet.http.Cookie;

import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class AuthenticationController {
    private final UserLoginService userLoginService;
    private final UserRegisterService userRegisterService;
    private final JwtTokenService jwtTokenService;
    private final UserDetailsServiceImpl userDetailsService;

    AuthenticationController(
            UserLoginService userLoginService,
            UserRegisterService userRegisterService,
            JwtTokenService jwtTokenService,
            UserDetailsServiceImpl userDetailsService) {
        this.userLoginService = userLoginService;
        this.userRegisterService = userRegisterService;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
    }

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody User userBody) throws Exception {
        try {
            // Vérification des champs manquants dans le corps de la requête
            if (userBody.getEmail() == null || userBody.getEmail().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Email is required");
            }
            if (userBody.getPassword() == null || userBody.getPassword().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Password is required");
            }
    
            // Authentifier l'utilisateur
            User user = userLoginService.getUserEntityByEmail(userBody.getEmail());
            
            // Vérification des informations d'identification incorrectes
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid email or password");
            }
    
            // Vérifier que le mot de passe est correct
            if (!userLoginService.checkPassword(userBody.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid email or password");
            }
    
            // Générer le token pour l'utilisateur authentifié
            Token token = jwtTokenService.generateToken(userDetailsService.loadUserByUsername(user.getEmail()));
        
            // Créer le cookie avec la configuration appropriée
            Cookie jwtCookie = new Cookie("token", token.getToken());
            jwtCookie.setHttpOnly(true);  
            jwtCookie.setSecure(true);    
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(24 * 60 * 60); 
            jwtCookie.setDomain("localhost");
        
            // Créer un DTO pour l'utilisateur avec l'ID
            UserIdDTO userBodyDTO = new UserIdDTO();
            userBodyDTO.setId(user.getId());
                
            // Ajouter le cookie à la réponse
            HttpHeaders headers = new HttpHeaders();
            headers.add("Set-Cookie", String.format("token=%s; Path=/; Max-Age=%d; SameSite=none; Secure=true", 
                token.getToken(), 24 * 60 * 60));
                
            // Retourner la réponse avec l'utilisateur et le token
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(Map.of(
                        "id", user.getId(),
                        "token", token.getToken()
                    ));
        } catch (BadCredentialsException e) {
            // Gestion des erreurs de mauvaise authentification
            logger.error("Invalid credentials provided", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        } catch (Exception e) {
            // Gestion des autres erreurs génériques
            logger.error("An error occurred during authentication", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during authentication");
        }
    }
    
    


    @PostMapping("/register")
public ResponseEntity<?> register(@RequestBody User userBody) {
    try {
        // Vérification de l'email vide
        if (userBody.getEmail() == null || userBody.getEmail().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", "Email is required"));
        }

        // Vérification du mot de passe vide
        if (userBody.getPassword() == null || userBody.getPassword().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", "Password is required"));
        }

        // Appel au service pour l'enregistrement
        UserDTO createdUser = userRegisterService.UserRegister(userBody);
        return ResponseEntity.status(201).body(createdUser);  // Succès de la création
    } catch (IllegalArgumentException e) {
        // Cas où l'email existe déjà
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", e.getMessage()));
    } catch (ResponseStatusException e) {
        // Cas de validation de mot de passe ou autres exceptions de validation
        return ResponseEntity.status(e.getStatusCode()).body(Collections.singletonMap("message", e.getMessage()));
    } catch (Exception e) {
        // Gestion d'erreurs internes
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "An unexpected error occurred"));
    }
}




    @GetMapping("/logged-out")
    public ResponseEntity<Map<String, String>> loggedOut() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/auth/me")
    public ResponseEntity<CurrentUserDTO> getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        if (userPrincipal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userPrincipal.getUser();

        CurrentUserDTO currentUserDTO = new CurrentUserDTO();
        currentUserDTO.setId(user.getId());
        currentUserDTO.setFirstName(user.getFirstName());
        currentUserDTO.setLastName(user.getLastName());
        currentUserDTO.setEmail(user.getEmail());
        currentUserDTO.setRole(user.getRole());

        return ResponseEntity.ok(currentUserDTO);
    }

}
