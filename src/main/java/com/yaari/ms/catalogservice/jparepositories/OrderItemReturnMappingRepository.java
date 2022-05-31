package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.OrderItemReturnMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemReturnMappingRepository extends JpaRepository<OrderItemReturnMapping, Long> {
}
