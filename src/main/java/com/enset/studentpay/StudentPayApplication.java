package com.enset.studentpay;

import com.enset.studentpay.repository.PaymentRepository;
import com.enset.studentpay.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StudentPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentPayApplication.class, args);
	}

}
