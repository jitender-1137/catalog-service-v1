package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ShipmentHistoryDto {
	private Long id;

	private String awbNumber;

	private String status;

	private String statusDate;

	private String expectedDeliveryTime;

	private String location;


	private String statusCode;


	private Boolean visible;

	private String comment;


}
