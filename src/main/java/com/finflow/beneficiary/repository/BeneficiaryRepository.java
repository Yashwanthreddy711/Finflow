package com.finflow.beneficiary.repository;

import com.finflow.beneficiary.entity.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary,Long> {
        List<Beneficiary> findByCustomer_CustomerNumber(String customerNumber);
        Beneficiary findByBeneficiaryAccountNumberAndCustomer_CustomerNumber(String beneficiaryAccountNumber,String customerId);
    }
