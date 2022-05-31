package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class OrderDto {
	private Long id;

	private String ucId;

	private Long resellerId;

	private String status;

	private double totalPrice;

	private double totalTax;

	private double shippingCharges;

	private double totalDiscount;

	private double totalResellerMargin;

	private Long addressId;


	private long updatedTime;

	private long paymentTime;

	private long cancellableTime;


}
