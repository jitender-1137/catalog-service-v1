package com.yaari.ms.catalogservice.enums;

import lombok.Getter;

@Getter
public enum ComparativeRelation {
	eq("equal"),
	gt("greaterThan"),
	ne("notEqual"),
	lt("lessThan"),
	lte("lessThanEqualTo"),
	gte("greaterThanEqualTo"),
	like("like"),
	ilike("ilike"),
	or_eq("or_equal"),
	or_gt("or_greaterThan"),
	or_ne("or_notEqual"),
	or_lt("or_lessThan"),
	or_lte("or_lessThanEqualTo"),
	or_gte("or_greaterThanEqualTo"),
	or_like("or_like"),
	or_ilike("or_ilike");
	String methodAssociated;

	ComparativeRelation(String methodAssociated) {
		this.methodAssociated = methodAssociated;
	}
}
