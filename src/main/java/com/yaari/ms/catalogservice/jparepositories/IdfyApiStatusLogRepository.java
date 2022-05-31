package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.IdfyApiStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdfyApiStatusLogRepository extends JpaRepository<IdfyApiStatusLog, Long> {
}
