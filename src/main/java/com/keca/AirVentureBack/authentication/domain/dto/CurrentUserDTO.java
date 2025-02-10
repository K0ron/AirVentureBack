package com.keca.AirVentureBack.authentication.domain.dto;

import com.keca.AirVentureBack.user.domain.entity.User;

public class CurrentUserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private User.Role role;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public User.Role getRole() {
        return role;
    }
    public void setRole(User.Role role) {
        this.role = role;
    }
}
