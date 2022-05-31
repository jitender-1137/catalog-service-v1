package com.yaari.ms.catalogservice.jparepositories;

import com.yaari.ms.catalogservice.domains.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
