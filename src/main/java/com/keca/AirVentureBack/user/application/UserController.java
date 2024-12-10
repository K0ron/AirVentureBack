package com.keca.AirVentureBack.user.application;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.keca.AirVentureBack.authentication.infrastructure.repository.UserRepository;
import com.keca.AirVentureBack.upload.services.UploadScalewayService;
import com.keca.AirVentureBack.user.domain.dto.UserPasswordChangeDTO;
import com.keca.AirVentureBack.user.domain.entity.User;
import com.keca.AirVentureBack.user.domain.service.UserService;

import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController {


    UserService userService;
   // UploadService uploadService;
   UploadScalewayService uploadScalewayService;
   UserRepository userRepository;

    public UserController(UserService userService, UploadScalewayService uploadScalewayService, UserRepository userRepository ) {
        this.userService = userService;
       // this.uploadService = uploadService;
       this.uploadScalewayService = uploadScalewayService;
       this.userRepository = userRepository;

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

    @PostMapping("/user/{id}/upload-profile-picture")
    public ResponseEntity<String> uploadProfilePicture(@RequestParam("file") MultipartFile file, @PathVariable("id") Long userId) {

        try {
            String fileUrl = uploadScalewayService.uploadOneFile(file, userId, "users");
            
            userService.addProfilePicture(userId, fileUrl);

            return ResponseEntity.ok(fileUrl);
          
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error: " + e.getMessage());
        }
    
    }

    @GetMapping("/user/{id}/profile-picture")
    public ResponseEntity<String> getProfilePicture(@PathVariable("id") Long userId) {
        try {
            String profilePicture = userService.getProfilePicture(userId);
            return ResponseEntity.ok(profilePicture);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("User not found" + e.getMessage());
        }
    }
    


}
