package com.yaari.ms.catalogservice.validation.category;

import com.yaari.ms.catalogservice.data.co.CategoryLevel;
import com.yaari.ms.catalogservice.data.co.UpdateCategoryCO;
import com.yaari.ms.catalogservice.data.co.ValidationCO;
import com.yaari.ms.catalogservice.utility.CommonUtil;
import com.yaari.ms.catalogservice.domains.Category;
import com.yaari.ms.catalogservice.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HomePageBannerImageValidation implements CategoryValidation {

	public void validate(ValidationCO passedCategoryObject, Category existingCategoryObject) {
		validateOnHomepageUrl((UpdateCategoryCO) passedCategoryObject, existingCategoryObject);
	}

	private void validateOnHomepageUrl(UpdateCategoryCO passedCategoryObject, Category existingCategory) {
		if (passedCategoryObject.getCategoryPageBannerImage() != null && !CommonUtil.isValidUrl(passedCategoryObject.getCategoryPageBannerImage())) {
			log.error("url is not rightly formed");
			throw new ServiceException("CS_17");
		}
		if (passedCategoryObject.getHomepageBannerImage() != null) {
			if (!existingCategory.getLevel().equals(CategoryLevel.L1.name())) {
				log.error("can not change banner image for this category");
				throw new ServiceException("CS_28");
			} else if (!CommonUtil.isValidUrl(passedCategoryObject.getHomepageBannerImage())) {
				log.error("url is not rightly formed");
				throw new ServiceException("CS_17");
			}
		}
	}
}
