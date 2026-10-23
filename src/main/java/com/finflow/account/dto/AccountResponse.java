package com.finflow.account.dto;

import com.finflow.account.entity.AccountStatus;
import com.finflow.account.entity.AccountType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponse {
    private String accountNumber;
    private BigDecimal balance;
    private AccountType type;
    private AccountStatus status;
    private String customerNumber;
}
