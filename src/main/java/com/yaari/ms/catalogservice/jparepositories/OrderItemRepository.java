package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
