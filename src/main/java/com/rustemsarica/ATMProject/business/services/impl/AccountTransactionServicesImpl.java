package com.rustemsarica.ATMProject.business.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rustemsarica.ATMProject.business.dto.AccountTransactionDto;
import com.rustemsarica.ATMProject.business.services.AccountTransactionServices;
import com.rustemsarica.ATMProject.data.entities.AccountTransactionEntity;
import com.rustemsarica.ATMProject.data.entities.UserEntity;
import com.rustemsarica.ATMProject.data.entities.utils.TransactionType;
import com.rustemsarica.ATMProject.data.repositories.AccountTransactionRepository;
import com.rustemsarica.ATMProject.data.repositories.UserRepository;

@Service
public class AccountTransactionServicesImpl implements AccountTransactionServices {

    @Autowired
    private AccountTransactionRepository accountTransactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<AccountTransactionEntity> getAllTransactions(Pageable pageable) {
        
        Page<AccountTransactionEntity> entities = accountTransactionRepository.findAllByOrderByIdDesc(pageable);        
        
        return entities;
    }

    @Override
    public Page<AccountTransactionEntity> getUserAllTransactions(Pageable pageable, Long id) {
        
        Page<AccountTransactionEntity> entities = accountTransactionRepository.findAllByUserIdOrderByIdDesc(pageable,id);
        return entities;
    }

    @Override
    public AccountTransactionDto createTransaction(AccountTransactionDto transactionDto) throws Exception {
                
        switch (transactionDto.getType()){
            case WITHDRAW:
                withdraw(transactionDto);
            break;
            case DEPOSIT:
                deposit(transactionDto);             
            break;
            case TRANSFER:                
                transfer(transactionDto);
            break;
        }
        return transactionDto;
    }

    @Override
    public ResponseEntity<AccountTransactionDto> getTransactionById(Long id) {
        AccountTransactionDto accountTransactionDto = entityToDto(accountTransactionRepository.findById(id).get());
        return ResponseEntity.ok(accountTransactionDto);
    }

    @Override
    public AccountTransactionDto entityToDto(AccountTransactionEntity entity) {
        AccountTransactionDto accountTransactionDto = modelMapper.map(entity, AccountTransactionDto.class);
        return accountTransactionDto;
    }

    @Override
    public AccountTransactionEntity dtoToEntity(AccountTransactionDto dto) {
        AccountTransactionEntity accountTransactionEntity = modelMapper.map(dto, AccountTransactionEntity.class);
        return accountTransactionEntity;
    }

    private void withdraw(AccountTransactionDto transactionDto) throws Exception{
        AccountTransactionEntity accountTransactionEntity = dtoToEntity(transactionDto);
        accountTransactionRepository.save(accountTransactionEntity);

        UserEntity userEntity = transactionDto.getUser();
        if(userEntity.getBalance() >= transactionDto.getAmount()){
            userEntity.removeBalance(transactionDto.getAmount());
            userRepository.save(userEntity);
        }else{
            throw new Exception("Not enough balance");
        }
    }

    private void deposit(AccountTransactionDto transactionDto) throws Exception{
        AccountTransactionEntity accountTransactionEntity = dtoToEntity(transactionDto);
        accountTransactionRepository.save(accountTransactionEntity);

        UserEntity userEntity = transactionDto.getUser();
        userEntity.addBalance(transactionDto.getAmount());
        userRepository.save(userEntity);
    }

    private void transfer(AccountTransactionDto transactionDto) throws Exception{        
        
        UserEntity userEntity = transactionDto.getUser();
        if(userEntity.getBalance() >= transactionDto.getAmount()){
            AccountTransactionDto sender = AccountTransactionDto.builder()
                                                                    .user(transactionDto.getUser())
                                                                    .receiver(transactionDto.getReceiver())
                                                                    .amount(transactionDto.getAmount())
                                                                    .type(TransactionType.WITHDRAW)
                                                                    .build();
            withdraw(sender);

            AccountTransactionDto receiver = AccountTransactionDto.builder()
                                                                    .user(transactionDto.getReceiver())
                                                                    .amount(transactionDto.getAmount())
                                                                    .type(TransactionType.DEPOSIT)
                                                                    .sender(transactionDto.getUser())
                                                                    .build();
            deposit(receiver);            
        }else{
            throw new Exception("Not enough balance");
        }
        
    }
    
}
