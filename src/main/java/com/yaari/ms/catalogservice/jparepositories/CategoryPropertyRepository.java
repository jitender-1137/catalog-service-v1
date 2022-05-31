package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.CategoryProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryPropertyRepository extends JpaRepository<CategoryProperty, Long> {
}
