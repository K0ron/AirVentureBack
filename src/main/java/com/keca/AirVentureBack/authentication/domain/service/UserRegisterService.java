package com.keca.AirVentureBack.authentication.domain.service;

import com.keca.AirVentureBack.authentication.infrastructure.repository.UserRepository;
import com.keca.AirVentureBack.user.domain.dto.UserDTO;
import com.keca.AirVentureBack.user.domain.entity.User;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserRegisterService {
    final UserRepository userRepository;
    final BCryptPasswordEncoder bCryptPasswordEncoder;
    final UserMapper userMapper;

    public UserRegisterService(
            UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            UserMapper userMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
    }

    public UserDTO UserRegister(User userData) {

        if (userData.getEmail() == null || userData.getEmail().isEmpty()) {
            
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }

        if (userData.getPassword() == null || userData.getPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
        }

        if (userRepository.existsByEmail(userData.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        if (!isPasswordValid(userData.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Password does not meet complexity requierments");
        }

        userData.setPassword(generateHashedPassword(userData.getPassword()));
        try {
            return userMapper.transformUserEntityIntoUSerDto(userRepository.save(userData), true);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User registration failed", e);

        }
    }

    public String generateHashedPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    private boolean isPasswordValid(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+\\{\\}\\[\\]|\\\\:;\"'<>,.?/]).{8,}$";
        return password.matches(passwordPattern);
    }

}
