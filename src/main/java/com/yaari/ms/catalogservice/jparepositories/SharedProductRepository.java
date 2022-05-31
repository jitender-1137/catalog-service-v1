package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.SharedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharedProductRepository extends JpaRepository<SharedProduct, Long> {
}
