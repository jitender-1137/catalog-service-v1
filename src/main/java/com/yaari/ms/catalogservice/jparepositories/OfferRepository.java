package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
}
