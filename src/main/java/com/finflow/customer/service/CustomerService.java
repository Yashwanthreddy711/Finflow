package com.finflow.customer.service;

import com.finflow.account.repository.AccountRepository;
import com.finflow.common.exception.CustomerHasAccountsException;
import com.finflow.common.exception.CustomerNotFoundException;
import com.finflow.common.exception.DuplicateCustomerException;
import com.finflow.customer.dto.CustomerRequest;
import com.finflow.customer.entity.Customer;
import com.finflow.customer.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    public CustomerService(CustomerRepository customerRepository,AccountRepository accountRepository){
        this.customerRepository=customerRepository;
        this.accountRepository=accountRepository;
    }

    public Customer createCustomer(Customer customer){
        if(customerRepository.existsByCustomerNumber(customer.getCustomerNumber())){
            throw new DuplicateCustomerException("Customer already Exists");
        }
        if(customerRepository.existsByEmail(customer.getEmail())){
            throw new DuplicateCustomerException("Email already exists");
        }
        return customerRepository.save(customer);
    }

    public Customer getCustomerByCustomerNumber(String customerNumber){
        Customer customer = customerRepository.findByCustomerNumber(customerNumber);
        if(ObjectUtils.isEmpty(customer))throw new CustomerNotFoundException("User not found");
        return customer;
    }
    public List<Customer>getAllCustomers(){
        return customerRepository.findAll();
    }

    @Transactional
    public String deleteCustomer(String customerNumber){

        Customer customer=customerRepository.findByCustomerNumber(customerNumber);
        if(customer==null){
            throw new CustomerNotFoundException("Customer not found");
        }
        if(accountRepository.existsByCustomer_Id(customer.getId())){
                throw new CustomerHasAccountsException( "Customer deletion failed, customer is holding accounts");
        }
        customerRepository.delete(customer);
        return "User deletion success";
    }

    @Transactional
    public String updateCustomer(String customerNumber, CustomerRequest data){
        Customer customer = customerRepository.findByCustomerNumber(customerNumber);
        if(ObjectUtils.isEmpty(customer))throw new CustomerNotFoundException("User not found");
        customer.setPhone(data.getPhone());
        customer.setEmail(data.getEmail());
        customer.setFirstName(data.getFirstName());
        customer.setLastName(data.getLastName());
        customer.setDateOfBirth(data.getDateOfBirth());
        customer.setUpdatedAt(LocalDateTime.now());
        Customer updatedCustomer = customerRepository.save(customer);
        if(ObjectUtils.isEmpty(updatedCustomer))return "Error in updating the status";
        return "Customer data updated sucessfully";
    }
}
