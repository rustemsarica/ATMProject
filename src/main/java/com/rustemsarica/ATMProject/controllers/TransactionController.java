package com.rustemsarica.ATMProject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rustemsarica.ATMProject.business.dto.AccountTransactionDto;
import com.rustemsarica.ATMProject.business.services.AccountTransactionServices;
import com.rustemsarica.ATMProject.data.entities.utils.TransactionType;
import com.rustemsarica.ATMProject.data.repositories.UserRepository;


@RestController
@RequestMapping("/transactions")
public class TransactionController {
    
    @Autowired
    AccountTransactionServices accountTransactionServices;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity<List<AccountTransactionDto>> getAllTransactions(){
        return ResponseEntity.ok(accountTransactionServices.getAllTransactions());
    }

    @GetMapping
    public ResponseEntity<?> createTransaction(){
        //  AccountTransactionDto accountTransactionDto = AccountTransactionDto.builder().user(userRepository.findById(1L).get()).amount(50F).type(TransactionType.TRANSFER).receiver(userRepository.findById(2L).get()).build(); 
        AccountTransactionDto accountTransactionDto = AccountTransactionDto.builder().user(userRepository.findById(1L).get()).amount(50F).type(TransactionType.DEPOSIT).build(); 
        // AccountTransactionDto accountTransactionDto = AccountTransactionDto.builder().user(userRepository.findById(1L).get()).amount(50F).type(TransactionType.WITHDRAW).build();
        try {
            AccountTransactionDto accountTransactionDto2 = accountTransactionServices.createTransaction(accountTransactionDto);
            return ResponseEntity.ok(accountTransactionDto2);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
