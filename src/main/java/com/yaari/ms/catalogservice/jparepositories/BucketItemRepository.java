package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.BucketItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketItemRepository extends JpaRepository<BucketItem, Long> {
}
