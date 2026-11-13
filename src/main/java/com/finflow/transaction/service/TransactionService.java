package com.finflow.transaction.service;
import com.finflow.common.exception.*;
import com.finflow.transaction.dto.TransactionRequest;
import com.finflow.transaction.dto.TransactionResponse;
import com.finflow.transaction.entity.Transaction;
import com.finflow.transaction.entity.TransactionStatus;
import com.finflow.transaction.repository.TransactionRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;



@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionWorker transactionWorker;
    private final TransactionQueryService transactionQueryService;
    public TransactionService(TransactionRepository transactionRepository,TransactionWorker transactionWorker,TransactionQueryService transactionQueryService){
        this.transactionRepository=transactionRepository;
        this.transactionWorker=transactionWorker;
        this.transactionQueryService=transactionQueryService;
    }

    public TransactionResponse transfer(TransactionRequest transactionRequest) {
        try {
            Transaction exisitingtransaction = transactionQueryService.findByIdempotencyKey(transactionRequest.getIdempotencyKey()).orElse(null);
            if (exisitingtransaction != null) {
                return validateSameRequest(exisitingtransaction, transactionRequest);
            }
            return transactionWorker.executeTransfer(transactionRequest);
        }
        catch (DuplicateKeyException e){
            Transaction existing=transactionQueryService.findByIdempotencyKey(transactionRequest.getIdempotencyKey()).orElse(null);
            return validateSameRequest(existing,transactionRequest);
        }
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
    private TransactionResponse validateSameRequest(Transaction transaction,TransactionRequest transactionRequest){
        if( !(transaction.getToAccountNumber().equals(transactionRequest.getToAccountNumber()) &&
                transaction.getFromAccountNumber().equals(transactionRequest.getFromAccountNumber()) &&
                transaction.getAmount().compareTo(transactionRequest.getAmount()) == 0)) throw new InvalidTransactionException("Idempotency is already used for this transaction");

        if(transaction.getStatus().equals(TransactionStatus.SUCCESS))return transactionResponse(transaction);
        else if(transaction.getStatus().equals(TransactionStatus.PENDING))throw new TransactionInProcessException("Transaction is inprocess");
        else throw new InvalidTransactionException("Transaction failed retry");
    }
}
