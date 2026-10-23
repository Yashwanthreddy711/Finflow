package com.finflow;

import com.finflow.customer.entity.Customer;
import com.finflow.customer.entity.CustomerStatus;
import com.finflow.customer.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootApplication
public class FinFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinFlowApplication.class, args);
	}


}
