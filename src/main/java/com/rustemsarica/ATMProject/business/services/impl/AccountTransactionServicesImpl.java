package com.rustemsarica.ATMProject.business.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rustemsarica.ATMProject.business.dto.AccountTransactionDto;
import com.rustemsarica.ATMProject.business.services.AccountTransactionServices;
import com.rustemsarica.ATMProject.data.entities.AccountTransactionEntity;
import com.rustemsarica.ATMProject.data.entities.UserEntity;
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
    public List<AccountTransactionDto> getAllTransactions() {
        List<AccountTransactionDto> list = new ArrayList<>();
        List<AccountTransactionEntity> entities = accountTransactionRepository.findAll();
        
        for(AccountTransactionEntity entity : entities){
            list.add(entityToDto(entity));
        }
        return list;
    }

    @Override
    public List<AccountTransactionDto> getUserAllTransactions(Long id) {
        List<AccountTransactionDto> list = new ArrayList<>();
        List<AccountTransactionEntity> entities = accountTransactionRepository.findByUserId(id);
        
        for(AccountTransactionEntity entity : entities){
            list.add(entityToDto(entity));
        }
        return list;
    }

    @Override
    public AccountTransactionDto createTransaction(AccountTransactionDto transactionDto) {
        AccountTransactionEntity accountTransactionEntity = dtoToEntity(transactionDto);
        accountTransactionRepository.save(accountTransactionEntity);
        
        UserEntity userEntity = transactionDto.getUser();

        switch (transactionDto.getType()){
            case WITHDRAW:
                userEntity.removeBalance(transactionDto.getAmount());
                userRepository.save(userEntity);
            break;
            case DEPOSIT:
                userEntity.addBalance(transactionDto.getAmount());
                userRepository.save(userEntity);                
            break;
            case TRANSFER:
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
    
}
