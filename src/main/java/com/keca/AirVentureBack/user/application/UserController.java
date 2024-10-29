package com.keca.AirVentureBack.user.application;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.keca.AirVentureBack.user.domain.entity.User;
import com.keca.AirVentureBack.user.domain.service.UserService;

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

}
