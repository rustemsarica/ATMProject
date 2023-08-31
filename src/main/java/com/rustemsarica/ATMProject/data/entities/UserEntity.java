package com.rustemsarica.ATMProject.data.entities;


import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity extends BaseEntity implements Serializable{
    
    private String name;

    @Column(unique = true)
    private String username;

    private String password;

    private float balance = 0.00F;

    private int failedLoginAttempts = 0;

    private boolean accountNonLocked = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getBalance() {
        return balance;
    }

    public void addBalance(float balance) {
        this.balance += balance;
    }

    public void removeBalance(float balance) {
        this.balance -= balance;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedAttempts) {
        this.failedLoginAttempts = failedAttempts;
        
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

 
    
}
