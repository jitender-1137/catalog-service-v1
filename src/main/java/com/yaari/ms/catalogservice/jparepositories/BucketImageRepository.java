package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.BucketImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketImageRepository extends JpaRepository<BucketImage, Long> {
}
