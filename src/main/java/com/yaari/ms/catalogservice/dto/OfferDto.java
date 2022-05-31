package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@NoArgsConstructor
@Getter
@Setter
public class OfferDto {
	private Long id;

	private Long productSkuId;

	private String name;

	private Date startDate;

	private Date endDate;


}
