package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
}
