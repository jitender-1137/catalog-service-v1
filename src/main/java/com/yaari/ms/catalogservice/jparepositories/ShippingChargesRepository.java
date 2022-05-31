package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.ShippingCharges;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingChargesRepository extends JpaRepository<ShippingCharges, Long> {
}
