package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.ResellerComplaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResellerComplaintRepository extends JpaRepository<ResellerComplaint, Long> {
}
