package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class SharedProductDto {
	private Long id;

	private Long resellerId;

	private Long productId;

	private String type;


}
