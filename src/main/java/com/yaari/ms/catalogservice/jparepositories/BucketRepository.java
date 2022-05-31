package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
}
