package com.keca.AirVentureBack.authentication.domain.service;

import com.keca.AirVentureBack.authentication.infrastructure.repository.UserRepository;
import com.keca.AirVentureBack.user.domain.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserLoginService(
            UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // public User login(User user) {
    // User userEntity = getUserEntityByEmail(user.getEmail());
    // if (!verifyHashedPasswordDuringLogin(user.getPassword(),
    // userEntity.getPassword())) {
    // throw new RuntimeException();
    // }

    // user.setRole(userEntity.getRole());
    // user.setFirstName((userEntity.getFirstName()));
    // user.setLastName(userEntity.getLastName());
    // user.setPassword(userEntity.getPassword());
    // return user;
    // }
    public User login(User user) {
        // Vérifiez si l'utilisateur passé en paramètre est null
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        // Obtenez l'utilisateur de la base de données par e-mail
        User userEntity = getUserEntityByEmail(user.getEmail());

        // Vérifiez si l'utilisateur existe
        if (userEntity == null) {
            throw new RuntimeException("User not found with email: " + user.getEmail());
        }

        // Vérifiez le mot de passe
        if (!verifyHashedPasswordDuringLogin(user.getPassword(), userEntity.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Mettez à jour les informations de l'utilisateur
        user.setRole(userEntity.getRole());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setPassword(userEntity.getPassword()); // Assurez-vous de ne pas retourner le mot de passe

        return user; // Retournez l'utilisateur mis à jour
    }

    public boolean verifyHashedPasswordDuringLogin(String password, String hashedPassword) {
        return bCryptPasswordEncoder.matches(password, hashedPassword);
    }

    public User getUserEntityByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
