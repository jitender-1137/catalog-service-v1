package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.RatingProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingProductRepository extends JpaRepository<RatingProduct, Long> {
}
