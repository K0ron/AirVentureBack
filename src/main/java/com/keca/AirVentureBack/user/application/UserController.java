package com.keca.AirVentureBack.user.application;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.keca.AirVentureBack.user.domain.dto.UserPasswordChangeDTO;
import com.keca.AirVentureBack.user.domain.entity.User;
import com.keca.AirVentureBack.user.domain.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    List<User> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    User getOne(@PathVariable Long id) {
        return userService.getOneUser(id);
    }

    @PutMapping("/user/{id}")
    User edit(@RequestBody User updateUser, @PathVariable Long id) {
        return userService.updateUser(updateUser, id);
    }

    @DeleteMapping("/user/{id}")
    void delete(@PathVariable Long id) {
        userService.deleteUSer(id);
    }

    @PutMapping("/password-change/{id}")
    public ResponseEntity<?> editPassword(@RequestBody UserPasswordChangeDTO userPasswordChangeDTO,
            @PathVariable Long id) {
        try {
            User updateUser = userService.changePasswordWithAuthentication(userPasswordChangeDTO, id);
            return ResponseEntity.ok(updateUser);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }

}
