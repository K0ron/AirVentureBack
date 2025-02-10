package com.keca.AirVentureBack.authentication.application;

import com.keca.AirVentureBack.authentication.domain.dto.CurrentUserDTO;
import com.keca.AirVentureBack.authentication.domain.dto.UserPrincipal;
import com.keca.AirVentureBack.authentication.domain.entity.Token;
import com.keca.AirVentureBack.authentication.domain.service.JwtTokenService;
import com.keca.AirVentureBack.authentication.domain.service.UserDetailsServiceImpl;
import com.keca.AirVentureBack.authentication.domain.service.UserLoginService;
import com.keca.AirVentureBack.authentication.domain.service.UserRegisterService;
import com.keca.AirVentureBack.user.domain.dto.UserIdDTO;
import com.keca.AirVentureBack.user.domain.entity.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            userLoginService.login(userBody);
            Token token = jwtTokenService.generateToken(userDetailsService.loadUserByUsername(userBody.getEmail()));
            User user = userLoginService.getUserEntityByEmail(userBody.getEmail());
            
            // Créer le cookie avec la configuration appropriée
            Cookie jwtCookie = new Cookie("token", token.getToken());
            jwtCookie.setHttpOnly(true);  
            jwtCookie.setSecure(true);    
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(24 * 60 * 60); 
            jwtCookie.setDomain("localhost");

            
            
            UserIdDTO userBodyDTO = new UserIdDTO();
            userBodyDTO.setId(user.getId());
            
            // Ajouter le cookie à la réponse
            HttpHeaders headers = new HttpHeaders();
            headers.add("Set-Cookie", String.format("token=%s; Path=/; Max-Age=%d; SameSite=none; Secure=true", 
                token.getToken(), 24 * 60 * 60));
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(Map.of(
                        "id", user.getId(),
                        "token", token.getToken()
                    ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during authentication");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User userBody) throws Exception {
        try {
            return ResponseEntity.status(201).body(userRegisterService.UserRegister(userBody));
        } catch (BadCredentialsException e) {
            logger.error("Invalid credentials: " + e.getMessage());
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/logged-out")
    public String loggedOut(Model model) {
        model.addAttribute("logout", "You logged out!");
        return "Logged out 👌🏽";

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
