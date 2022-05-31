package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.OrderItemStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemStatusLogRepository extends JpaRepository<OrderItemStatusLog, Long> {
}
