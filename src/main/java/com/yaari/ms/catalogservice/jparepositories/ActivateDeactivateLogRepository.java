package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.ActivateDeactivateLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivateDeactivateLogRepository extends JpaRepository<ActivateDeactivateLog, Long> {
}
