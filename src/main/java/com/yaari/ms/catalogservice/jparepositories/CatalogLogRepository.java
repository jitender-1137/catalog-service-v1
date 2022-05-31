package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.CatalogLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogLogRepository extends JpaRepository<CatalogLog, Long> {
}
