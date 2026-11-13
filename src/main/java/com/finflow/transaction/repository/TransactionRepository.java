package com.finflow.transaction.repository;

import com.finflow.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    Optional<Transaction> findByIdempotencyKey(String idempotencyKey);
    Optional<Transaction> findByTransactionReference(String transactionReference);
    @Query(value = "SELECT nextval('finflow.transaction_reference_seq')",
            nativeQuery = true)
    Long getNextTransactionNumber();
}
