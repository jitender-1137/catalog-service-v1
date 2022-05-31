package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ResellerSettlementDto {
	private Long id;

	private Long orderItemId;

	private String status;

	private String reason;

	private String type;


	private String cancelDescription;


}
