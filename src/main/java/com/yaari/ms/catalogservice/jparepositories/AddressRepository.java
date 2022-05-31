package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
