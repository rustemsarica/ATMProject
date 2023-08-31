package com.rustemsarica.ATMProject.data.entities;

import com.rustemsarica.ATMProject.data.entities.utils.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account_transactions")
public class AccountTransactionEntity extends BaseEntity {

    private TransactionType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private UserEntity user;

    private float amount;

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    

}
