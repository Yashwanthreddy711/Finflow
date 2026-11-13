package com.finflow.transaction.controller;

import com.finflow.transaction.dto.TransactionRequest;
import com.finflow.transaction.dto.TransactionResponse;
import com.finflow.transaction.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService){
        this.transactionService=transactionService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse>transfer(@Valid @RequestBody TransactionRequest transactionRequest){

        TransactionResponse transactionResponse= transactionService.transfer(transactionRequest);
        return ResponseEntity.ok(transactionResponse);

    }
}
