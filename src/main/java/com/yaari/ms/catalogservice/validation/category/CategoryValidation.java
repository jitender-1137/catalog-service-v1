package com.yaari.ms.catalogservice.validation.category;

import com.yaari.ms.catalogservice.data.co.ValidationCO;
import com.yaari.ms.catalogservice.domains.Category;

public interface CategoryValidation {
	void validate(ValidationCO categoryCO, Category category);
}
