package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.ProductCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCatalogRepository extends JpaRepository<ProductCatalog, Long> {
}
