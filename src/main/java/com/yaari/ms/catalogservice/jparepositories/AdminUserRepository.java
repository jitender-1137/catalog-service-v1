package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
}
