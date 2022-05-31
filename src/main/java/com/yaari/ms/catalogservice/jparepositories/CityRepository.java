package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
