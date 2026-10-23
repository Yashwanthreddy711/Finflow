package com.finflow.account.dto;

import com.finflow.account.entity.AccountType;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;

@Data
public class AccountRequest {
    @NotBlank
    private String customerNumber;
    @NotNull
    @DecimalMin(value = "0",inclusive = true)
    private BigDecimal balance;
    @NotNull
    private AccountType type;
}
