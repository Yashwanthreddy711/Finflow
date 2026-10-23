package com.finflow.account.service;

import com.finflow.account.dto.AccountRequest;
import com.finflow.account.dto.AccountResponse;
import com.finflow.account.entity.Account;
import com.finflow.account.entity.AccountStatus;
import com.finflow.account.repository.AccountRepository;
import com.finflow.common.exception.*;
import com.finflow.customer.entity.Customer;
import com.finflow.customer.repository.CustomerRepository;
import com.zaxxer.hikari.util.IsolationLevel;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
    public class AccountService {
        private final AccountRepository accountRepository;
        private final CustomerRepository customerRepository;


        public AccountService(AccountRepository accountRepository,CustomerRepository customerRepository){

            this.accountRepository=accountRepository;
            this.customerRepository=customerRepository;
        }
        public Account getAccount(String accountNumber){
          return accountRepository.findByAccountNumber(accountNumber).orElseThrow(()->new AccountNotFoundException("Account Not found"));
        }
        public Account saveAccount(Account account){
            return accountRepository.save(account);
        }

        @Transactional
        public void transfer(String accountNumber, String toAccountNumber, BigDecimal amount)  {
            if(accountNumber.equals(toAccountNumber))throw new InvalidTransactionException("Same accounts detected");
            if(amount.compareTo(BigDecimal.ZERO)<=0){
                throw new InvalidTransferAmountException("amount must be greater than zero");
            }

            Account fromAccount;
            Account toAccount;

            if(accountNumber.compareTo(toAccountNumber)>0){
                 toAccount=getAccount(toAccountNumber);
                 fromAccount = getAccount(accountNumber);
            }
            else{
                fromAccount=getAccount(accountNumber);
                toAccount=getAccount(toAccountNumber);
            }
          debitAmount(fromAccount,amount);
          creditAmount(toAccount,amount);
        }
        public  void debitAmount(Account fromAccount,BigDecimal amount){
            if(fromAccount.getBalance().compareTo(amount)<0) {
                throw new InsufficientBalanceException("Insufficient Funds");
            }
            BigDecimal finalAmount=fromAccount.getBalance().subtract(amount);
            fromAccount.setBalance(finalAmount);
            saveAccount(fromAccount);
        }
        public  void creditAmount(Account toAccount,BigDecimal amount){
            BigDecimal finalAmount=toAccount.getBalance().add(amount);
            toAccount.setBalance(finalAmount);
            saveAccount(toAccount);
        }

        public void debitAtomically(String accountNumber, BigDecimal amount){
            if(amount.compareTo(BigDecimal.ZERO)<=0)throw new InvalidTransactionException("amount should be greater than the 0");
               int status= accountRepository.amountTransfer(accountNumber, amount);
               if(status==0)throw new InvalidTransactionException("Internal server retry again");
        }

        @Transactional
        public AccountResponse createAccount(AccountRequest accountRequest){
            Customer customer=customerRepository.findByCustomerNumber(accountRequest.getCustomerNumber());
            if(customer==null)throw  new CustomerNotFoundException("Customer Not found");
            Account account=new Account();
            account.setBalance(accountRequest.getBalance());
            account.setType(accountRequest.getType());
            account.setCustomer(customer);
            account.setStatus(AccountStatus.ACTIVE);
            Account savedAccount= accountRepository.save(account);
            return prepareCreateAccountResponse(savedAccount);
        }

        public AccountResponse prepareCreateAccountResponse(Account account){
            AccountResponse accountResponse=new AccountResponse();
            accountResponse.setAccountNumber(account.getAccountNumber());
            accountResponse.setType(account.getType());
            accountResponse.setBalance(account.getBalance());
            accountResponse.setCustomerNumber(account.getCustomer().getCustomerNumber());
            accountResponse.setStatus(account.getStatus());
          return accountResponse;
        }

        @Transactional
        public AccountResponse findAccount(String accountNumber){
          Account account=accountRepository.findByAccountNumber(accountNumber).orElseThrow(()->new  AccountNotFoundException("Account Not Found"));
          return prepareCreateAccountResponse(account);
        }

        public List<AccountResponse>findCustomerByAccountNumber(String customerNumber){
            Customer customer = customerRepository.findByCustomerNumber(customerNumber);
            if(customer==null)throw new CustomerNotFoundException("Customer Not found");
           List<Account>accountList=accountRepository.findByCustomer_CustomerNumber(customerNumber);
           List<AccountResponse>accountResponseList=new ArrayList<>();
            for (Account account : accountList) {
                accountResponseList.add(prepareCreateAccountResponse(account));
            }
           return accountResponseList;
        }

        public Page<AccountResponse> findAllAccounts(Pageable pageable){
            Page<Account>accountPage=accountRepository.findAll(pageable);
            return accountPage.map(account -> prepareCreateAccountResponse(account));

        }
    }
