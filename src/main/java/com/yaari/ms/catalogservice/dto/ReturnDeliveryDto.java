package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ReturnDeliveryDto {
	private Long id;

	private String returnReason;

	private String returnDate;

	private String returnAwbNumber;

	private String returnShippingPartner;

	private String penalty;

	private String penaltyAmount;

	private String status;

	private Long addressId;

	private String returnReasonParams;


}
