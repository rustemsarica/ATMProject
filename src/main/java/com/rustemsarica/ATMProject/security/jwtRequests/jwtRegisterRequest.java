package com.rustemsarica.ATMProject.security.jwtRequests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class jwtRegisterRequest {
    @NotEmpty(message = "Name is required")
    @Size(min = 1, max = 255)
    private String name;

    @NotEmpty(message = "Username is required")
    @Email(regexp=".*@.*\\..*", message = "Format is invalid")
    @Column(unique = true)
    @Size(min = 1, max = 255)
    private String username;

    @NotEmpty(message = "Password is required")
    @Size(min = 1, max = 255)
    private String password;
}
