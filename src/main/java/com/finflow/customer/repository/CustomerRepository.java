package com.finflow.customer.repository;

import com.finflow.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findByCustomerNumber(String customerNumber);
    boolean existsByCustomerNumber(String customerNumber);
    Customer deleteByCustomerNumber(String customerNumber);
    boolean existsByEmail(String email);
}
