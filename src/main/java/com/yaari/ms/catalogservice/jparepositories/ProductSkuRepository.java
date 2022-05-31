package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.ProductSku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSkuRepository extends JpaRepository<ProductSku, Long> {
}
