package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.SupplierPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierPaymentRepository extends JpaRepository<SupplierPayment, Long> {
}
