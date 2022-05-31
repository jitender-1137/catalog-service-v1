package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.ShipmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentHistoryRepository extends JpaRepository<ShipmentHistory, Long> {
}
