package com.rustemsarica.ATMProject.business.dto;

import java.util.List;

import com.rustemsarica.ATMProject.data.entities.AccountTransactionEntity;

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

    private List<AccountTransactionEntity> accountTransactionEntities;

}
