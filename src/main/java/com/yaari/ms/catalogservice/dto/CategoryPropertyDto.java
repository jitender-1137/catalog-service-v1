package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class CategoryPropertyDto {
	private Long id;

	private Long categoryId;

	private String propertyName;

	private Boolean isMandatory;


}
