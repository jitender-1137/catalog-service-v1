package com.yaari.ms.catalogservice.validation.category;

import com.yaari.ms.catalogservice.data.co.ValidationCO;
import com.yaari.ms.catalogservice.domains.Category;
import com.yaari.ms.catalogservice.enums.CategoryStatus;
import com.yaari.ms.catalogservice.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NonPendingCheckerStatusValidation implements CategoryValidation {

    public void validate(ValidationCO passedCategoryObject, Category existingCategoryObject) {
        validateNonPendingCheckerStatus(existingCategoryObject);
    }

    private void validateNonPendingCheckerStatus(Category existingCategoryObject) {
        if (CategoryStatus.PENDING.name().equals(existingCategoryObject.getCheckerStatus()) ||
                CategoryStatus.DRAFT.name().equals(existingCategoryObject.getCheckerStatus())) {
            log.error("Can not update category with PENDING or DRAFT checker status");
            throw new ServiceException("CS_30");
        }
    }
}
