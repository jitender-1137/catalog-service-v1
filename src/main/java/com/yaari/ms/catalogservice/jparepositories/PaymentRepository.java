package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
