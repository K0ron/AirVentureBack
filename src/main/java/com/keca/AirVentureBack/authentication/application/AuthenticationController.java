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
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody User userBody) throws Exception {
        try {
            userLoginService.login(userBody);
            Token token = jwtTokenService.generateToken(userDetailsService.loadUserByUsername(userBody.getEmail()));
            User user = userLoginService.getUserEntityByEmail(userBody.getEmail());
            ResponseCookie jwtCookie = ResponseCookie.from("token", token.getToken())
                    .httpOnly(true)
                    .path("/")
                    .maxAge(60 * 60)
                    .sameSite("Strict")
                    .build();
            UserIdDTO userBodyDTO = new UserIdDTO();
            userBodyDTO.setId(user.getId());
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(userBodyDTO); // cette ligne renvoie le DTO dans le body
            /* .build(); */
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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

    /*
     * @PostMapping("/logout")
     * public String logout(HttpServletResponse response, @CookieValue(name =
     * "token", required = false) Cookie cookie) {
     * if (cookie != null) {
     * ResponseCookie deleteCookie = ResponseCookie.from("token", "")
     * .httpOnly(true)
     * .path("/")
     * .maxAge(0)
     * .sameSite("Strict")
     * .build();
     * response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());
     * }
     * return "redirect:/login";
     * }
     */
}
