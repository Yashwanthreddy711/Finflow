package com.finflow.transaction.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ff_transaction")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(
            name = "transaction_reference",
            nullable = false,
            unique = true
    )
    private String transactionReference;
    @Column(nullable = false)
    private String fromAccountNumber;
    @Column(nullable = false)
    private String toAccountNumber;
    @Column(nullable = false,precision = 19,scale = 2)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;
    @Column(nullable = false,unique = true)
    private String idempotencyKey;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
