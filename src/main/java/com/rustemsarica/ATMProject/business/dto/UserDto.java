package com.rustemsarica.ATMProject.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
   
    private Long id;
    
    private String name;

    private String username;

}
