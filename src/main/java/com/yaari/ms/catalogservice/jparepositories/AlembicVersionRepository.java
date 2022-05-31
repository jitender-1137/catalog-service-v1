package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.AlembicVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlembicVersionRepository extends JpaRepository<AlembicVersion, Long> {
}
