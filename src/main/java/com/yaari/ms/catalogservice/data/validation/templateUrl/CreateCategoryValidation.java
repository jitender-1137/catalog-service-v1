package com.yaari.ms.catalogservice.data.validation.templateUrl;

import com.yaari.ms.catalogservice.data.co.CategoryCO;
import com.yaari.ms.catalogservice.data.co.CategoryLevel;
import com.yaari.ms.catalogservice.utility.CommonUtil;
import com.yaari.ms.catalogservice.validation.category.ActivityStatusValidation;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreateCategoryValidation implements ConstraintValidator<ValidTemplateUrl, CategoryCO> {

	@Override
	public boolean isValid(CategoryCO value, ConstraintValidatorContext context) {
		CategoryLevel level = value.getLevel();
		context.disableDefaultConstraintViolation();
		if (!StringUtils.hasText(value.getStatus())) {
			return buildErrorMessage(context, "{CS_36}", "status");
		}
		if (!ActivityStatusValidation.isValidStatus(value.getStatus())) {
			return buildErrorMessage(context, "{CS_06}", "status");
		}
		if (level == CategoryLevel.L1 && StringUtils.isEmpty(value.getHomepageBannerImage())) {
			return buildErrorMessage(context, "{CS_32}", "homepageBannerImage");
		}
		if (level == CategoryLevel.L2 || level == CategoryLevel.L3) {
			if (value.getParentCategoryId() == null) {
				return buildErrorMessage(context, "{CS_26}", "parentCategoryId");
			}
			if (StringUtils.hasText(value.getHomepageBannerImage())) {
				return buildErrorMessage(context, "{CS_37}", "homepageBannerImage");
			}
		}
		if (level == CategoryLevel.L2 || level == CategoryLevel.L1) {
			if (StringUtils.hasLength(value.getTemplateFileUrl())) {
				return buildErrorMessage(context, "{CS_19}", "templateFileUrl");
			}
		}
		if (level == CategoryLevel.L3) {
			if (value.getTemplateFileUrl() == null) {
				return buildErrorMessage(context, "{CS_18}", "templateFileUrl");
			}
			if (!CommonUtil.isValidUrl(value.getTemplateFileUrl())) {
				return buildErrorMessage(context, "{CS_33}", "templateFileUrl");
			}
		}
		return true;
	}

	private boolean buildErrorMessage(ConstraintValidatorContext context, String errorCode, String field) {
		context.buildConstraintViolationWithTemplate(errorCode)
				.addNode(field)
				.addConstraintViolation();
		return false;
	}
}
