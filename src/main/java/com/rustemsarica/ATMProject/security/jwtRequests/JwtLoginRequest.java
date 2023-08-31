package com.rustemsarica.ATMProject.security.jwtRequests;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtLoginRequest implements Serializable{
    private static final long serialVersionUID = 5926468583005150707L;

    @NotEmpty
    @Email
    private String username;
    @NotEmpty
    private String password;
}
