package com.finflow.common.exception;

public class TransactionInProcessException extends RuntimeException{

    public TransactionInProcessException(String message) {
        super(message);
    }
}
