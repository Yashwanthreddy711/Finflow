package com.finflow.customer.controller;

import com.finflow.account.dto.AccountResponse;
import com.finflow.account.entity.Account;
import com.finflow.account.service.AccountService;
import com.finflow.beneficiary.dto.BeneficiaryResponse;
import com.finflow.beneficiary.service.BeneficiaryService;
import com.finflow.common.exception.CustomerNotFoundException;
import com.finflow.customer.dto.CustomerRequest;
import com.finflow.customer.entity.Customer;
import com.finflow.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final AccountService accountService;
    private final BeneficiaryService beneficiaryService;

    public  CustomerController(CustomerService customerService,AccountService accountService,BeneficiaryService beneficiaryService){
        this.customerService=customerService;
        this.accountService=accountService;
        this.beneficiaryService=beneficiaryService;
    }

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody  Customer customer){
        Customer savedCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(200).body("user created successfully");
    }

    @GetMapping("/{customerNumber}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String customerNumber){
        Customer customer= customerService.getCustomerByCustomerNumber(customerNumber);
         return ResponseEntity.ok(customer);
    }

    @GetMapping()
    public ResponseEntity<List<Customer>>getAllCustomers(){
      List<Customer>customers=customerService.getAllCustomers();
      return ResponseEntity.ok(customers);
    }

    @DeleteMapping("/{customerNumber}")
    public ResponseEntity<String>deleteCustomer(@PathVariable String customerNumber) {
        String deleteStatus = customerService.deleteCustomer(customerNumber);
        if ("User deleted".equals(deleteStatus)) {
            return ResponseEntity.ok(deleteStatus);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{customerNumber}")
    public ResponseEntity<String>updateCustomer(@PathVariable String customerNumber,@Valid @RequestBody  CustomerRequest customer){
        String updateStatus=customerService.updateCustomer(customerNumber,customer);
        return ResponseEntity.ok(updateStatus);
    }
    @GetMapping("/{customerNumber}/accounts")
    public ResponseEntity<List<AccountResponse>> getAccountsByCustomerNumber(@PathVariable String customerNumber){
        List<AccountResponse> accountList= accountService.findCustomerByAccountNumber(customerNumber);
        return ResponseEntity.ok(accountList);
    }

    @GetMapping("/{customerNumber}/beneficiaries")
    public ResponseEntity<List<BeneficiaryResponse>>getAllBeneficiaries(@PathVariable String customerNumber){
        List<BeneficiaryResponse>beneficiaryResponseList=beneficiaryService.getAllBeneficiaries(customerNumber);
        return ResponseEntity.status(200).body(beneficiaryResponseList);
    }

}
