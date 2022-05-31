package com.yaari.ms.catalogservice.enums;

import lombok.Getter;

@Getter
public enum OrderByType {
	asc("ascending"),
	desc("descending");
	String methodAssociated;

	OrderByType(String methodAssociated) {
		this.methodAssociated = methodAssociated;
	}
}
