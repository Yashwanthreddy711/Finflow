package com.finflow.beneficiary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BeneficiaryRequest {
    @NotBlank
    private String customerNumber;
    @NotBlank(message = "Beneficiary account number required")
    @Size(min = 1,max = 20,message = "Invalid account number length")
    private String beneficiaryAccountNumber;
    private String nickName;
    @NotBlank
    private String ifscCode;
}
