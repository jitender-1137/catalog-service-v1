package com.yaari.ms.catalogservice.validation.category;

import com.yaari.ms.catalogservice.data.co.UpdateCategoryCO;
import com.yaari.ms.catalogservice.data.co.ValidationCO;
import com.yaari.ms.catalogservice.domains.Category;
import com.yaari.ms.catalogservice.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class UpdaterNameValidation implements CategoryValidation {

	public void validate(ValidationCO passedCategoryObject, Category existingCategoryObject) {
		validateUpdaterName((UpdateCategoryCO) passedCategoryObject);
	}

	private void validateUpdaterName(UpdateCategoryCO passedCategoryObject) {
		String updaterName = passedCategoryObject.getUpdatedByUsername();
		if (!StringUtils.hasText(updaterName))
			throw new ServiceException("CS_08");
	}
}
