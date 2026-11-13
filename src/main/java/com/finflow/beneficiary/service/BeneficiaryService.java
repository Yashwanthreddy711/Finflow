package com.finflow.beneficiary.service;

import com.finflow.beneficiary.dto.BeneficiaryRequest;
import com.finflow.beneficiary.dto.BeneficiaryResponse;
import com.finflow.beneficiary.dto.DeactivateBeneficiaryRequest;
import com.finflow.beneficiary.entity.Beneficiary;
import com.finflow.beneficiary.entity.BeneficiaryStatus;
import com.finflow.beneficiary.repository.BeneficiaryRepository;
import com.finflow.common.exception.BeneficiaryAccountNotFoundException;
import com.finflow.common.exception.CustomerNotFoundException;
import com.finflow.customer.entity.Customer;
import com.finflow.customer.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BeneficiaryService {
    private final CustomerRepository customerRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    public BeneficiaryService(CustomerRepository customerRepository,BeneficiaryRepository beneficiaryRepository) {
        this.customerRepository=customerRepository;
        this.beneficiaryRepository=beneficiaryRepository;
    }

    public BeneficiaryResponse addBeneficiary(BeneficiaryRequest beneficiaryRequest){
        Customer customer = customerRepository.findByCustomerNumber(beneficiaryRequest.getCustomerNumber());
        if(customer==null)throw new CustomerNotFoundException("Customer not found");
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setBeneficiaryAccountNumber(beneficiaryRequest.getBeneficiaryAccountNumber());
        beneficiary.setStatus(BeneficiaryStatus.PENDING);
        beneficiary.setIfscCode(beneficiaryRequest.getIfscCode());
        beneficiary.setNickName(beneficiaryRequest.getNickName());
        beneficiary.setCustomer(customer);
        beneficiary.setCreatedAt(LocalDateTime.now());
        beneficiary.setUpdatedAt(LocalDateTime.now());
        Beneficiary savedBeneficiary = beneficiaryRepository.save(beneficiary);
        return prepareBeneficiaryResponse(savedBeneficiary);
    }

    public List<BeneficiaryResponse>getAllBeneficiaries(String customerNumber){
        Customer customer = customerRepository.findByCustomerNumber(customerNumber);
        if(customer==null)throw new CustomerNotFoundException("Customer not found");

        List<Beneficiary>beneficiaries=beneficiaryRepository.findByCustomer_CustomerNumber(customerNumber);

        return prepareBeneficiaryListResponse(beneficiaries);
    }

    private BeneficiaryResponse prepareBeneficiaryResponse(Beneficiary beneficiary){
        BeneficiaryResponse beneficiaryResponse = new BeneficiaryResponse();
        beneficiaryResponse.setBeneficiaryId(beneficiary.getId());
        beneficiaryResponse.setBeneficiaryAccountNumber(beneficiary.getBeneficiaryAccountNumber());
        beneficiaryResponse.setNickName(beneficiary.getNickName());
        beneficiaryResponse.setIfscCode(beneficiary.getIfscCode());
        beneficiaryResponse.setCustomerNumber(beneficiary.getCustomer().getCustomerNumber());
        beneficiaryResponse.setStatus(beneficiary.getStatus());
        return beneficiaryResponse;
    }

    private List<BeneficiaryResponse>prepareBeneficiaryListResponse(List<Beneficiary>beneficiaries){
        List<BeneficiaryResponse>beneficiaryResponseList=new ArrayList<>();
        for(Beneficiary beneficiary:beneficiaries){
            beneficiaryResponseList.add(prepareBeneficiaryResponse(beneficiary));
        }
        return beneficiaryResponseList;
    }

    @Transactional
    public String deactivateBeneficiary(DeactivateBeneficiaryRequest deactivateBeneficiaryRequest){
        Beneficiary beneficiary = beneficiaryRepository.findByBeneficiaryAccountNumberAndCustomer_CustomerNumber(deactivateBeneficiaryRequest.getBeneficiaryAccountNumber(),deactivateBeneficiaryRequest.getCustomerNumber());
        if(beneficiary==null) throw new BeneficiaryAccountNotFoundException("Beneficiary account not found");
        if(beneficiary.getStatus()!=BeneficiaryStatus.INACTIVE){
           beneficiary.setStatus(BeneficiaryStatus.INACTIVE);
           beneficiary.setUpdatedAt(LocalDateTime.now());
           beneficiaryRepository.save(beneficiary);
       }
        return "Beneficiary Account deactivated";
    }
}
