package com.rustemsarica.ATMProject.security.jwtRequests;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRegisterRequest {
    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Username is required")
    @Email(regexp=".*@.*\\..*", message = "Format is invalid")
    @Column(unique = true)
    private String username;

    @NotEmpty(message = "Password is required")
    @Size(min = 6, max = 255, message = "Password must be at least 6 characters long")
    private String password;
}
