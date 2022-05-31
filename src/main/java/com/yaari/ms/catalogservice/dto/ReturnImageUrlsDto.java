package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ReturnImageUrlsDto {
	private Long id;

	private Long returnId;

	private String imageUrl;


}
