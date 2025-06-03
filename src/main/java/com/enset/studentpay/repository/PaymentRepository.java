package com.enset.studentpay.repository;

import com.enset.studentpay.enums.PaymentStatus;
import com.enset.studentpay.enums.PaymentType;
import com.enset.studentpay.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStudentCode(String code);

    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByType(PaymentType type);

}
