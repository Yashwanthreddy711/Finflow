package com.finflow.beneficiary.dto;

import com.finflow.beneficiary.entity.BeneficiaryStatus;
import lombok.Data;


@Data
public class BeneficiaryResponse {
    private Long beneficiaryId;
    private String customerNumber;
    private String beneficiaryAccountNumber;
    private String nickName;
    private String ifscCode;
    private BeneficiaryStatus status;
}
