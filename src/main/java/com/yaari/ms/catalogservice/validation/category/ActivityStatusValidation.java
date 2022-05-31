package com.yaari.ms.catalogservice.validation.category;

import com.yaari.ms.catalogservice.data.co.UpdateCategoryCO;
import com.yaari.ms.catalogservice.data.co.ValidationCO;
import com.yaari.ms.catalogservice.domains.Category;
import com.yaari.ms.catalogservice.enums.ActivityStatus;
import com.yaari.ms.catalogservice.enums.CategoryStatus;
import com.yaari.ms.catalogservice.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActivityStatusValidation implements CategoryValidation {

	public void validate(ValidationCO passedCategoryObject, Category existingCategoryObject) {
		validateActivityStatus((UpdateCategoryCO) passedCategoryObject, existingCategoryObject);
	}

	private void validateActivityStatus(UpdateCategoryCO passedCategoryObject, Category existingCategoryObject) {
		String passedCategoryStatus = passedCategoryObject.getStatus();
		if (!isValidStatus(passedCategoryStatus)) {
			log.error("Passed status value not in record: {}", passedCategoryStatus);
			throw new ServiceException("CS_06");
		}
		if (CategoryStatus.REJECTED.name().equals(existingCategoryObject.getCheckerStatus())){
			log.error("Can not update in Rejected category: {}", passedCategoryStatus);
			throw new ServiceException("CS_41");
		}
	}

	public static boolean isValidStatus(String passedCategoryStatus) {
		return (ActivityStatus.INACTIVE.name().equals(passedCategoryStatus) ||
				ActivityStatus.ACTIVE.name().equals(passedCategoryStatus));
	}
}
