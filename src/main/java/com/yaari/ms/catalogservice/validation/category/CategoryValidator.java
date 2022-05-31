package com.yaari.ms.catalogservice.validation.category;

import com.yaari.ms.catalogservice.data.co.ApproveRejectCategoryCO;
import com.yaari.ms.catalogservice.data.co.UpdateCategoryCO;
import com.yaari.ms.catalogservice.domains.Category;
import com.yaari.ms.catalogservice.domains.Category;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class CategoryValidator {

	public static void validate(UpdateCategoryCO categoryCO, Category category) {
		List<CategoryValidation> validations = new ArrayList<>();
		validations.add(new NullCategoryValidation());
		validations.add(new HomePageBannerImageValidation());
		validations.add(new ActivityStatusValidation());
		validations.add(new UpdaterNameValidation());
		validations.add(new TemplateFileUrlValidation());
		validations.add(new NonPendingCheckerStatusValidation());
		validations.forEach(categoryValidation -> categoryValidation.validate(categoryCO, category));
	}

	public static void validateUpdateAll(ApproveRejectCategoryCO categoryCO, Category category) {
		List<CategoryValidation> validations = new ArrayList<>();
		validations.add(new NullCategoryValidation());
		validations.add(new CheckerStatusValidation());
		validations.add(new ReviewedUserValidation());
		validations.forEach(categoryValidation -> categoryValidation.validate(categoryCO, category));
	}
}
