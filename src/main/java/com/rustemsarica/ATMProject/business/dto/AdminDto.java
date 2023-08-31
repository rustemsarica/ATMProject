package com.rustemsarica.ATMProject.business.dto;

import java.util.List;

import com.rustemsarica.ATMProject.data.entities.UserEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

public class AdminDto {
    private String username;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserEntity> users;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

}
