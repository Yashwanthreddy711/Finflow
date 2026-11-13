package com.finflow.transaction.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    @NotBlank
    private String fromAccountNumber;
    @NotBlank
    private String toAccountNumber;
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.00",inclusive = false)
    private BigDecimal amount;
    @NotBlank
    private String idempotencyKey;
}
