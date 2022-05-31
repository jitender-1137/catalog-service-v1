package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class PaymentDto {
	private Long id;

	private Long orderId;

	private String paymentStatus;

	private String paymentMode;

	private String razorPayPaymentId;

	private String razorPayOrderId;

	private String redirectUrl;

	private String paymentMethod;


}
