package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
