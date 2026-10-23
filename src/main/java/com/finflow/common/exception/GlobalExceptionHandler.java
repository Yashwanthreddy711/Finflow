package com.finflow.common.exception;

import com.finflow.common.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<?>handleValidationException(MethodArgumentNotValidException e){
            List<FieldError> errors=e.getBindingResult().getFieldErrors();
            Map<String, String>erroRsp=new HashMap<>();
            for (FieldError error : errors) {
                erroRsp.put(error.getField(), error.getDefaultMessage());
            }
            ApiErrorResponse apiErrorResponse=new ApiErrorResponse(400,"Validation Failed",erroRsp);
            return ResponseEntity.status(400).body(apiErrorResponse);
        }

        @ExceptionHandler(CustomerNotFoundException.class)
        public ResponseEntity<?>handleCustomerNotFoundException(CustomerNotFoundException e){
            ApiErrorResponse apiErrorResponse=new ApiErrorResponse(404,e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorResponse);
        }

        @ExceptionHandler(DuplicateCustomerException.class)
        public ResponseEntity<?>hanldeDuplicateCustomerFoundException(DuplicateCustomerException e){
            ApiErrorResponse apiErrorResponse=new ApiErrorResponse(409,e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiErrorResponse);
        }

        @ExceptionHandler(InsufficientBalanceException.class)
        public ResponseEntity<?>hanldeInsufficientBalanceException(InsufficientBalanceException e){
            ApiErrorResponse apiErrorResponse=new ApiErrorResponse(422,e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiErrorResponse);
        }
        @ExceptionHandler(InvalidTransferAmountException.class)
        public ResponseEntity<?>handleInvalidTransferAmountException(InvalidTransferAmountException e){
            ApiErrorResponse apiErrorResponse=new ApiErrorResponse(422, e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiErrorResponse);
        }
        @ExceptionHandler(InvalidTransactionException.class)
        public ResponseEntity<?>handleInvalidTransactionException(InvalidTransactionException e){
            ApiErrorResponse apiErrorResponse=new ApiErrorResponse(500, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorResponse);
        }
        @ExceptionHandler(CustomerHasAccountsException.class)
        public ResponseEntity<?>handleCustomerHasAccountsException(CustomerHasAccountsException e){
            ApiErrorResponse apiErrorResponse=new ApiErrorResponse(409,e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiErrorResponse);
        }

    }
