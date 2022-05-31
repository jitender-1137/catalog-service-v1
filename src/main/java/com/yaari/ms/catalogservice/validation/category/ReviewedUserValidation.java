package com.yaari.ms.catalogservice.validation.category;

import com.yaari.ms.catalogservice.data.co.ApproveRejectCategoryCO;
import com.yaari.ms.catalogservice.data.co.ValidationCO;
import com.yaari.ms.catalogservice.domains.Category;
import com.yaari.ms.catalogservice.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class ReviewedUserValidation implements CategoryValidation {

    public void validate(ValidationCO passedCategoryObject, Category existingCategoryObject) {
        validateReviewer((ApproveRejectCategoryCO) passedCategoryObject, existingCategoryObject);
    }

    private void validateReviewer(ApproveRejectCategoryCO passedCategoryObject, Category existingCategoryObject) {
        if (Objects.nonNull(existingCategoryObject.getUpdatedByUsername()) &&
                existingCategoryObject.getUpdatedByUsername().equals(passedCategoryObject.getReviewedBy())) {
            throw new ServiceException("CS_34");
        }
        if (Objects.isNull(existingCategoryObject.getUpdatedByUsername()) &&
                existingCategoryObject.getCreatedByUsername().equals(passedCategoryObject.getReviewedBy())) {
            throw new ServiceException("CS_34");
        }
    }
}
