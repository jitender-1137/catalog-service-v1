package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.WishListItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListItemRepository extends JpaRepository<WishListItem, Long> {
}
