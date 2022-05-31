package com.yaari.ms.catalogservice.validation.category;

import com.yaari.ms.catalogservice.data.co.ApproveRejectCategoryCO;
import com.yaari.ms.catalogservice.data.co.ValidationCO;
import com.yaari.ms.catalogservice.domains.Category;
import com.yaari.ms.catalogservice.enums.CategoryStatus;
import com.yaari.ms.catalogservice.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
public class CheckerStatusValidation implements CategoryValidation {

    public void validate(ValidationCO passedCategoryObject, Category existingCategoryObject) {
        validateCheckerStatus((ApproveRejectCategoryCO) passedCategoryObject, existingCategoryObject);
    }

    private void validateCheckerStatus(ApproveRejectCategoryCO passedCategoryObject, Category existingCategory) {
        if (passedCategoryObject.getCheckerStatus() == null) {
            log.error("Trying to update without checker status");
            throw new ServiceException("CS_42");
        }
        if (CategoryStatus.REJECTED.name().equals(existingCategory.getCheckerStatus())) {
            log.warn("Rejected category can not approve and reject");
            throw new ServiceException(("CS_43"));
        }
        if (passedCategoryObject.getCheckerStatus() != null && (!CategoryStatus.PENDING.name().equals(existingCategory.getCheckerStatus())
                && !CategoryStatus.DRAFT.name().equals(existingCategory.getCheckerStatus()))) {
            log.warn("Trying to update checker status of category not pending for approval");
            throw new ServiceException(("CS_04"));
        }
        if (passedCategoryObject.getCheckerStatus() != null && !Objects.equals(passedCategoryObject.getCheckerStatus(), CategoryStatus.PENDING.name()) && !StringUtils.hasLength(passedCategoryObject.getReviewedBy())) {
            log.warn("Trying to update checker status of category without reviewer name");
            throw new ServiceException(("CS_09"));
        }
        if (passedCategoryObject.getCheckerStatus() != null && Objects.equals(passedCategoryObject.getCheckerStatus(), CategoryStatus.REJECTED.name()) && !StringUtils.hasLength(passedCategoryObject.getRejectReason())) {
            log.warn("Trying to update checker status of category without reject reason");
            throw new ServiceException(("CS_11"));
        }
        if (!Arrays.asList(CategoryStatus.REJECTED.name(), CategoryStatus.PENDING.name(), CategoryStatus.APPROVED.name()).contains(passedCategoryObject.getCheckerStatus())) {
            log.warn("Wrong value of checker status psssed : {}", passedCategoryObject.getCheckerStatus());
            throw new ServiceException(("CS_16"));
        }
        if (StringUtils.hasLength(passedCategoryObject.getRejectReason()) && !CategoryStatus.REJECTED.name().equals(passedCategoryObject.getCheckerStatus())) {
            log.warn("Trying to update reject reason without setting rejection");
            throw new ServiceException(("CS_15"));
        }
    }
}
