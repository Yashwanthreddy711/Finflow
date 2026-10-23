package com.finflow.account.controller;


import com.finflow.account.dto.AccountRequest;
import com.finflow.account.dto.AccountResponse;
import com.finflow.account.dto.TransferRequest;
import com.finflow.account.entity.Account;
import com.finflow.account.entity.AccountStatus;
import com.finflow.account.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService=accountService;
    }
    @PostMapping
    public ResponseEntity<?>createAccount(@Valid  @RequestBody AccountRequest accountRequest){
        AccountResponse createAccountResponse=accountService.createAccount(accountRequest);
        return  ResponseEntity.status(HttpStatus.CREATED).body(createAccountResponse);
    }
    @GetMapping("/{accountNumber}")
    public ResponseEntity<?>findAccount(@PathVariable  String accountNumber){
      AccountResponse findAccountResponse=accountService.findAccount(accountNumber);
      return ResponseEntity.ok(findAccountResponse);
    }
    @GetMapping
    public ResponseEntity<?>findAllAccounts(@PageableDefault(page = 0,size = 10)Pageable pageable){
        Page<AccountResponse>accountPageResponse=accountService.findAllAccounts(pageable);
        return ResponseEntity.ok(accountPageResponse);
    }

}
