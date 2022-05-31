package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ShippingChargesDto {
	private Long id;

	private String categoryIds;

	private String shippingCharge;

	private long startTime;

	private String endTime;


}
