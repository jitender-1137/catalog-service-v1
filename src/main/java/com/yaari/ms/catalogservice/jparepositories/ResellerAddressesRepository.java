package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.ResellerAddresses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResellerAddressesRepository extends JpaRepository<ResellerAddresses, Long> {
}
