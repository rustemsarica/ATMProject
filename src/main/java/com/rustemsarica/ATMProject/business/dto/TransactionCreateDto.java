package com.rustemsarica.ATMProject.business.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionCreateDto {
    private int type;

    private Long userId;

    private String receiver;

    private float amount;
}
