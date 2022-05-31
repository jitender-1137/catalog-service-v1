package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.Reseller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResellerRepository extends JpaRepository<Reseller, Long> {
}
