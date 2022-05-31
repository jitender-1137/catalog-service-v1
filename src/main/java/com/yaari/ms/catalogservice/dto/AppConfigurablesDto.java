package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class AppConfigurablesDto {
	private Long id;

	private String paramKey;

	private Long paramValue;

}
