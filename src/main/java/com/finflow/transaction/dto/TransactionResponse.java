package com.finflow.transaction.dto;

import com.finflow.transaction.entity.TransactionStatus;
import com.finflow.transaction.entity.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {
    private String transactionReference;
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;
    private TransactionType type;
    private TransactionStatus status;
    private LocalDateTime createdAt;
}
