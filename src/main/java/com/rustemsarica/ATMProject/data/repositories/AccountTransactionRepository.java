package com.rustemsarica.ATMProject.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rustemsarica.ATMProject.data.entities.AccountTransactionEntity;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransactionEntity,Long> {

    List<AccountTransactionEntity> findByUserId(Long id);
    
}
