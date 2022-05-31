package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
