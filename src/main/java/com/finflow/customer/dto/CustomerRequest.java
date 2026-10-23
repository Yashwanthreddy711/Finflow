package com.finflow.customer.dto;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import jakarta.validation.constraints.*;

import java.time.LocalDate;


@Getter
@Setter
public class CustomerRequest {
    @NotBlank @Size(max = 50)
    private String firstName;
    @Size(max = 50)
    private String lastName;
    @NotBlank @Email
    private String email;
    @NotBlank
    private String phone;
    @NonNull
    private LocalDate dateOfBirth;
}
