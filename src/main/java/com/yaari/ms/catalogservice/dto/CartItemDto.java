package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class CartItemDto {
	private Long id;

	private Long resellerId;

	private Long productId;

	private Long quantity;

	private Long margin;


}
