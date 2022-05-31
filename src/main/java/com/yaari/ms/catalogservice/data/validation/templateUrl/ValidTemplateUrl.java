package com.yaari.ms.catalogservice.data.validation.templateUrl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = CreateCategoryValidation.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ValidTemplateUrl.List.class)
public @interface ValidTemplateUrl {
	String message() default "Invalid url";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
	@Retention(RUNTIME)
	@Documented
	@interface List {

		ValidTemplateUrl[] value();
	}
}