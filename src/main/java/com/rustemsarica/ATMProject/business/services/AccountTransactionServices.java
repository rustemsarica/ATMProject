package com.rustemsarica.ATMProject.business.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.rustemsarica.ATMProject.business.dto.AccountTransactionDto;
import com.rustemsarica.ATMProject.data.entities.AccountTransactionEntity;

public interface AccountTransactionServices {

    public List<AccountTransactionDto> getAllTransactions();
    public List<AccountTransactionDto> getUserAllTransactions(Long id);

    public AccountTransactionDto createTransaction(AccountTransactionDto transactionDto);
    public ResponseEntity<AccountTransactionDto> getTransactionById(Long id);

    public AccountTransactionDto entityToDto(AccountTransactionEntity user);
    public AccountTransactionEntity dtoToEntity(AccountTransactionDto userDto);
}
