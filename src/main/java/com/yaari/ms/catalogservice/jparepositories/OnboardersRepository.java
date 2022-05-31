package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.Onboarders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnboardersRepository extends JpaRepository<Onboarders, Long> {
}
