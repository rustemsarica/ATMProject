package com.rustemsarica.ATMProject.business.dto;

import com.rustemsarica.ATMProject.data.entities.UserEntity;
import com.rustemsarica.ATMProject.data.entities.utils.TransactionType;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountTransactionDto {
    private TransactionType type;

    private UserEntity user;

    private UserEntity receiver;

    private UserEntity sender;

    private float amount;

}
