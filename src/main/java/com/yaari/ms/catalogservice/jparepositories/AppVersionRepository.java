package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppVersionRepository extends JpaRepository<AppVersion, Long> {
}
