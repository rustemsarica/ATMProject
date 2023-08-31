package com.rustemsarica.ATMProject.data.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rustemsarica.ATMProject.data.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long>{

    UserEntity findByUsernameAndPassword(String username,  String password);

    Optional<UserEntity> findByUsername(String username);

    


    
}
