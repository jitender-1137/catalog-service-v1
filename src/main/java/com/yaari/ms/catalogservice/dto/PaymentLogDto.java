package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class PaymentLogDto {
	private Long id;

	private String paymentStatus;

	private String paymentMode;

	private String razorPayOrderId;

	private Long orderId;


}
