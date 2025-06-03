package com.enset.studentpay.services;

import com.enset.studentpay.dto.NewPaymentDTO;
import com.enset.studentpay.enums.PaymentStatus;
import com.enset.studentpay.repository.StudentRepository;
import com.enset.studentpay.entities.Payment;
import com.enset.studentpay.entities.Student;

import com.enset.studentpay.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Service
@Transactional
public class PaymentService {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;

    public PaymentService(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    public Payment updatePaymentStatus(Long id, PaymentStatus status){
        Payment p = paymentRepository.findById(id).get();
        p.setStatus(status);
        return paymentRepository.save(p);
    }

    public Payment savePayment(MultipartFile file, NewPaymentDTO newPaymentDTO) throws IOException {
        Path folderPath = Paths.get(System.getProperty("user.home"), "students-data", "payments");
        //Create directories
        if(!Files.exists(folderPath)){
            Files.createDirectories(folderPath);
        }
        String fileExtension = "pdf";
        String fileName = UUID.randomUUID().toString() + '.' + fileExtension;
        Path filePath = Paths.get(System.getProperty("user.home"), "students-data", "payments", fileName);
        System.out.println("filePath: " + filePath);
        Files.copy(file.getInputStream(), filePath);

        Student student = studentRepository.findStudentByCode(newPaymentDTO.getStudentCode());
        //build payment data
        Payment payment = Payment.builder()
                .amount(newPaymentDTO.getAmount())
                .date(newPaymentDTO.getDate())
                .student(student)
                .type(newPaymentDTO.getType())
                .status(PaymentStatus.CREATED)
                .file(filePath.toUri().toString())
                .build();
        //save payment
        return paymentRepository.save(payment);
    }

    public byte[] getPaymentFile(Long id) throws IOException {
        Payment payment = paymentRepository.findById(id).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }

}
