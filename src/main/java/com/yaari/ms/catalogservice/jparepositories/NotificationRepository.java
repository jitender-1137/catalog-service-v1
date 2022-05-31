package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
