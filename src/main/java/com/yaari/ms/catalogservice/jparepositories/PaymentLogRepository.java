package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.PaymentLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentLogRepository extends JpaRepository<PaymentLog, Long> {
}
