package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.SecurityImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityImageRepository extends JpaRepository<SecurityImage, Long> {
}
