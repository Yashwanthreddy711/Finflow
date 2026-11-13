package com.finflow.common.exception;

public class DuplicateAccountFoundException extends RuntimeException{

    public DuplicateAccountFoundException(String message) {
        super(message);
    }
}
