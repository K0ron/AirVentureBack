package com.keca.AirVentureBack.authentication.domain.service;

import com.keca.AirVentureBack.authentication.infrastructure.repository.UserRepository;
import com.keca.AirVentureBack.user.domain.dto.UserDTO;
import com.keca.AirVentureBack.user.domain.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

        if (userRepository.existsByEmail(userData.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        userData.setPassword(generateHashedPassword(userData.getPassword()));
        try {
            return userMapper.transformUserEntityIntoUSerDto(userRepository.save(userData), true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String generateHashedPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

}
