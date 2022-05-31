package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ZoneDto {
	private Long id;

	private String name;

	private String states;

	private Boolean status;

	private Boolean isDefault;

	private String defaultCity;

	private String defaultState;


}
