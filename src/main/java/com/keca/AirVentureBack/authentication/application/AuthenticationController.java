package com.keca.AirVentureBack.authentication.application;

import com.keca.AirVentureBack.authentication.domain.entity.Token;
import com.keca.AirVentureBack.authentication.domain.service.JwtTokenService;
import com.keca.AirVentureBack.authentication.domain.service.UserDetailsServiceImpl;
import com.keca.AirVentureBack.authentication.domain.service.UserLoginService;
import com.keca.AirVentureBack.authentication.domain.service.UserRegisterService;
import com.keca.AirVentureBack.user.domain.dto.UserIdDTO;
import com.keca.AirVentureBack.user.domain.entity.User;

import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        logger.info("Loggin attemp for email: {}", userBody.getEmail());

        try {
            userLoginService.login(userBody);
            logger.info("Authentication successful for email: {}", userBody.getEmail()); // Étape 2
            Token token = jwtTokenService.generateToken(userDetailsService.loadUserByUsername(userBody.getEmail()));
            logger.info("JWT generated for email: {}", userBody.getEmail()); // Étape 3
            logger.debug("JWT content: {}", token.getToken()); // Debug pour voir le contenu
            User user = userLoginService.getUserEntityByEmail(userBody.getEmail());
            ResponseCookie jwtCookie = ResponseCookie.from("token", token.getToken())
                    .httpOnly(true)
                    .path("/")
                    .maxAge(60 * 60)
                    .sameSite("Strict")
                    .build();
            logger.info("JWT cookie created for email: {}", userBody.getEmail()); // Étape 4
            UserIdDTO userBodyDTO = new UserIdDTO();
            userBodyDTO.setId(user.getId());
            logger.info("Login response ready for email: {}", userBody.getEmail()); // Étape 5
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(userBodyDTO); // cette ligne renvoie le DTO dans le body
            /* .build(); */
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occured durieng authentication");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User userBody) throws Exception {
        try {
            return ResponseEntity.status(201).body(userRegisterService.UserRegister(userBody));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/logged-out")
    public String loggedOut(Model model) {
        model.addAttribute("logout", "You logged out!");
        return "Logged out 👌🏽";

    }

}
