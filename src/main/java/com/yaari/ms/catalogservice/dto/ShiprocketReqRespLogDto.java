package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ShiprocketReqRespLogDto {
	private Long id;

	private String orderItemId;

	private String reqBody;

	private String responseData;

	private String apiType;


}
