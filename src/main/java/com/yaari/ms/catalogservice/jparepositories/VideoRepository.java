package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
