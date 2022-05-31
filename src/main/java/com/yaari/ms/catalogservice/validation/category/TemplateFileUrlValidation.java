package com.yaari.ms.catalogservice.validation.category;

import com.yaari.ms.catalogservice.data.co.CategoryLevel;
import com.yaari.ms.catalogservice.data.co.UpdateCategoryCO;
import com.yaari.ms.catalogservice.data.co.ValidationCO;
import com.yaari.ms.catalogservice.utility.CommonUtil;
import com.yaari.ms.catalogservice.domains.Category;
import com.yaari.ms.catalogservice.enums.CategoryStatus;
import com.yaari.ms.catalogservice.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TemplateFileUrlValidation implements CategoryValidation {

    public void validate(ValidationCO passedCategoryObject, Category existingCategoryObject) {
        validateOnTemplateFilUrl((UpdateCategoryCO) passedCategoryObject, existingCategoryObject);
    }

    private void validateOnTemplateFilUrl(UpdateCategoryCO passedCategoryObject, Category existingCategory) {
        if (passedCategoryObject.getTemplateFileUrl() != null) {
            if (!existingCategory.getLevel().equals(CategoryLevel.L3.name())) {
                log.error("can not update template file url in L1 and L2 category");
                throw new ServiceException("CS_29");
            } else if (!CommonUtil.isValidUrl(passedCategoryObject.getTemplateFileUrl())) {
                log.error("url is not rightly formed");
                throw new ServiceException("CS_17");
            }
        }
    }
}
