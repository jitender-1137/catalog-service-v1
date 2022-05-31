package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.ProductSkuImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSkuImageRepository extends JpaRepository<ProductSkuImage, Long> {
}
