package com.yaari.ms.catalogservice.data.validation.level;

import com.yaari.ms.catalogservice.data.co.CategoryLevel;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CategoryLevelValidation implements ConstraintValidator<ValidLevel, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (!StringUtils.isEmpty(value) && CategoryLevel.contains(value))
			return true;
		return false;
	}
}
