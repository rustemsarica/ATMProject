package com.rustemsarica.ATMProject.business.dto;

import com.rustemsarica.ATMProject.data.entities.utils.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LoginDto {
    private Long id;
    private String token;
    private String refreshToken;
    private String username;
    private UserRole role;
}
