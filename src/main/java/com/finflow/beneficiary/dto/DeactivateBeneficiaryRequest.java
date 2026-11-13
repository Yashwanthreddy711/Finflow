package com.finflow.beneficiary.dto;

import lombok.Data;

@Data
public class DeactivateBeneficiaryRequest {
    private String customerNumber;
    private String beneficiaryAccountNumber;
}
