package com.finflow.transaction.service;

import com.finflow.account.entity.Account;
import com.finflow.account.repository.AccountRepository;
import com.finflow.common.exception.AccountNotFoundException;
import com.finflow.common.exception.DuplicateAccountFoundException;
import com.finflow.common.exception.InsufficientBalanceException;
import com.finflow.common.exception.InvalidTransactionException;
import com.finflow.transaction.dto.TransactionRequest;
import com.finflow.transaction.dto.TransactionResponse;
import com.finflow.transaction.entity.Transaction;
import com.finflow.transaction.entity.TransactionStatus;
import com.finflow.transaction.entity.TransactionType;
import com.finflow.transaction.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionWorker {
    AccountRepository accountRepository;
    TransactionRepository transactionRepository;

    public TransactionWorker(AccountRepository accountRepository,TransactionRepository transactionRepository){
        this.transactionRepository=transactionRepository;
        this.accountRepository=accountRepository;
    }

    @Transactional
    public TransactionResponse executeTransfer(TransactionRequest transactionRequest){
        if (transactionRequest.getToAccountNumber().equals(transactionRequest.getFromAccountNumber()))
            throw new DuplicateAccountFoundException("From and to account numbers are identical");
        if (transactionRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0)
            throw new InvalidTransactionException("amount is less than zero");
        Account fromAccountNumber;
        Account toAccountNumber;

        if (transactionRequest.getFromAccountNumber().compareTo(transactionRequest.getToAccountNumber()) < 0) {
            fromAccountNumber = accountRepository.findByAccountNumber(transactionRequest.getFromAccountNumber()).orElseThrow(() -> new AccountNotFoundException("From account not found"));
            toAccountNumber = accountRepository.findByAccountNumber(transactionRequest.getToAccountNumber()).orElseThrow(() -> new AccountNotFoundException("To account not found"));
        } else {
            toAccountNumber = accountRepository.findByAccountNumber(transactionRequest.getToAccountNumber()).orElseThrow(() -> new AccountNotFoundException("To account not found"));
            fromAccountNumber = accountRepository.findByAccountNumber(transactionRequest.getFromAccountNumber()).orElseThrow(() -> new AccountNotFoundException("From account not found"));
        }

        Transaction transaction = new Transaction();
        Long nextNumber = transactionRepository.getNextTransactionNumber();
        transaction.setTransactionReference("TXN" + nextNumber);
        transaction.setFromAccountNumber(transactionRequest.getFromAccountNumber());
        transaction.setToAccountNumber(transactionRequest.getToAccountNumber());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setIdempotencyKey(transactionRequest.getIdempotencyKey());
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setType(TransactionType.TRANSFER);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());
        Transaction savedTransaction = transactionRepository.saveAndFlush(transaction);

        if (fromAccountNumber.getBalance().compareTo(transactionRequest.getAmount()) < 0)
            throw new InsufficientBalanceException("Sender doesn't have sufficient balance");
        fromAccountNumber.setBalance(fromAccountNumber.getBalance().subtract(transactionRequest.getAmount()));
        toAccountNumber.setBalance(toAccountNumber.getBalance().add(transactionRequest.getAmount()));
        accountRepository.save(fromAccountNumber);
        accountRepository.save(toAccountNumber);
        savedTransaction.setStatus(TransactionStatus.SUCCESS);
        savedTransaction.setUpdatedAt(LocalDateTime.now());
        savedTransaction = transactionRepository.save(savedTransaction);
        return transactionResponse(savedTransaction);
    }
    private TransactionResponse transactionResponse(Transaction transaction){
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setTransactionReference(transaction.getTransactionReference());
        transactionResponse.setFromAccountNumber(transaction.getFromAccountNumber());
        transactionResponse.setToAccountNumber(transaction.getToAccountNumber());
        transactionResponse.setAmount(transaction.getAmount());
        transactionResponse.setType(transaction.getType());
        transactionResponse.setStatus(transaction.getStatus());
        transactionResponse.setCreatedAt(transaction.getCreatedAt());
        return transactionResponse;
    }
}
