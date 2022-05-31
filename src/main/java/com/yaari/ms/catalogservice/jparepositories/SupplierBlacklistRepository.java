package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.SupplierBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierBlacklistRepository extends JpaRepository<SupplierBlacklist, Long> {
}
