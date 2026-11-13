package com.finflow.beneficiary.controller;

import com.finflow.beneficiary.dto.BeneficiaryRequest;
import com.finflow.beneficiary.dto.BeneficiaryResponse;
import com.finflow.beneficiary.dto.DeactivateBeneficiaryRequest;
import com.finflow.beneficiary.service.BeneficiaryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/beneficiaries")
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;

    public BeneficiaryController(BeneficiaryService beneficiaryService){
        this.beneficiaryService=beneficiaryService;
    }

    @PostMapping
    public ResponseEntity<BeneficiaryResponse>addBeneficiary(@RequestBody @Valid BeneficiaryRequest beneficiaryRequest){
        BeneficiaryResponse beneficiaryResponse = beneficiaryService.addBeneficiary(beneficiaryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(beneficiaryResponse);
    }
    @PatchMapping("/deactivate")
    public ResponseEntity<String>deactivateBeneficiary(@RequestBody DeactivateBeneficiaryRequest deactivateBeneficiaryRequest){
        String response = beneficiaryService.deactivateBeneficiary(deactivateBeneficiaryRequest);
        return ResponseEntity.ok(response);
    }
}
