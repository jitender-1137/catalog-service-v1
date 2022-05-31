package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.PincodeMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PincodeMasterRepository extends JpaRepository<PincodeMaster, Long> {
}
