package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.BankValidationFailedLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankValidationFailedLogRepository extends JpaRepository<BankValidationFailedLog, Long> {
}
