package com.rustemsarica.ATMProject.data.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rustemsarica.ATMProject.data.entities.utils.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity extends BaseEntity{
    
    @JsonIgnore
    private UserRole role = UserRole.ROLE_USER;

    private String name;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @Min(value = 0, message = "Your money not enaugh")
    private float balance = 0.00F;

    @JsonIgnore
    private int failedLoginAttempts = 0;

    @JsonIgnore
    private boolean accountNonLocked = true;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<AccountTransactionEntity> accountTransactionEntities;


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

    public List<AccountTransactionEntity> getAccountTransactionEntities() {
        return accountTransactionEntities;
    }

    public void setAccountTransactionEntities(List<AccountTransactionEntity> accountTransactionEntities) {
        this.accountTransactionEntities = accountTransactionEntities;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole userRole) {
        this.role = userRole;
    }
    
}
