package me.sjlee.payment.domain.repository;

import me.sjlee.payment.domain.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
