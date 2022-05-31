package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class PincodeMasterDto {
	private Long id;

	private String pincode;

	private String city;

	private String state;


}
