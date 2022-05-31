package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.DummySuppliers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DummySuppliersRepository extends JpaRepository<DummySuppliers, Long> {
}
