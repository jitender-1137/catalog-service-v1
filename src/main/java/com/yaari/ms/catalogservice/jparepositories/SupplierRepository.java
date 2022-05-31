package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
