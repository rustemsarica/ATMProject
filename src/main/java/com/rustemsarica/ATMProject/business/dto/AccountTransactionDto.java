package com.rustemsarica.ATMProject.business.dto;

import com.rustemsarica.ATMProject.data.entities.UserEntity;
import com.rustemsarica.ATMProject.data.entities.utils.TransactionType;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountTransactionDto {
    private TransactionType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private UserEntity user;

    private float amount;

}
