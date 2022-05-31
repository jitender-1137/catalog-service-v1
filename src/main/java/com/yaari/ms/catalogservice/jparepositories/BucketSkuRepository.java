package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.BucketSku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketSkuRepository extends JpaRepository<BucketSku, Long> {
}
