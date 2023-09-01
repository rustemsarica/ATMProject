package com.rustemsarica.ATMProject.business.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.rustemsarica.ATMProject.business.dto.AccountTransactionDto;
import com.rustemsarica.ATMProject.data.entities.AccountTransactionEntity;

public interface AccountTransactionServices {

    public Page<AccountTransactionEntity> getAllTransactions(Pageable pageable);
    public Page<AccountTransactionEntity> getUserAllTransactions(Pageable pageable,Long id);

    public AccountTransactionDto createTransaction(AccountTransactionDto transactionDto) throws Exception;
    public ResponseEntity<AccountTransactionDto> getTransactionById(Long id);

    public AccountTransactionDto entityToDto(AccountTransactionEntity user);
    public AccountTransactionEntity dtoToEntity(AccountTransactionDto userDto);
}
