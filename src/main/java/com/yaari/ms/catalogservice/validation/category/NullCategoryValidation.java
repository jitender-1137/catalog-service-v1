package com.yaari.ms.catalogservice.validation.category;

import com.yaari.ms.catalogservice.data.co.ValidationCO;
import com.yaari.ms.catalogservice.domains.Category;
import com.yaari.ms.catalogservice.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class NullCategoryValidation implements CategoryValidation {

    public void validate(ValidationCO passedCategoryObject, Category existingCategoryObject) {
        validateNullCategory(existingCategoryObject);
    }

    private void validateNullCategory(Category existingCategoryObject) {
        if (Objects.isNull(existingCategoryObject)) {
            log.error("Category does not exist with given id");
            throw new ServiceException("CS_10");
        }
    }
}
