package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.ProductLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLogRepository extends JpaRepository<ProductLog, Long> {
}
