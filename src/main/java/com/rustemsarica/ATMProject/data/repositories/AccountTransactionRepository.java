package com.rustemsarica.ATMProject.data.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rustemsarica.ATMProject.data.entities.AccountTransactionEntity;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransactionEntity,Long> {

    Page<AccountTransactionEntity> findAllByUserId(Pageable pageable, Long id);

    Page<AccountTransactionEntity> findAllByOrderByCreatedDateDesc(Pageable pageable);

    Page<AccountTransactionEntity> findAllByOrderByIdDesc(Pageable pageable);

    Page<AccountTransactionEntity> findAllByUserIdOrderByIdDesc(Pageable pageable, Long id);

   
    
}
