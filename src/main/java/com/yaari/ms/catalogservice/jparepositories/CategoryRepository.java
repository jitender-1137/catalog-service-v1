package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
