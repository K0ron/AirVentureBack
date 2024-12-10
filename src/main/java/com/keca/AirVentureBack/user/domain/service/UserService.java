package com.keca.AirVentureBack.user.domain.service;

import com.keca.AirVentureBack.authentication.domain.service.UserLoginService;
import com.keca.AirVentureBack.authentication.domain.service.UserRegisterService;
import com.keca.AirVentureBack.user.domain.dto.UserPasswordChangeDTO;
import com.keca.AirVentureBack.user.domain.entity.User;
import com.keca.AirVentureBack.authentication.infrastructure.repository.UserRepository;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.*;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserLoginService userLoginService;
    private final UserRegisterService userRegisterService;

    public UserService(UserRepository userRepository, UserLoginService userLoginService,
            UserRegisterService userRegisterService) {
        this.userRepository = userRepository;
        this.userLoginService = userLoginService;
        this.userRegisterService = userRegisterService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getOneUser(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User updateUser(User updateUser, Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(updateUser.getFirstName());
                    user.setLastName(updateUser.getLastName());
                    user.setEmail(updateUser.getEmail());
                    return userRepository.save(user);
                })
                .orElseThrow();
    }

    public void deleteUSer(Long id) {
        userRepository.deleteById(id);
    }

    public User changePasswordWithAuthentication(UserPasswordChangeDTO userPasswordChangeDTO, Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    if (!userLoginService.verifyHashedPasswordDuringLogin(userPasswordChangeDTO.getPassword(),
                            user.getPassword())) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Password");
                    }

                    String newPassword = userPasswordChangeDTO.getNewPassword();
                    if (!isPasswordValid(newPassword)) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Password does not meet complexity requierments");
                    }
                    String hasedPassword = userRegisterService.generateHashedPassword(newPassword);
                    user.setPassword(hasedPassword);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Internal error, password was not changed"));

    }

    private boolean isPasswordValid(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$";
        return password.matches(passwordPattern);
    }

    public void addProfilePicture(Long userId, String pictureUrl) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        user.setProfilePicture(pictureUrl);

        userRepository.save(user);
    }

    public String getProfilePicture(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return user.getProfilePicture();
    }

}
