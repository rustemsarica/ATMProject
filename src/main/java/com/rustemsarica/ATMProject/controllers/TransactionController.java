package com.rustemsarica.ATMProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rustemsarica.ATMProject.business.dto.AccountTransactionDto;
import com.rustemsarica.ATMProject.business.dto.TransactionCreateDto;
import com.rustemsarica.ATMProject.business.dto.AccountTransactionDto.AccountTransactionDtoBuilder;
import com.rustemsarica.ATMProject.business.services.AccountTransactionServices;
import com.rustemsarica.ATMProject.business.services.UserServices;
import com.rustemsarica.ATMProject.data.entities.AccountTransactionEntity;
import com.rustemsarica.ATMProject.data.entities.UserEntity;
import com.rustemsarica.ATMProject.data.entities.utils.TransactionType;
import com.rustemsarica.ATMProject.data.repositories.UserRepository;
import com.rustemsarica.ATMProject.exceptions.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
@RequestMapping("/transactions")
public class TransactionController {
    
    @Autowired
    AccountTransactionServices accountTransactionServices;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServices userServices;

    @GetMapping
    public ResponseEntity<Page<AccountTransactionEntity>> getAllTransactions(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Pageable pageable = PageRequest.of((int)page, (int)size);
        Page<AccountTransactionEntity> response = null;

        if(auth!=null){
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                response = accountTransactionServices.getAllTransactions(pageable);
            }else{
                response = accountTransactionServices.getUserAllTransactions(pageable, userServices.getUserByUsername(auth.getName()).getId());
            }
        }
        

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<AccountTransactionEntity>> getUserTransactions(@PathVariable Long userId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size)
    {
        Pageable pageable = PageRequest.of((int)page, (int)size);
        Page<AccountTransactionEntity> response = accountTransactionServices.getUserAllTransactions(pageable, userId);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionCreateDto transactionCreateDto) throws Exception, ResourceNotFoundException{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userServices.getUserByUsername(auth.getName());
        AccountTransactionDtoBuilder accountTransactionDtoBuilder = AccountTransactionDto.builder().user(userEntity).amount(transactionCreateDto.getAmount());

        if(transactionCreateDto.getType()==0){
            accountTransactionDtoBuilder.type(TransactionType.WITHDRAW);
        }else if(transactionCreateDto.getType()==1){
            accountTransactionDtoBuilder.type(TransactionType.DEPOSIT);
        }else if(transactionCreateDto.getType()==2){
            try {
                accountTransactionDtoBuilder.type(TransactionType.TRANSFER).receiver(userServices.getUserByUsername(transactionCreateDto.getReceiver())).sender(userEntity);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
            
        }else{
            throw new Exception("Type error");
        }
        AccountTransactionDto accountTransactionDto = accountTransactionDtoBuilder.build();

        try {
            AccountTransactionDto accountTransactionDto2 = accountTransactionServices.createTransaction(accountTransactionDto);
            return ResponseEntity.ok(accountTransactionDto2);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
