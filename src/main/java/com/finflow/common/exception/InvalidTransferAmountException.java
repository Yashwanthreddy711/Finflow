package com.finflow.common.exception;

public class InvalidTransferAmountException extends RuntimeException{
    public InvalidTransferAmountException(String message){
        super(message);
    }
}
