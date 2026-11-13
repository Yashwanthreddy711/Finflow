package com.finflow.transaction.service;

import com.finflow.transaction.entity.Transaction;
import com.finflow.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
public class TransactionQueryService {

    private final TransactionRepository transactionRepository;

    public TransactionQueryService(TransactionRepository transactionRepository){
        this.transactionRepository=transactionRepository;
    }
    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true
    )
    public Optional<Transaction>findByIdempotencyKey(String key){
        return transactionRepository.findByIdempotencyKey(key);
    }
}
