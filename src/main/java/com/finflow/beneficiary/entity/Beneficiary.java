package com.finflow.beneficiary.entity;

import com.finflow.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="ff_beneficiary")
@Data
public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String beneficiaryAccountNumber;
    private String nickName;
    @Column(nullable = false)
    private String ifscCode;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BeneficiaryStatus status;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "customer_id",referencedColumnName = "customerNumber",nullable = false)
    Customer customer;
}
