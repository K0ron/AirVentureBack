package com.keca.AirVentureBack.authentication.domain.service;

import com.keca.AirVentureBack.user.domain.dto.UserDTO;
import com.keca.AirVentureBack.user.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserDTO transformUserEntityIntoUSerDto(User user, Boolean isForResponse) {
        UserDTO userDto = new UserDTO();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(isForResponse ? "" : userDto.getPassword());
        userDto.setRole(user.getRole());

        return userDto;
    }

}
